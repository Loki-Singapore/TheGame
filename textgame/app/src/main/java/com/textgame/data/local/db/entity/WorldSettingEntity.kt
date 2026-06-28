package com.textgame.data.local.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "world_settings")
data class WorldSettingEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val sessionId: Long,
    val name: String = "",
    val description: String = "",
    val worldType: String = "",
    val timeSetting: String = "",
    val locationSetting: String = "",
    val socialStructure: String = "",
    val specialRulesJson: String = "[]",
    val lore: String = "",
    val factionsJson: String = "[]",
    val locationsJson: String = "[]",
    val attributeCategoriesJson: String = "[]",
    val worldRulesJson: String = "[]",
    val updatedAt: Long = 0
)
