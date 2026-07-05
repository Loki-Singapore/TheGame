package com.textgame.data.local.db.entity

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.textgame.domain.model.AttributeCategory
import com.textgame.domain.model.BackgroundSetting
import com.textgame.domain.model.WorldRule
import com.textgame.domain.model.Dialogue
import com.textgame.domain.model.GameSession
import com.textgame.domain.model.GameState
import com.textgame.domain.model.NPC
import com.textgame.domain.model.Protagonist
import com.textgame.domain.model.Summary
import com.textgame.domain.model.WorldSetting

private val gson = Gson()

fun GameSessionEntity.toDomain(): GameSession = GameSession(
    id = id,
    name = name,
    createdAt = createdAt,
    currentTurn = currentTurn
)

fun GameSession.toEntity(): GameSessionEntity = GameSessionEntity(
    id = id,
    name = name,
    createdAt = createdAt,
    currentTurn = currentTurn
)

fun ProtagonistEntity.toDomain(): Protagonist {
    val attributesType = object : TypeToken<Map<String, Any>>() {}.type
    val inventoryType = object : TypeToken<List<String>>() {}.type
    val relationshipsType = object : TypeToken<Map<String, Int>>() {}.type

    return Protagonist(
        id = id,
        sessionId = sessionId,
        name = name,
        attributes = gson.fromJson(attributesJson, attributesType) ?: emptyMap(),
        inventory = gson.fromJson(inventoryJson, inventoryType) ?: emptyList(),
        relationships = gson.fromJson(relationshipsJson, relationshipsType) ?: emptyMap(),
        location = location,
        createdAt = createdAt,
        updatedAt = updatedAt
    )
}

fun Protagonist.toEntity(): ProtagonistEntity = ProtagonistEntity(
    id = id,
    sessionId = sessionId,
    name = name,
    attributesJson = gson.toJson(attributes),
    inventoryJson = gson.toJson(inventory),
    relationshipsJson = gson.toJson(relationships),
    location = location,
    createdAt = createdAt,
    updatedAt = updatedAt
)

fun NPCStateEntity.toDomain(): NPC {
    val attributesType = object : TypeToken<Map<String, Any>>() {}.type
    val dialogueType = object : TypeToken<List<Dialogue>>() {}.type

    return NPC(
        id = id,
        sessionId = sessionId,
        npcId = npcId,
        name = name,
        role = role,
        briefing = briefing,
        attributes = gson.fromJson(attributesJson, attributesType) ?: emptyMap(),
        dialogueHistory = gson.fromJson(dialogueHistoryJson, dialogueType) ?: emptyList(),
        mood = mood,
        awareness = awareness,
        appearance = appearance,
        personality = personality,
        backstory = backstory,
        updatedAt = updatedAt
    )
}

fun NPC.toEntity(): NPCStateEntity = NPCStateEntity(
    id = id,
    sessionId = sessionId,
    npcId = npcId,
    name = name,
    role = role,
    briefing = briefing,
    attributesJson = gson.toJson(attributes),
    dialogueHistoryJson = gson.toJson(dialogueHistory),
    mood = mood,
    awareness = awareness,
    appearance = appearance,
    personality = personality,
    backstory = backstory,
    updatedAt = updatedAt
)

fun GameStateEntity.toDomain(): GameState {
    val eventsType = object : TypeToken<List<String>>() {}.type
    val flagsType = object : TypeToken<Map<String, Boolean>>() {}.type

    return GameState(
        id = id,
        sessionId = sessionId,
        currentScene = currentScene,
        turnCount = turnCount,
        activeEvents = gson.fromJson(activeEventsJson, eventsType) ?: emptyList(),
        flags = gson.fromJson(flagsJson, flagsType) ?: emptyMap(),
        lastAction = lastAction,
        currentTime = currentTime,
        updatedAt = updatedAt
    )
}

fun GameState.toEntity(): GameStateEntity = GameStateEntity(
    id = id,
    sessionId = sessionId,
    currentScene = currentScene,
    turnCount = turnCount,
    activeEventsJson = gson.toJson(activeEvents),
    flagsJson = gson.toJson(flags),
    lastAction = lastAction,
    currentTime = currentTime,
    updatedAt = updatedAt
)

fun SummaryEntity.toDomain(): Summary {
    val eventsType = object : TypeToken<List<String>>() {}.type
    val npcsType = object : TypeToken<List<String>>() {}.type

    return Summary(
        id = id,
        sessionId = sessionId,
        summaryText = summaryText,
        keyEvents = gson.fromJson(keyEventsJson, eventsType) ?: emptyList(),
        involvedNPCs = gson.fromJson(involvedNPCsJson, npcsType) ?: emptyList(),
        sceneContext = sceneContext,
        turnRangeStart = turnRangeStart,
        turnRangeEnd = turnRangeEnd,
        generatedAt = generatedAt
    )
}

