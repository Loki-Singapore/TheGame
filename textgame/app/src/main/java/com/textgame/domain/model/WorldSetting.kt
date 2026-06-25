package com.textgame.domain.model

data class WorldSetting(
    val id: Long = 0,
    val sessionId: Long = 0,
    val name: String = "",
    val description: String = "",
    val worldType: String = "",
    val timeSetting: String = "",
    val locationSetting: String = "",
    val socialStructure: String = "",
    val specialRules: List<String> = emptyList(),
    val lore: String = "",
    val factions: List<String> = emptyList(),
    val locations: List<String> = emptyList(),
    val updatedAt: Long = 0
)
