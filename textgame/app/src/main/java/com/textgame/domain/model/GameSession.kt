package com.textgame.domain.model

data class GameSession(
    val id: Long = 0,
    val name: String,
    val createdAt: Long,
    val currentTurn: Int = 0
)
