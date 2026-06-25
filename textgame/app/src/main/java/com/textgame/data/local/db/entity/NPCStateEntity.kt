package com.textgame.data.local.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "npc_states")
data class NPCStateEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val sessionId: Long,
    val npcId: String = "",
    val name: String,
    val role: String,
    val attributesJson: String = "{}",
    val dialogueHistoryJson: String = "[]",
    val mood: String = "neutral",
    val awareness: String = "",
    val appearance: String = "",
    val personality: String = "",
    val backstory: String = "",
    val updatedAt: Long = 0
)
