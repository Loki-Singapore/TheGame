package com.textgame.domain.model

data class Protagonist(
    val id: Long = 0,
    val sessionId: Long = 0,
    val name: String,
    val attributes: Map<String, Any> = emptyMap(),
    val inventory: List<String> = emptyList(),
    val relationships: Map<String, Int> = emptyMap(),
    val location: String = "",
    val createdAt: Long = 0,
    val updatedAt: Long = 0
)
