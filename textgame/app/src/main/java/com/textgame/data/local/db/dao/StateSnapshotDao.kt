package com.textgame.data.local.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.textgame.data.local.db.entity.StateSnapshotEntity

@Dao
interface StateSnapshotDao {

    @Insert
    suspend fun insertSnapshot(snapshot: StateSnapshotEntity): Long

    @Query("SELECT * FROM state_snapshots WHERE sessionId = :sessionId AND turnNumber = :turnNumber ORDER BY id DESC LIMIT 1")
    suspend fun getSnapshotByTurn(sessionId: Long, turnNumber: Int): StateSnapshotEntity?

    @Query("SELECT * FROM state_snapshots WHERE sessionId = :sessionId ORDER BY turnNumber DESC LIMIT 1")
    suspend fun getLatestSnapshot(sessionId: Long): StateSnapshotEntity?

    @Query("DELETE FROM state_snapshots WHERE sessionId = :sessionId AND turnNumber >= :fromTurn")
    suspend fun deleteSnapshotsFromTurn(sessionId: Long, fromTurn: Int)

    @Query("DELETE FROM state_snapshots WHERE sessionId = :sessionId")
    suspend fun deleteSnapshotsBySessionId(sessionId: Long)
}
