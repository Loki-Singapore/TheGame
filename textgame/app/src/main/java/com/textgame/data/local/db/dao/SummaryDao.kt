package com.textgame.data.local.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.textgame.data.local.db.entity.SummaryEntity

@Dao
interface SummaryDao {
    @Query("SELECT * FROM summaries WHERE sessionId = :sessionId ORDER BY generatedAt DESC LIMIT 1")
    suspend fun getLatestSummary(sessionId: Long): SummaryEntity?

    @Query("SELECT * FROM summaries WHERE sessionId = :sessionId ORDER BY generatedAt DESC")
    suspend fun getAllSummaries(sessionId: Long): List<SummaryEntity>

    @Insert
    suspend fun insertSummary(summary: SummaryEntity): Long

    @Update
    suspend fun updateSummary(summary: SummaryEntity)

    @androidx.room.Upsert
    suspend fun upsertSummary(summary: SummaryEntity): Long

    @Query("DELETE FROM summaries WHERE sessionId = :sessionId")
    suspend fun deleteSummariesBySessionId(sessionId: Long)

    @Query("DELETE FROM summaries WHERE sessionId = :sessionId AND turnRangeStart > :turn")
    suspend fun deleteSummariesAfterTurn(sessionId: Long, turn: Int)

    @Query("DELETE FROM summaries WHERE sessionId = :sessionId AND turnRangeEnd > :turn")
    suspend fun deleteSummariesOverlappingTurn(sessionId: Long, turn: Int)
}
