package com.textgame.data.local.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "dialogues")
data class DialogueEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val sessionId: Long,
    val turnNumber: Int = 0,
    val speaker: String = "",
    val content: String = "",
    val isPlayer: Boolean = false,
    val isNarrative: Boolean = false,
    val createdAt: Long = 0
)
