package com.textgame.domain.model

data class GameState(
    val id: Long = 0,
    val sessionId: Long = 0,
    val currentScene: String = "",
    val turnCount: Int = 0,
    val activeEvents: List<String> = emptyList(),
    val flags: Map<String, Boolean> = emptyMap(),
    val lastAction: String = "",
    val currentTime: String = "",
    val updatedAt: Long = 0
)