fun Summary.toEntity(): SummaryEntity = SummaryEntity(
    id = id,
    sessionId = sessionId,
    summaryText = summaryText,
    keyEventsJson = gson.toJson(keyEvents),
    involvedNPCsJson = gson.toJson(involvedNPCs),
    sceneContext = sceneContext,
    turnRangeStart = turnRangeStart,
    turnRangeEnd = turnRangeEnd,
    generatedAt = generatedAt
)

fun WorldSettingEntity.toDomain(): WorldSetting {
    val rulesType = object : TypeToken<List<String>>() {}.type
    val factionsType = object : TypeToken<List<String>>() {}.type
    val locationsType = object : TypeToken<List<String>>() {}.type
    val attributesType = object : TypeToken<List<AttributeCategory>>() {}.type
    val worldRulesType = object : TypeToken<List<WorldRule>>() {}.type

    return WorldSetting(
        id = id,
        sessionId = sessionId,
        name = name,
        description = description,
        worldType = worldType,
        timeSetting = timeSetting,
        locationSetting = locationSetting,
        socialStructure = socialStructure,
        specialRules = gson.fromJson(specialRulesJson, rulesType) ?: emptyList(),
        lore = lore,
        factions = gson.fromJson(factionsJson, factionsType) ?: emptyList(),
        locations = gson.fromJson(locationsJson, locationsType) ?: emptyList(),
        attributeCategories = gson.fromJson(attributeCategoriesJson, attributesType) ?: emptyList(),
        worldRules = gson.fromJson(worldRulesJson, worldRulesType) ?: emptyList(),
        updatedAt = updatedAt
    )
}

fun WorldSetting.toEntity(): WorldSettingEntity = WorldSettingEntity(
    id = id,
    sessionId = sessionId,
    name = name,
    description = description,
    worldType = worldType,
    timeSetting = timeSetting,
    locationSetting = locationSetting,
    socialStructure = socialStructure,
    specialRulesJson = gson.toJson(specialRules),
    lore = lore,
    factionsJson = gson.toJson(factions),
    locationsJson = gson.toJson(locations),
    attributeCategoriesJson = gson.toJson(attributeCategories),
    worldRulesJson = gson.toJson(worldRules),
    updatedAt = updatedAt
)

fun BackgroundSettingEntity.toDomain(): BackgroundSetting {
    val npcBgType = object : TypeToken<Map<String, String>>() {}.type
    val threadsType = object : TypeToken<List<String>>() {}.type
    val loreType = object : TypeToken<List<String>>() {}.type

    return BackgroundSetting(
        id = id,
        sessionId = sessionId,
        protagonistBackground = protagonistBackground,
        npcBackgrounds = gson.fromJson(npcBackgroundsJson, npcBgType) ?: emptyMap(),
        worldHistory = worldHistory,
        relationshipHistory = relationshipHistory,
        majorPlotThreads = gson.fromJson(majorPlotThreadsJson, threadsType) ?: emptyList(),
        unlockedLore = gson.fromJson(unlockedLoreJson, loreType) ?: emptyList(),
        updatedAt = updatedAt
    )
}

fun BackgroundSetting.toEntity(): BackgroundSettingEntity = BackgroundSettingEntity(
    id = id,
    sessionId = sessionId,
    protagonistBackground = protagonistBackground,
    npcBackgroundsJson = gson.toJson(npcBackgrounds),
    worldHistory = worldHistory,
    relationshipHistory = relationshipHistory,
    majorPlotThreadsJson = gson.toJson(majorPlotThreads),
    unlockedLoreJson = gson.toJson(unlockedLore),
    updatedAt = updatedAt
)

fun DialogueEntity.toDomain(): Dialogue = Dialogue(
    id = id,
    sessionId = sessionId,
    turnNumber = turnNumber,
    speaker = speaker,
    content = content,
    isPlayer = isPlayer,
    isNarrative = isNarrative,
    createdAt = createdAt
)

fun Dialogue.toEntity(): DialogueEntity = DialogueEntity(
    id = id,
    sessionId = sessionId,
    turnNumber = turnNumber,
    speaker = speaker,
    content = content,
    isPlayer = isPlayer,
    isNarrative = isNarrative,
    createdAt = createdAt
)
