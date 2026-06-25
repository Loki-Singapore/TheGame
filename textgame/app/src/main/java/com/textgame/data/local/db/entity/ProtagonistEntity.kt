package com.textgame.data.local.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "protagonist_states")
data class ProtagonistEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val sessionId: Long,
    val name: String,
    val attributesJson: String = "{}",
    val inventoryJson: String = "[]",
    val relationshipsJson: String = "{}",
    val location: String = "",
    val createdAt: Long = 0,
    val updatedAt: Long = 0
)
