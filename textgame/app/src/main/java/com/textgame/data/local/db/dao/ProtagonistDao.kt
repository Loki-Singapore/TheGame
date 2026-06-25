package com.textgame.data.local.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.textgame.data.local.db.entity.ProtagonistEntity

@Dao
interface ProtagonistDao {
    @Query("SELECT * FROM protagonist_states WHERE sessionId = :sessionId LIMIT 1")
    suspend fun getProtagonistBySessionId(sessionId: Long): ProtagonistEntity?

    @Insert
    suspend fun insertProtagonist(protagonist: ProtagonistEntity): Long

    @Update
    suspend fun updateProtagonist(protagonist: ProtagonistEntity)

    @Query("DELETE FROM protagonist_states WHERE sessionId = :sessionId")
    suspend fun deleteProtagonistBySessionId(sessionId: Long)
}
