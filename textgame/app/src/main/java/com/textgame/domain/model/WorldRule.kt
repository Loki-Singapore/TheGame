package com.textgame.domain.model

data class WorldRule(
    val id: String = "",
    val content: String = "",
    val createdAt: Long = System.currentTimeMillis()
)
