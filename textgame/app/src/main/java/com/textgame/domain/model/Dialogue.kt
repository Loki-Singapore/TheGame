package com.textgame.domain.model

data class Dialogue(
    val id: Long = 0,
    val sessionId: Long = 0,
    val turnNumber: Int = 0,
    val speaker: String = "",
    val content: String = "",
    val isPlayer: Boolean = false,
    val isNarrative: Boolean = false,
    val createdAt: Long = System.currentTimeMillis()
)
