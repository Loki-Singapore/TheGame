package com.textgame.data.local.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.textgame.data.local.db.entity.NPCStateEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface NPCDao {
    @Query("SELECT * FROM npc_states WHERE sessionId = :sessionId")
    fun getNPCsBySessionId(sessionId: Long): Flow<List<NPCStateEntity>>

    @Query("SELECT * FROM npc_states WHERE sessionId = :sessionId")
    suspend fun getNPCListBySessionId(sessionId: Long): List<NPCStateEntity>

    @Query("SELECT * FROM npc_states WHERE id = :id")
    suspend fun getNPCById(id: Long): NPCStateEntity?

    @Query("SELECT * FROM npc_states WHERE sessionId = :sessionId AND name = :name")
    suspend fun getNPCByName(sessionId: Long, name: String): NPCStateEntity?

    @Query("SELECT * FROM npc_states WHERE sessionId = :sessionId AND npcId = :npcId")
    suspend fun getNPCByNpcId(sessionId: Long, npcId: String): NPCStateEntity?

    @Insert
    suspend fun insertNPC(npc: NPCStateEntity): Long

    @Update
    suspend fun updateNPC(npc: NPCStateEntity)

    @Query("DELETE FROM npc_states WHERE id = :id")
    suspend fun deleteNPC(id: Long)

    @Query("DELETE FROM npc_states WHERE sessionId = :sessionId")
    suspend fun deleteNPCsBySessionId(sessionId: Long)
}
