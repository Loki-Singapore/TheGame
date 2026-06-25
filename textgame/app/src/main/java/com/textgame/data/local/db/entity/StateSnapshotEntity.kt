package com.textgame.data.local.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "state_snapshots")
data class StateSnapshotEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val sessionId: Long = 0,
    val turnNumber: Int = 0,
    val protagonistJson: String = "",
    val npcsJson: String = "",
    val gameStateJson: String = "",
    val worldSettingJson: String = "",
    val backgroundSettingJson: String = "",
    val summaryJson: String = "",
    val createdAt: Long = System.currentTimeMillis()
)
