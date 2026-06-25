package com.textgame.data.local.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.textgame.data.local.db.entity.GameSessionEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SessionDao {
    @Query("SELECT * FROM game_sessions ORDER BY createdAt DESC")
    fun getAllSessions(): Flow<List<GameSessionEntity>>

    @Query("SELECT * FROM game_sessions WHERE id = :id")
    suspend fun getSessionById(id: Long): GameSessionEntity?

    @Insert
    suspend fun insertSession(session: GameSessionEntity): Long

    @Update
    suspend fun updateSession(session: GameSessionEntity)

    @Query("DELETE FROM game_sessions WHERE id = :id")
    suspend fun deleteSession(id: Long)

    @Query("UPDATE game_sessions SET currentTurn = :turn WHERE id = :id")
    suspend fun updateCurrentTurn(id: Long, turn: Int)
}
