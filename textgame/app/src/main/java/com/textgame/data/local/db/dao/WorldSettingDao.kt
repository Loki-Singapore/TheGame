package com.textgame.data.local.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.textgame.data.local.db.entity.WorldSettingEntity

@Dao
interface WorldSettingDao {
    @Query("SELECT * FROM world_settings WHERE sessionId = :sessionId LIMIT 1")
    suspend fun getWorldSettingBySessionId(sessionId: Long): WorldSettingEntity?

    @Insert
    suspend fun insertWorldSetting(setting: WorldSettingEntity): Long

    @Update
    suspend fun updateWorldSetting(setting: WorldSettingEntity)

    @Query("DELETE FROM world_settings WHERE sessionId = :sessionId")
    suspend fun deleteWorldSettingBySessionId(sessionId: Long)
}
