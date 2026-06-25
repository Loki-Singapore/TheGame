package com.textgame.domain.repository

import com.textgame.domain.model.BackgroundSetting
import com.textgame.domain.model.Dialogue
import com.textgame.domain.model.GameSession
import com.textgame.domain.model.GameState
import com.textgame.domain.model.NPC
import com.textgame.domain.model.Protagonist
import com.textgame.domain.model.StateSnapshot
import com.textgame.domain.model.Summary
import com.textgame.domain.model.WorldSetting
import kotlinx.coroutines.flow.Flow

interface GameRepository {
    fun getAllSessions(): Flow<List<GameSession>>
    suspend fun getSessionById(id: Long): GameSession?
    suspend fun createGameSession(name: String): Long
    suspend fun deleteSession(id: Long)
    suspend fun updateCurrentTurn(sessionId: Long, turn: Int)

    suspend fun getProtagonist(sessionId: Long): Protagonist?
    suspend fun saveProtagonist(protagonist: Protagonist): Long

    fun getNPCs(sessionId: Long): Flow<List<NPC>>
    suspend fun getNPCList(sessionId: Long): List<NPC>
    suspend fun getNPCById(id: Long): NPC?
    suspend fun getNPCByName(sessionId: Long, name: String): NPC?
    suspend fun saveNPC(npc: NPC): Long
    suspend fun updateNPC(npc: NPC)
    suspend fun deleteNPC(id: Long)

    suspend fun getGameState(sessionId: Long): GameState?
    suspend fun saveGameState(gameState: GameState): Long
    suspend fun updateGameState(gameState: GameState)

    suspend fun getLatestSummary(sessionId: Long): Summary?
    suspend fun saveSummary(summary: Summary): Long
    suspend fun updateSummary(summary: Summary)
    suspend fun upsertSummary(summary: Summary): Long
    suspend fun deleteSummariesAfterTurn(sessionId: Long, turn: Int)
    suspend fun deleteSummariesOverlappingTurn(sessionId: Long, turn: Int)
    suspend fun deleteAllNPCs(sessionId: Long)

    suspend fun getWorldSetting(sessionId: Long): WorldSetting?
    suspend fun saveWorldSetting(setting: WorldSetting): Long
    suspend fun updateWorldSetting(setting: WorldSetting)

    suspend fun getBackgroundSetting(sessionId: Long): BackgroundSetting?
    suspend fun saveBackgroundSetting(setting: BackgroundSetting): Long
    suspend fun updateBackgroundSetting(setting: BackgroundSetting)

    suspend fun getDialogues(sessionId: Long): List<Dialogue>
    suspend fun saveDialogue(dialogue: Dialogue): Long
    suspend fun updateDialogueContent(id: Long, content: String)
    suspend fun deleteDialogueById(id: Long)
    suspend fun deleteDialoguesFromTurn(sessionId: Long, fromTurn: Int)

    suspend fun saveStateSnapshot(snapshot: StateSnapshot): Long
    suspend fun getStateSnapshotByTurn(sessionId: Long, turnNumber: Int): StateSnapshot?
    suspend fun deleteStateSnapshotsFromTurn(sessionId: Long, fromTurn: Int)
}
