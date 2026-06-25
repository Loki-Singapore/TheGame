package com.textgame.domain.model

data class StateSnapshot(
    val id: Long = 0,
    val sessionId: Long = 0,
    val turnNumber: Int = 0,
    val protagonist: Protagonist? = null,
    val npcs: List<NPC> = emptyList(),
    val gameState: GameState? = null,
    val worldSetting: WorldSetting? = null,
    val backgroundSetting: BackgroundSetting? = null,
    val summary: Summary? = null,
    val createdAt: Long = System.currentTimeMillis()
)
