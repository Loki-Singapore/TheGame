package com.textgame.data.local.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "game_states")
data class GameStateEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val sessionId: Long,
    val currentScene: String = "",
    val turnCount: Int = 0,
    val activeEventsJson: String = "[]",
    val flagsJson: String = "{}",
    val lastAction: String = "",
    val updatedAt: Long = 0
)
