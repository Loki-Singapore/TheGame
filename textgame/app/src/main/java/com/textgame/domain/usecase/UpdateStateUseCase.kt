package com.textgame.domain.usecase

import com.textgame.domain.model.AIResponse
import com.textgame.domain.model.GameChanges
import com.textgame.domain.model.GameState
import com.textgame.domain.model.NPC
import com.textgame.domain.model.NPCChanges
import com.textgame.domain.model.Protagonist
import com.textgame.domain.model.ProtagonistChanges
import com.textgame.domain.repository.GameRepository

class UpdateStateUseCase(
    private val gameRepository: GameRepository
) {
    suspend fun execute(sessionId: Long, aiResponse: AIResponse, userInput: String) {
        val now = System.currentTimeMillis()
        val stateChanges = aiResponse.stateChanges

        val protagonist = gameRepository.getProtagonist(sessionId)
        if (protagonist != null && stateChanges?.protagonist != null) {
            val updatedProtagonist = updateProtagonist(protagonist, stateChanges.protagonist, now)
            gameRepository.saveProtagonist(updatedProtagonist)
        }

        if (stateChanges?.npc != null) {
            val existingNpcs = gameRepository.getNPCsBySession(sessionId)
            var maxNpcNumber = existingNpcs.mapNotNull {
                it.npcId.removePrefix("npc_").toIntOrNull()
            }.maxOrNull() ?: 0

            stateChanges.npc.forEach { (npcId, npcChanges) ->
                // 尝试用 npcId 查找已有NPC
                val existingNpc = gameRepository.getNPCByNpcId(sessionId, npcId)
                if (existingNpc != null) {
                    val updatedNpc = updateNPC(existingNpc, npcChanges, now)
                    gameRepository.updateNPC(updatedNpc)
                } else {
                    // 新NPC：分配新ID并插入数据库
                    maxNpcNumber++
                    val newNpc = NPC(
                        sessionId = sessionId,
                        npcId = npcId,
                        name = npcChanges.name ?: "未知角色",
                        role = npcChanges.role ?: "未知",
                        attributes = npcChanges.attributeChanges ?: emptyMap(),
                        mood = npcChanges.mood ?: "neutral",
                        awareness = npcChanges.awareness ?: "",
                        appearance = npcChanges.appearance ?: "",
                        personality = npcChanges.personality ?: "",
                        backstory = npcChanges.backstory ?: "",
                        updatedAt = now
                    )
                    gameRepository.saveNPC(newNpc)
                }
            }
        }

        val gameState = gameRepository.getGameState(sessionId)
        if (gameState != null) {
            val updatedGameState = updateGameState(gameState, stateChanges?.game, userInput, now)
            gameRepository.updateGameState(updatedGameState)
        }
    }

    private fun updateProtagonist(
        protagonist: Protagonist,
        changes: ProtagonistChanges,
        now: Long
    ): Protagonist {
        var updatedAttributes = protagonist.attributes.toMutableMap()

        changes.attributeChanges?.forEach { (key, value) ->
            updatedAttributes[key] = value
        }

        var updatedInventory = protagonist.inventory.toMutableList()
        changes.inventoryAdd?.let { updatedInventory.addAll(it) }
        changes.inventoryRemove?.let { updatedInventory.removeAll(it.toSet()) }

        return protagonist.copy(
            attributes = updatedAttributes,
            inventory = updatedInventory,
            location = changes.locationChange ?: protagonist.location,
            updatedAt = now
        )
    }

    private fun updateNPC(npc: NPC, changes: NPCChanges, now: Long): NPC {
        var updatedAttributes = npc.attributes.toMutableMap()
        changes.attributeChanges?.let { updatedAttributes.putAll(it) }

        return npc.copy(
            attributes = updatedAttributes,
            name = changes.name ?: npc.name,
            mood = changes.mood ?: npc.mood,
            awareness = changes.awareness ?: npc.awareness,
            appearance = changes.appearance ?: npc.appearance,
            personality = changes.personality ?: npc.personality,
            backstory = changes.backstory ?: npc.backstory,
            role = changes.role ?: npc.role,
            updatedAt = now
        )
    }

    private fun updateGameState(
        gameState: GameState,
        changes: GameChanges?,
        userInput: String,
        now: Long
    ): GameState {
        if (changes == null) {
            return gameState.copy(
                turnCount = gameState.turnCount + 1,
                lastAction = userInput,
                updatedAt = now
            )
        }

        var updatedEvents = gameState.activeEvents.toMutableList()
        changes.eventTrigger?.let { updatedEvents.add(it) }

        var updatedFlags = gameState.flags.toMutableMap()
        changes.flagSet?.let { updatedFlags.putAll(it) }

        return gameState.copy(
            currentScene = changes.sceneChange ?: gameState.currentScene,
            turnCount = gameState.turnCount + 1,
            activeEvents = updatedEvents,
            flags = updatedFlags,
            lastAction = userInput,
            updatedAt = now
        )
    }
}
