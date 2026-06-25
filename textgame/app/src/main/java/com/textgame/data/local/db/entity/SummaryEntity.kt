package com.textgame.data.local.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "summaries")
data class SummaryEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val sessionId: Long,
    val summaryText: String = "",
    val keyEventsJson: String = "[]",
    val involvedNPCsJson: String = "[]",
    val sceneContext: String = "",
    val turnRangeStart: Int = 0,
    val turnRangeEnd: Int = 0,
    val generatedAt: Long = 0
)
