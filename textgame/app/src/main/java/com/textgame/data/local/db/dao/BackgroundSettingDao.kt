package com.textgame.data.local.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.textgame.data.local.db.entity.BackgroundSettingEntity

@Dao
interface BackgroundSettingDao {
    @Query("SELECT * FROM background_settings WHERE sessionId = :sessionId LIMIT 1")
    suspend fun getBackgroundSettingBySessionId(sessionId: Long): BackgroundSettingEntity?

    @Insert
    suspend fun insertBackgroundSetting(setting: BackgroundSettingEntity): Long

    @Update
    suspend fun updateBackgroundSetting(setting: BackgroundSettingEntity)

    @Query("DELETE FROM background_settings WHERE sessionId = :sessionId")
    suspend fun deleteBackgroundSettingBySessionId(sessionId: Long)
}
