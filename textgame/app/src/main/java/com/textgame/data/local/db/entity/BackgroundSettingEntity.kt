package com.textgame.data.local.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "background_settings")
data class BackgroundSettingEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val sessionId: Long,
    val protagonistBackground: String = "",
    val npcBackgroundsJson: String = "{}",
    val worldHistory: String = "",
    val relationshipHistory: String = "",
    val majorPlotThreadsJson: String = "[]",
    val unlockedLoreJson: String = "[]",
    val updatedAt: Long = 0
)
