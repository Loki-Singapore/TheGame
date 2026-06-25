package com.textgame.domain.model

data class Summary(
    val id: Long = 0,
    val sessionId: Long = 0,
    val summaryText: String = "",
    val keyEvents: List<String> = emptyList(),
    val involvedNPCs: List<String> = emptyList(),
    val sceneContext: String = "",
    val turnRangeStart: Int = 0,
    val turnRangeEnd: Int = 0,
    val generatedAt: Long = 0
)
