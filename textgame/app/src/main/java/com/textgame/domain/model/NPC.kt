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
    // ponytail: 玩家不可见的隐藏动机。AI作为GM可见，用于驱动NPC自主行动、背叛、伏笔。
    // 绝不能在 GameScreen 的状态面板里渲染此字段。
    val hiddenAgenda: String = "",
    val updatedAt: Long = 0
)
