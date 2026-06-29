package com.textgame.domain.model

data class WorldRule(
    val id: String = generateShortUuid(),
    val content: String = "",
    val createdAt: Long = System.currentTimeMillis()
)

fun generateShortUuid(): String {
    return java.util.UUID.randomUUID().toString()
        .replace("-", "")
        .take(8)
}
