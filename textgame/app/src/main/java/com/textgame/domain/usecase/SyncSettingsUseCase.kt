package com.textgame.domain.usecase

import com.textgame.domain.model.AIResponse
import com.textgame.domain.model.BackgroundSetting
import com.textgame.domain.model.WorldSetting
import com.textgame.domain.repository.GameRepository

class SyncSettingsUseCase(
    private val gameRepository: GameRepository
) {
    suspend fun execute(sessionId: Long, aiResponse: AIResponse) {
        val stateChanges = aiResponse.stateChanges ?: return
        val now = System.currentTimeMillis()

        val worldSetting = gameRepository.getWorldSetting(sessionId)
        val backgroundSetting = gameRepository.getBackgroundSetting(sessionId)

        if (worldSetting == null || backgroundSetting == null) return

        val newLocations = mutableListOf<String>()
        val newPlotThreads = mutableListOf<String>()
        val newNpcBackgrounds = mutableMapOf<String, String>()
        val newUnlockedLore = mutableListOf<String>()

        stateChanges.game?.sceneChange?.let { newScene ->
            if (!worldSetting.locations.contains(newScene)) {
                newLocations.add(newScene)
            }
        }

        stateChanges.game?.eventTrigger?.let { event ->
            if (!backgroundSetting.majorPlotThreads.contains(event)) {
                newPlotThreads.add(event)
            }
        }

        stateChanges.npc?.forEach { (npcId, npcChanges) ->
            val existingBg = backgroundSetting.npcBackgrounds[npcId]
            val newAwareness = npcChanges.awareness
            if (newAwareness != null && existingBg?.contains(newAwareness) != true) {
                val currentBg = existingBg ?: ""
                newNpcBackgrounds[npcId] = if (currentBg.isEmpty()) {
                    newAwareness
                } else {
                    "$currentBg；$newAwareness"
                }
            }
        }

        stateChanges.game?.flagSet?.forEach { (flag, value) ->
            if (value && !backgroundSetting.unlockedLore.contains(flag)) {
                newUnlockedLore.add(flag)
            }
        }

        var worldChanged = false
        var updatedWorld = worldSetting
        if (newLocations.isNotEmpty()) {
            updatedWorld = updatedWorld.copy(
                locations = updatedWorld.locations + newLocations,
                updatedAt = now
            )
            worldChanged = true
        }

        var bgChanged = false
        var updatedBg = backgroundSetting
        if (newPlotThreads.isNotEmpty()) {
            updatedBg = updatedBg.copy(
                majorPlotThreads = updatedBg.majorPlotThreads + newPlotThreads,
                updatedAt = now
            )
            bgChanged = true
        }
        if (newNpcBackgrounds.isNotEmpty()) {
            val combinedBgs = updatedBg.npcBackgrounds.toMutableMap()
            combinedBgs.putAll(newNpcBackgrounds)
            updatedBg = updatedBg.copy(
                npcBackgrounds = combinedBgs,
                updatedAt = now
            )
            bgChanged = true
        }
        if (newUnlockedLore.isNotEmpty()) {
            updatedBg = updatedBg.copy(
                unlockedLore = updatedBg.unlockedLore + newUnlockedLore,
                updatedAt = now
            )
            bgChanged = true
        }

        if (worldChanged) {
            gameRepository.updateWorldSetting(updatedWorld)
        }
        if (bgChanged) {
            gameRepository.updateBackgroundSetting(updatedBg)
        }
    }
}
