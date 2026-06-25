package com.textgame.domain.model

data class NPC(
    val id: Long = 0,
    val sessionId: Long = 0,
    val npcId: String = "",
    val name: String,
    val role: String,
    val briefing: String = "",
    val attributes: Map<String, Any> = emptyMap(),
    val dialogueHistory: List<Dialogue> = emptyList(),
    val mood: String = "neutral",
    val awareness: String = "",
    val appearance: String = "",
    val personality: String = "",
    val backstory: String = "",
    val updatedAt: Long = 0
)
