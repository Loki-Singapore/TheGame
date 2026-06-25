package com.textgame.data.local.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.textgame.data.local.db.entity.DialogueEntity

@Dao
interface DialogueDao {

    @Query("SELECT * FROM dialogues WHERE sessionId = :sessionId ORDER BY createdAt ASC")
    suspend fun getDialoguesBySessionId(sessionId: Long): List<DialogueEntity>

    @Insert
    suspend fun insertDialogue(dialogue: DialogueEntity): Long

    @Query("UPDATE dialogues SET content = :content WHERE id = :id")
    suspend fun updateDialogueContent(id: Long, content: String)

    @Query("DELETE FROM dialogues WHERE id = :id")
    suspend fun deleteDialogueById(id: Long)

    @Query("DELETE FROM dialogues WHERE sessionId = :sessionId")
    suspend fun deleteDialoguesBySessionId(sessionId: Long)

    @Query("DELETE FROM dialogues WHERE sessionId = :sessionId AND turnNumber >= :fromTurn")
    suspend fun deleteDialoguesFromTurn(sessionId: Long, fromTurn: Int)
}
