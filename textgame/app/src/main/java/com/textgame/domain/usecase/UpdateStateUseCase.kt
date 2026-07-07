package com.textgame.domain.usecase

import com.textgame.domain.model.AIResponse
import com.textgame.domain.model.AttributeCategory
import com.textgame.domain.model.AttributeCategoryChange
import com.textgame.domain.model.AttributeType
import com.textgame.domain.model.GameChanges
import com.textgame.domain.model.GameState
import com.textgame.domain.model.NPC
import com.textgame.domain.model.NPCChanges
import com.textgame.domain.model.Protagonist
import com.textgame.domain.model.ProtagonistChanges
import com.textgame.domain.model.WorldRule
import com.textgame.domain.model.WorldSetting
import com.textgame.domain.repository.GameRepository

class UpdateStateUseCase(
    private val gameRepository: GameRepository
) {
    suspend fun execute(sessionId: Long, aiResponse: AIResponse, userInput: String) {
        val now = System.currentTimeMillis()
        val stateChanges = aiResponse.stateChanges

        // 1. 先处理属性类目变更：更新 WorldSetting.attributeCategories，
        //    并把新增类目的 defaultValue 应用到主角、把删除类目从主角/NPC 上清理掉。
        //    返回更新后的类目列表，供后续 updateProtagonist/updateNPC 校验新属性名是否合法。
        val updatedCategories = processAttributeCategoryChanges(
            sessionId, stateChanges?.game?.attributeCategories, now
        )

        val protagonist = gameRepository.getProtagonist(sessionId)
        if (protagonist != null && stateChanges?.protagonist != null) {
            val updatedProtagonist = updateProtagonist(
                protagonist, stateChanges.protagonist, updatedCategories, now
            )
            gameRepository.saveProtagonist(updatedProtagonist)
        }

        if (stateChanges?.npc != null) {
            stateChanges.npc.forEach { (npcId, npcChanges) ->
                if (npcChanges.isDeleted) {
                    val existingNpc = gameRepository.getNPCByNpcId(sessionId, npcId)
                    if (existingNpc != null) {
                        gameRepository.deleteNPC(existingNpc.id)
                    }
                } else {
                    val existingNpc = gameRepository.getNPCByNpcId(sessionId, npcId)
                    if (existingNpc != null) {
                        val updatedNpc = updateNPC(existingNpc, npcChanges, updatedCategories, now)
                        gameRepository.updateNPC(updatedNpc)
                    } else {
                        // 新NPC：插入数据库
                        val newNpc = NPC(
                            sessionId = sessionId,
                            npcId = npcId,
                            name = npcChanges.name ?: "未知角色",
                            role = npcChanges.role ?: "未知",
                            briefing = npcChanges.briefing ?: "",
                            attributes = npcChanges.attributes ?: emptyMap(),
                            mood = npcChanges.mood ?: "neutral",
                            awareness = npcChanges.awareness ?: "",
                            appearance = npcChanges.appearance ?: "",
                            personality = npcChanges.personality ?: "",
                            backstory = npcChanges.backstory ?: "",
                            hiddenAgenda = npcChanges.hiddenAgenda ?: "",
                            updatedAt = now
                        )
                        gameRepository.saveNPC(newNpc)
                    }
                }
            }
        }

        val gameState = gameRepository.getGameState(sessionId)
        if (gameState != null) {
            val updatedGameState = updateGameState(gameState, stateChanges?.game, userInput, now)
            gameRepository.updateGameState(updatedGameState)
        }

        stateChanges?.game?.worldRules?.let { worldRuleChanges ->
            if (worldRuleChanges.isNotEmpty()) {
                val worldSetting = gameRepository.getWorldSetting(sessionId)
                if (worldSetting != null) {
                    val currentRules = worldSetting.worldRules.toMutableList()
                    worldRuleChanges.forEach { change ->
                        val ruleId = change.id?.takeIf { it.isNotBlank() }
                        if (ruleId != null) {
                            // 有id：更新已有细则或按指定id新增
                            val index = currentRules.indexOfFirst { it.id == ruleId }
                            if (index >= 0) {
                                currentRules[index] = currentRules[index].copy(content = change.content)
                            } else {
                                currentRules.add(WorldRule(id = ruleId, content = change.content))
                            }
                        } else {
                            // 无id：自动生成新id新增
                            val newId = generateWorldRuleId(currentRules)
                            currentRules.add(WorldRule(id = newId, content = change.content))
                        }
                    }
                    gameRepository.updateWorldSetting(worldSetting.copy(worldRules = currentRules))
                }
            }
        }
    }

    /**
     * 处理属性类目变更。返回更新后的类目列表；若没有变更则返回 null（调用方按原列表校验）。
     */
    private suspend fun processAttributeCategoryChanges(
        sessionId: Long,
        changes: List<AttributeCategoryChange>?,
        now: Long
    ): List<AttributeCategory>? {
        if (changes.isNullOrEmpty()) return null

        val worldSetting = gameRepository.getWorldSetting(sessionId) ?: return null
        val currentCategories = worldSetting.attributeCategories.toMutableList()
        val deletedNames = mutableSetOf<String>()
        val addedWithDefaults = mutableMapOf<String, Any>()

        changes.forEach { change ->
            val name = change.name.trim()
            if (name.isBlank()) return@forEach

            if (change.isDeleted) {
                // 删除：从类目列表移除，并标记清理主角/NPC 的对应属性
                currentCategories.removeAll { it.name == name }
                deletedNames.add(name)
            } else {
                val existingIndex = currentCategories.indexOfFirst { it.name == name }
                if (existingIndex >= 0) {
                    // 修改：按提供的字段部分更新，未提供的字段保留原值
                    val existing = currentCategories[existingIndex]
                    val parsedType = parseAttributeType(change.type) ?: existing.type
                    currentCategories[existingIndex] = AttributeCategory(
                        name = name,
                        type = parsedType,
                        minValue = change.minValue ?: existing.minValue,
                        maxValue = change.maxValue ?: existing.maxValue,
                        defaultValue = change.defaultValue ?: existing.defaultValue,
                        enumOptions = change.enumOptions ?: existing.enumOptions,
                        description = change.description ?: existing.description
                    )
                } else {
                    // 新增：type 必填，否则跳过该条变更
                    val parsedType = parseAttributeType(change.type) ?: return@forEach
                    val newCategory = AttributeCategory(
                        name = name,
                        type = parsedType,
                        minValue = change.minValue,
                        maxValue = change.maxValue,
                        defaultValue = change.defaultValue,
                        enumOptions = change.enumOptions ?: emptyList(),
                        description = change.description ?: ""
                    )
                    currentCategories.add(newCategory)
                    // 引擎自动用 defaultValue 初始化主角的该属性
                    newCategory.defaultValue?.let { addedWithDefaults[name] = it }
                }
            }
        }

        gameRepository.updateWorldSetting(
            worldSetting.copy(attributeCategories = currentCategories, updatedAt = now)
        )

        // 把新增类目的默认值应用到主角身上（仅当主角当前没有该属性时）
        if (addedWithDefaults.isNotEmpty()) {
            val protagonist = gameRepository.getProtagonist(sessionId)
            if (protagonist != null) {
                val newAttributes = protagonist.attributes.toMutableMap()
                var changed = false
                addedWithDefaults.forEach { (name, value) ->
                    if (!newAttributes.containsKey(name)) {
                        newAttributes[name] = value
                        changed = true
                    }
                }
                if (changed) {
                    gameRepository.saveProtagonist(
                        protagonist.copy(attributes = newAttributes, updatedAt = now)
                    )
                }
            }
        }

        // 删除类目时，同步从主角和所有 NPC 的 attributes 中移除该键，避免脏数据残留
        if (deletedNames.isNotEmpty()) {
            val protagonist = gameRepository.getProtagonist(sessionId)
            if (protagonist != null) {
                val newAttributes = protagonist.attributes.filterKeys { it !in deletedNames }
                if (newAttributes.size != protagonist.attributes.size) {
                    gameRepository.saveProtagonist(
                        protagonist.copy(attributes = newAttributes, updatedAt = now)
                    )
                }
            }

            val npcs = gameRepository.getNPCList(sessionId)
            npcs.forEach { npc ->
                val newAttributes = npc.attributes.filterKeys { it !in deletedNames }
                if (newAttributes.size != npc.attributes.size) {
                    gameRepository.updateNPC(npc.copy(attributes = newAttributes, updatedAt = now))
                }
            }
        }

        return currentCategories
    }

    private fun parseAttributeType(typeStr: String?): AttributeType? {
        if (typeStr.isNullOrBlank()) return null
        // 兼容大小写与历史 STRING 写法
        val normalized = when (typeStr.uppercase()) {
            "STRING" -> "TEXT"
            else -> typeStr.uppercase()
        }
        return AttributeType.values().firstOrNull { it.name == normalized }
    }

    private fun generateWorldRuleId(existingRules: List<WorldRule>): String {
        var maxNum = 0
        existingRules.forEach { rule ->
            val num = rule.id.removePrefix("worldrule_").toIntOrNull() ?: 0
            if (num > maxNum) maxNum = num
        }
        return "worldrule_%03d".format(maxNum + 1)
    }

    private fun updateProtagonist(
        protagonist: Protagonist,
        changes: ProtagonistChanges,
        validCategories: List<AttributeCategory>?,
        now: Long
    ): Protagonist {
        val updatedAttributes = protagonist.attributes.toMutableMap()
        changes.attributes?.forEach { (key, value) ->
            // 接受：已存在的属性，或本轮更新后类目列表中存在的属性（含本轮新增的）
            if (updatedAttributes.containsKey(key) ||
                validCategories?.any { it.name == key } == true
            ) {
                updatedAttributes[key] = value
            }
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

    private fun updateNPC(
        npc: NPC,
        changes: NPCChanges,
        validCategories: List<AttributeCategory>?,
        now: Long
    ): NPC {
        val updatedAttributes = npc.attributes.toMutableMap()
        changes.attributes?.forEach { (key, value) ->
            if (updatedAttributes.containsKey(key) ||
                validCategories?.any { it.name == key } == true
            ) {
                updatedAttributes[key] = value
            }
        }

        return npc.copy(
            attributes = updatedAttributes,
            name = changes.name ?: npc.name,
            briefing = changes.briefing ?: npc.briefing,
            mood = changes.mood ?: npc.mood,
            awareness = changes.awareness ?: npc.awareness,
            appearance = changes.appearance ?: npc.appearance,
            personality = changes.personality ?: npc.personality,
            backstory = changes.backstory ?: npc.backstory,
            role = changes.role ?: npc.role,
            hiddenAgenda = changes.hiddenAgenda ?: npc.hiddenAgenda,
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
