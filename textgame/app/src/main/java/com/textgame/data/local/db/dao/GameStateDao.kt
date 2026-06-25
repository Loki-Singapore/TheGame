package com.textgame.data.local.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.textgame.data.local.db.entity.GameStateEntity

@Dao
interface GameStateDao {
    @Query("SELECT * FROM game_states WHERE sessionId = :sessionId LIMIT 1")
    suspend fun getGameStateBySessionId(sessionId: Long): GameStateEntity?

    @Insert
    suspend fun insertGameState(gameState: GameStateEntity): Long

    @Update
    suspend fun updateGameState(gameState: GameStateEntity)

    @Query("DELETE FROM game_states WHERE sessionId = :sessionId")
    suspend fun deleteGameStateBySessionId(sessionId: Long)
}
