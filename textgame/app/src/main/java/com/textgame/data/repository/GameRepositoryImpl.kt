package com.textgame.data.repository

import com.textgame.data.local.db.dao.BackgroundSettingDao
import com.textgame.data.local.db.dao.DialogueDao
import com.textgame.data.local.db.dao.GameStateDao
import com.textgame.data.local.db.dao.NPCDao
import com.textgame.data.local.db.dao.ProtagonistDao
import com.textgame.data.local.db.dao.SessionDao
import com.textgame.data.local.db.dao.StateSnapshotDao
import com.textgame.data.local.db.dao.SummaryDao
import com.textgame.data.local.db.dao.WorldSettingDao
import com.textgame.data.local.db.entity.toDomain
import com.textgame.data.local.db.entity.toEntity
import com.textgame.domain.model.BackgroundSetting
import com.textgame.domain.model.Dialogue
import com.textgame.domain.model.GameSession
import com.textgame.domain.model.GameState
import com.textgame.domain.model.NPC
import com.textgame.domain.model.Protagonist
import com.textgame.domain.model.StateSnapshot
import com.textgame.domain.model.Summary
import com.textgame.domain.model.WorldSetting
import com.textgame.domain.repository.GameRepository
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GameRepositoryImpl(
    private val sessionDao: SessionDao,
    private val protagonistDao: ProtagonistDao,
    private val npcDao: NPCDao,
    private val gameStateDao: GameStateDao,
    private val summaryDao: SummaryDao,
    private val worldSettingDao: WorldSettingDao,
    private val backgroundSettingDao: BackgroundSettingDao,
    private val dialogueDao: DialogueDao,
    private val stateSnapshotDao: StateSnapshotDao
) : GameRepository {

    private val gson = Gson()

    override fun getAllSessions(): Flow<List<GameSession>> {
        return sessionDao.getAllSessions().map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override suspend fun getSessionById(id: Long): GameSession? {
        return sessionDao.getSessionById(id)?.toDomain()
    }

    override suspend fun createGameSession(name: String): Long {
        val now = System.currentTimeMillis()
        return sessionDao.insertSession(
            GameSession(
                name = name,
                createdAt = now,
                currentTurn = 0
            ).toEntity()
        )
    }

    override suspend fun deleteSession(id: Long) {
        protagonistDao.deleteProtagonistBySessionId(id)
        npcDao.deleteNPCsBySessionId(id)
        gameStateDao.deleteGameStateBySessionId(id)
        summaryDao.deleteSummariesBySessionId(id)
        worldSettingDao.deleteWorldSettingBySessionId(id)
        backgroundSettingDao.deleteBackgroundSettingBySessionId(id)
        dialogueDao.deleteDialoguesBySessionId(id)
        sessionDao.deleteSession(id)
    }

    override suspend fun updateCurrentTurn(sessionId: Long, turn: Int) {
        sessionDao.updateCurrentTurn(sessionId, turn)
    }

    override suspend fun getProtagonist(sessionId: Long): Protagonist? {
        return protagonistDao.getProtagonistBySessionId(sessionId)?.toDomain()
    }

    override suspend fun saveProtagonist(protagonist: Protagonist): Long {
        return if (protagonist.id == 0L) {
            protagonistDao.insertProtagonist(protagonist.toEntity())
        } else {
            protagonistDao.updateProtagonist(protagonist.toEntity())
            protagonist.id
        }
    }

    override fun getNPCs(sessionId: Long): Flow<List<NPC>> {
        return npcDao.getNPCsBySessionId(sessionId).map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override suspend fun getNPCList(sessionId: Long): List<NPC> {
        return npcDao.getNPCListBySessionId(sessionId).map { it.toDomain() }
    }

    override suspend fun getNPCById(id: Long): NPC? {
        return npcDao.getNPCById(id)?.toDomain()
    }

    override suspend fun getNPCByName(sessionId: Long, name: String): NPC? {
        return npcDao.getNPCByName(sessionId, name)?.toDomain()
    }

    override suspend fun getNPCByNpcId(sessionId: Long, npcId: String): NPC? {
        return npcDao.getNPCByNpcId(sessionId, npcId)?.toDomain()
    }

    override suspend fun saveNPC(npc: NPC): Long {
        return if (npc.id == 0L) {
            npcDao.insertNPC(npc.toEntity())
        } else {
            npcDao.updateNPC(npc.toEntity())
            npc.id
        }
    }

    override suspend fun updateNPC(npc: NPC) {
        npcDao.updateNPC(npc.toEntity())
    }

    override suspend fun deleteNPC(id: Long) {
        npcDao.deleteNPC(id)
    }

    override suspend fun getGameState(sessionId: Long): GameState? {
        return gameStateDao.getGameStateBySessionId(sessionId)?.toDomain()
    }

    override suspend fun saveGameState(gameState: GameState): Long {
        return if (gameState.id == 0L) {
            gameStateDao.insertGameState(gameState.toEntity())
        } else {
            gameStateDao.updateGameState(gameState.toEntity())
            gameState.id
        }
    }

    override suspend fun updateGameState(gameState: GameState) {
        gameStateDao.updateGameState(gameState.toEntity())
    }

    override suspend fun getLatestSummary(sessionId: Long): Summary? {
        return summaryDao.getLatestSummary(sessionId)?.toDomain()
    }

    override suspend fun saveSummary(summary: Summary): Long {
        return summaryDao.insertSummary(summary.toEntity())
    }

    override suspend fun updateSummary(summary: Summary) {
        summaryDao.updateSummary(summary.toEntity())
    }

    override suspend fun upsertSummary(summary: Summary): Long {
        return summaryDao.upsertSummary(summary.toEntity())
    }

    override suspend fun deleteSummariesAfterTurn(sessionId: Long, turn: Int) {
        summaryDao.deleteSummariesAfterTurn(sessionId, turn)
    }

    override suspend fun deleteSummariesOverlappingTurn(sessionId: Long, turn: Int) {
        summaryDao.deleteSummariesOverlappingTurn(sessionId, turn)
    }

    override suspend fun deleteAllNPCs(sessionId: Long) {
        npcDao.deleteNPCsBySessionId(sessionId)
    }

    override suspend fun getWorldSetting(sessionId: Long): WorldSetting? {
        return worldSettingDao.getWorldSettingBySessionId(sessionId)?.toDomain()
    }

    override suspend fun saveWorldSetting(setting: WorldSetting): Long {
        return if (setting.id == 0L) {
            worldSettingDao.insertWorldSetting(setting.toEntity())
        } else {
            worldSettingDao.updateWorldSetting(setting.toEntity())
            setting.id
        }
    }

    override suspend fun updateWorldSetting(setting: WorldSetting) {
        worldSettingDao.updateWorldSetting(setting.toEntity())
    }

    override suspend fun getBackgroundSetting(sessionId: Long): BackgroundSetting? {
        return backgroundSettingDao.getBackgroundSettingBySessionId(sessionId)?.toDomain()
    }

    override suspend fun saveBackgroundSetting(setting: BackgroundSetting): Long {
        return if (setting.id == 0L) {
            backgroundSettingDao.insertBackgroundSetting(setting.toEntity())
        } else {
            backgroundSettingDao.updateBackgroundSetting(setting.toEntity())
            setting.id
        }
    }

    override suspend fun updateBackgroundSetting(setting: BackgroundSetting) {
        backgroundSettingDao.updateBackgroundSetting(setting.toEntity())
    }

    override suspend fun getDialogues(sessionId: Long): List<Dialogue> {
        return dialogueDao.getDialoguesBySessionId(sessionId).map { it.toDomain() }
    }

    override suspend fun saveDialogue(dialogue: Dialogue): Long {
        return dialogueDao.insertDialogue(dialogue.toEntity())
    }

    override suspend fun updateDialogueContent(id: Long, content: String) {
        dialogueDao.updateDialogueContent(id, content)
    }

    override suspend fun deleteDialogueById(id: Long) {
        dialogueDao.deleteDialogueById(id)
    }

    override suspend fun deleteDialoguesFromTurn(sessionId: Long, fromTurn: Int) {
        dialogueDao.deleteDialoguesFromTurn(sessionId, fromTurn)
    }

    override suspend fun saveStateSnapshot(snapshot: StateSnapshot): Long {
        val entity = com.textgame.data.local.db.entity.StateSnapshotEntity(
            id = snapshot.id,
            sessionId = snapshot.sessionId,
            turnNumber = snapshot.turnNumber,
            protagonistJson = snapshot.protagonist?.let { gson.toJson(it) } ?: "",
            npcsJson = gson.toJson(snapshot.npcs),
            gameStateJson = snapshot.gameState?.let { gson.toJson(it) } ?: "",
            worldSettingJson = snapshot.worldSetting?.let { gson.toJson(it) } ?: "",
            backgroundSettingJson = snapshot.backgroundSetting?.let { gson.toJson(it) } ?: "",
            summaryJson = snapshot.summary?.let { gson.toJson(it) } ?: "",
            createdAt = snapshot.createdAt
        )
        return stateSnapshotDao.insertSnapshot(entity)
    }

    override suspend fun getStateSnapshotByTurn(sessionId: Long, turnNumber: Int): StateSnapshot? {
        val entity = stateSnapshotDao.getSnapshotByTurn(sessionId, turnNumber) ?: return null
        val protagonist = if (entity.protagonistJson.isNotEmpty()) {
            gson.fromJson(entity.protagonistJson, Protagonist::class.java)
        } else null
        val npcsType = object : TypeToken<List<NPC>>() {}.type
        val npcs: List<NPC> = gson.fromJson(entity.npcsJson, npcsType) ?: emptyList()
        val gameState = if (entity.gameStateJson.isNotEmpty()) {
            gson.fromJson(entity.gameStateJson, GameState::class.java)
        } else null
        val worldSetting = if (entity.worldSettingJson.isNotEmpty()) {
            gson.fromJson(entity.worldSettingJson, WorldSetting::class.java)
        } else null
        val backgroundSetting = if (entity.backgroundSettingJson.isNotEmpty()) {
            gson.fromJson(entity.backgroundSettingJson, BackgroundSetting::class.java)
        } else null
        val summary = if (entity.summaryJson.isNotEmpty()) {
            gson.fromJson(entity.summaryJson, Summary::class.java)
        } else null
        return StateSnapshot(
            id = entity.id,
            sessionId = entity.sessionId,
            turnNumber = entity.turnNumber,
            protagonist = protagonist,
            npcs = npcs,
            gameState = gameState,
            worldSetting = worldSetting,
            backgroundSetting = backgroundSetting,
            summary = summary,
            createdAt = entity.createdAt
        )
    }

    override suspend fun deleteStateSnapshotsFromTurn(sessionId: Long, fromTurn: Int) {
        stateSnapshotDao.deleteSnapshotsFromTurn(sessionId, fromTurn)
    }
}
