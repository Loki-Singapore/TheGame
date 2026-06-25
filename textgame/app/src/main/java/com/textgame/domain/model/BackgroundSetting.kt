package com.textgame.domain.model

data class BackgroundSetting(
    val id: Long = 0,
    val sessionId: Long = 0,
    val protagonistBackground: String = "",
    val npcBackgrounds: Map<String, String> = emptyMap(),
    val worldHistory: String = "",
    val relationshipHistory: String = "",
    val majorPlotThreads: List<String> = emptyList(),
    val unlockedLore: List<String> = emptyList(),
    val updatedAt: Long = 0
)
