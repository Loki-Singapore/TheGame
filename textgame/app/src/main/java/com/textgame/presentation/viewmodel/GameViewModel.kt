package com.textgame.presentation.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.textgame.data.audio.BgmTrack
import com.textgame.data.audio.BgmManager
import com.textgame.di.AppModule
import com.textgame.domain.model.AIResponse
import com.textgame.domain.model.BackgroundSetting
import com.textgame.domain.model.Dialogue
import com.textgame.domain.model.GameState
import com.textgame.domain.model.NPC
import com.textgame.domain.model.Protagonist
import com.textgame.domain.model.StreamingChunk
import com.textgame.domain.model.Summary
import com.textgame.domain.model.WorldSetting
import com.textgame.domain.repository.GameRepository
import com.textgame.domain.usecase.SendDialogueUseCase
import com.textgame.service.StreamingForegroundService
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class GameUiState(
    val protagonist: Protagonist? = null,
    val npcs: List<NPC> = emptyList(),
    val gameState: GameState? = null,
    val summary: Summary? = null,
    val worldSetting: WorldSetting? = null,
    val backgroundSetting: BackgroundSetting? = null,
    val dialogues: List<DialogueDisplay> = emptyList(),
    val choices: List<String> = emptyList(),
    val isLoading: Boolean = false,
    val isStreaming: Boolean = false,
    val error: String? = null,
    val pendingRegeneratePrompt: String? = null
)

data class DialogueDisplay(
    val id: Long = 0,
    val turnNumber: Int = 0,
    val speaker: String,
    val content: String,
    val isPlayer: Boolean = false,
    val isNarrative: Boolean = false,
    val tokenUsage: com.textgame.domain.model.TokenUsage? = null
)

class GameViewModel(
    private val sessionId: Long,
    private val context: Context
) : ViewModel() {
    private val gameRepository: GameRepository = AppModule.getGameRepository()
    private val sendDialogueUseCase: SendDialogueUseCase = AppModule.getSendDialogueUseCase()
    private val bgmManager: BgmManager = BgmManager.getInstance(context)

    private val _uiState = MutableStateFlow(GameUiState())
    val uiState: StateFlow<GameUiState> = _uiState.asStateFlow()

    private var streamingJob: Job? = null

    init {
        loadGameData()
    }

    private fun loadGameData() {
        viewModelScope.launch {
            try {
                val protagonist = gameRepository.getProtagonist(sessionId)
                val npcs = gameRepository.getNPCList(sessionId)
                val gameState = gameRepository.getGameState(sessionId)
                val summary = gameRepository.getLatestSummary(sessionId)
                val worldSetting = gameRepository.getWorldSetting(sessionId)
                val backgroundSetting = gameRepository.getBackgroundSetting(sessionId)
                val dialogues = gameRepository.getDialogues(sessionId)

                val dialogueDisplays = dialogues.map { DialogueDisplay(
                    id = it.id,
                    turnNumber = it.turnNumber,
                    speaker = it.speaker,
                    content = it.content,
                    isPlayer = it.isPlayer,
                    isNarrative = it.isNarrative
                ) }

                _uiState.value = _uiState.value.copy(
                    protagonist = protagonist,
                    npcs = npcs,
                    gameState = gameState,
                    summary = summary,
                    worldSetting = worldSetting,
                    backgroundSetting = backgroundSetting,
                    dialogues = dialogueDisplays
                )

                if (gameState != null && gameState.turnCount == 0 && dialogueDisplays.isEmpty()) {
                    val narrative = "游戏开始！你发现自己身处${gameState.currentScene}..."
                    val newId = saveDialogueToDb(
                        speaker = "",
                        content = narrative,
                        isPlayer = false,
                        isNarrative = true,
                        turnNumber = 0
                    )
                    addNarrativeWithId(newId, narrative, 0)
                }
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(error = e.message)
            }
        }
    }

    fun sendMessage(input: String) {
        if (input.isBlank() || _uiState.value.isLoading || _uiState.value.isStreaming) return

        streamingJob = viewModelScope.launch {
            // 从 DB 读 gameState 算 nextTurn，不依赖 UI 状态（UI 可能 stale），
            // 确保玩家对话 turnNumber 与快照 turnNumber 一致。
            val dbGameState = gameRepository.getGameState(sessionId)
            val currentTurn = dbGameState?.turnCount ?: 0
            val nextTurn = currentTurn + 1

            _uiState.value = _uiState.value.copy(isStreaming = true, isLoading = true, error = null, choices = emptyList())

            val playerId = saveDialogueToDb(
                speaker = "你",
                content = input,
                isPlayer = true,
                isNarrative = false,
                turnNumber = nextTurn
            )
            addPlayerDialogueWithId(playerId, input, nextTurn)

            var narrativeId: Long = 0
            var dialogueId: Long = 0
            var currentNarrative = StringBuilder()
            var currentDialogue = StringBuilder()

            // Keep the process in a foreground state while streaming so that switching
            // apps does not let the OS kill the streaming HTTP socket. The service hosts
            // a low-priority notification and is stopped in finally / onCleared.
            StreamingForegroundService.start(context)
            try {
                val flow = sendDialogueUseCase.executeStream(sessionId, input, nextTurn)
                flow.collect { chunk ->
                    when (chunk) {
                        is StreamingChunk.NarrativeDelta -> {
                            currentNarrative.append(chunk.delta)
                            if (narrativeId == 0L) {
                                narrativeId = saveDialogueToDb(
                                    speaker = "",
                                    content = currentNarrative.toString(),
                                    isPlayer = false,
                                    isNarrative = true,
                                    turnNumber = nextTurn
                                )
                                addNarrativeWithId(narrativeId, currentNarrative.toString(), nextTurn)
                            } else {
                                updateDialogueContent(narrativeId, currentNarrative.toString())
                                updateDialogueDisplayContent(narrativeId, currentNarrative.toString())
                            }
                        }
                        is StreamingChunk.DialogueDelta -> {
                            currentDialogue.append(chunk.delta)
                            if (dialogueId == 0L) {
                                val npcName = _uiState.value.npcs.firstOrNull()?.name ?: "NPC"
                                dialogueId = saveDialogueToDb(
                                    speaker = npcName,
                                    content = currentDialogue.toString(),
                                    isPlayer = false,
                                    isNarrative = false,
                                    turnNumber = nextTurn
                                )
                                addNPCDialogueWithId(dialogueId, npcName, currentDialogue.toString(), nextTurn)
                            } else {
                                updateDialogueContent(dialogueId, currentDialogue.toString())
                                updateDialogueDisplayContent(dialogueId, currentDialogue.toString())
                            }
                        }
                        is StreamingChunk.Complete -> {
                            handleAIResponse(chunk.response, nextTurn, narrativeId, dialogueId)
                            refreshGameData()
                            _uiState.value = _uiState.value.copy(isStreaming = false, isLoading = false)
                        }
                        is StreamingChunk.Error -> {
                            _uiState.value = _uiState.value.copy(
                                error = "AI响应错误: ${chunk.message}",
                                isStreaming = false,
                                isLoading = false
                            )
                        }
                    }
                }
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    error = "AI响应错误: ${e.message}",
                    isStreaming = false,
                    isLoading = false
                )
            } finally {
                StreamingForegroundService.stop(context)
            }
        }
    }

    private suspend fun updateDialogueContent(id: Long, content: String) {
        gameRepository.updateDialogueContent(id, content)
    }

    private fun updateDialogueDisplayContent(id: Long, content: String) {
        val newDialogues = _uiState.value.dialogues.map {
            if (it.id == id) it.copy(content = content) else it
        }
        _uiState.value = _uiState.value.copy(dialogues = newDialogues)
    }

    private suspend fun handleAIResponse(response: AIResponse, turnNumber: Int, existingNarrativeId: Long = 0, existingDialogueId: Long = 0) {
        response.bgm?.let { bgmKeyword ->
            val track = BgmTrack.fromKeyword(bgmKeyword)
            if (track != null) {
                bgmManager.play(track)
            }
        }

        if (response.dialogue.isNotEmpty()) {
            val npcName = _uiState.value.npcs.firstOrNull()?.name ?: "NPC"
            if (existingDialogueId == 0L) {
                val newId = saveDialogueToDb(
                    speaker = npcName,
                    content = response.dialogue,
                    isPlayer = false,
                    isNarrative = false,
                    turnNumber = turnNumber
                )
                addNPCDialogueWithId(newId, npcName, response.dialogue, turnNumber)
            } else {
                updateDialogueContent(existingDialogueId, response.dialogue)
                updateDialogueDisplayContent(existingDialogueId, response.dialogue)
            }
        }
        if (response.narrative.isNotEmpty()) {
            if (existingNarrativeId == 0L) {
                val newId = saveDialogueToDb(
                    speaker = "",
                    content = response.narrative,
                    isPlayer = false,
                    isNarrative = true,
                    turnNumber = turnNumber,
                    tokenUsage = response.tokenUsage
                )
                addNarrativeWithId(newId, response.narrative, turnNumber, response.tokenUsage)
            } else {
                updateDialogueContent(existingNarrativeId, response.narrative)
                updateDialogueDisplayContent(existingNarrativeId, response.narrative)
                val newDialogues = _uiState.value.dialogues.map {
                    if (it.id == existingNarrativeId) it.copy(tokenUsage = response.tokenUsage) else it
                }
                _uiState.value = _uiState.value.copy(dialogues = newDialogues)
            }
        }
        val newChoices = response.choices ?: emptyList()
        _uiState.value = _uiState.value.copy(choices = newChoices)
    }

    private suspend fun saveDialogueToDb(
        speaker: String,
        content: String,
        isPlayer: Boolean,
        isNarrative: Boolean,
        turnNumber: Int,
        tokenUsage: com.textgame.domain.model.TokenUsage? = null
    ): Long {
        val dialogue = Dialogue(
            sessionId = sessionId,
            turnNumber = turnNumber,
            speaker = speaker,
            content = content,
            isPlayer = isPlayer,
            isNarrative = isNarrative,
            createdAt = System.currentTimeMillis()
        )
        return gameRepository.saveDialogue(dialogue)
    }

    private fun addPlayerDialogueWithId(id: Long, content: String, turnNumber: Int) {
        val newDialogues = _uiState.value.dialogues + DialogueDisplay(
            id = id,
            speaker = "你",
            content = content,
            isPlayer = true,
            turnNumber = turnNumber
        )
        _uiState.value = _uiState.value.copy(dialogues = newDialogues)
    }

    private fun addNPCDialogueWithId(id: Long, speaker: String, content: String, turnNumber: Int) {
        val newDialogues = _uiState.value.dialogues + DialogueDisplay(
            id = id,
            speaker = speaker,
            content = content,
            isPlayer = false,
            turnNumber = turnNumber
        )
        _uiState.value = _uiState.value.copy(dialogues = newDialogues)
    }

    private fun addNarrativeWithId(id: Long, content: String, turnNumber: Int, tokenUsage: com.textgame.domain.model.TokenUsage? = null) {
        val newDialogues = _uiState.value.dialogues + DialogueDisplay(
            id = id,
            speaker = "",
            content = content,
            isNarrative = true,
            turnNumber = turnNumber,
            tokenUsage = tokenUsage
        )
        _uiState.value = _uiState.value.copy(dialogues = newDialogues)
    }

    private suspend fun refreshGameData() {
        val protagonist = gameRepository.getProtagonist(sessionId)
        val npcs = gameRepository.getNPCList(sessionId)
        val gameState = gameRepository.getGameState(sessionId)
        val summary = gameRepository.getLatestSummary(sessionId)
        val worldSetting = gameRepository.getWorldSetting(sessionId)

        _uiState.value = _uiState.value.copy(
            protagonist = protagonist,
            npcs = npcs,
            gameState = gameState,
            summary = summary,
            worldSetting = worldSetting
        )
    }

    fun deleteDialogue(dialogueId: Long) {
        viewModelScope.launch {
            try {
                gameRepository.deleteDialogueById(dialogueId)
                val updatedDialogues = _uiState.value.dialogues.filter { it.id != dialogueId }
                _uiState.value = _uiState.value.copy(dialogues = updatedDialogues)
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(error = "删除失败: ${e.message}")
            }
        }
    }

    fun editDialogue(dialogueId: Long, newContent: String) {
        viewModelScope.launch {
            try {
                gameRepository.updateDialogueContent(dialogueId, newContent)
                val updatedDialogues = _uiState.value.dialogues.map {
                    if (it.id == dialogueId) it.copy(content = newContent) else it
                }
                _uiState.value = _uiState.value.copy(dialogues = updatedDialogues)
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(error = "修改失败: ${e.message}")
            }
        }
    }

    fun regenerateFromTurn(turnNumber: Int) {
        streamingJob?.cancel()
        viewModelScope.launch {
            try {
                android.util.Log.d("RegenDebug", "regenerateFromTurn called with turnNumber=$turnNumber")

                if (turnNumber <= 0) {
                    _uiState.value = _uiState.value.copy(error = "该轮次无法重新生成")
                    return@launch
                }

                val allDialoguesBefore = gameRepository.getDialogues(sessionId)
                android.util.Log.d("RegenDebug", "Dialogues before delete: " +
                    allDialoguesBefore.joinToString { "turn${it.turnNumber}(${if (it.isPlayer) "P" else if (it.isNarrative) "N" else "D"})" })

                val snapshot = gameRepository.getStateSnapshotByTurn(sessionId, turnNumber)
                android.util.Log.d("RegenDebug", "snapshot for turn $turnNumber: ${if (snapshot != null) "found, snapshot.turnCount=${snapshot.gameState?.turnCount}" else "NULL"}")
                if (snapshot == null) {
                    _uiState.value = _uiState.value.copy(
                        error = "找不到轮次 $turnNumber 的状态快照，无法重新生成"
                    )
                    return@launch
                }

                snapshot.protagonist?.let { gameRepository.saveProtagonist(it) }
                snapshot.gameState?.let { gs ->
                    gameRepository.updateGameState(gs.copy(turnCount = turnNumber - 1))
                }
                snapshot.worldSetting?.let { gameRepository.updateWorldSetting(it) }
                snapshot.backgroundSetting?.let { gameRepository.updateBackgroundSetting(it) }

                gameRepository.deleteSummariesOverlappingTurn(sessionId, turnNumber)
                val summary = snapshot.summary
                if (summary != null) {
                    gameRepository.upsertSummary(summary)
                }

                gameRepository.deleteAllNPCs(sessionId)
                snapshot.npcs.forEach { npc ->
                    gameRepository.saveNPC(npc.copy(id = 0))
                }

                val pendingPrompt = allDialoguesBefore
                    .filter { it.turnNumber == turnNumber && it.isPlayer }
                    .firstOrNull()?.content
                android.util.Log.d("RegenDebug", "pendingPrompt=${pendingPrompt?.take(30)}")

                gameRepository.deleteDialoguesFromTurn(sessionId, turnNumber)
                gameRepository.deleteStateSnapshotsFromTurn(sessionId, turnNumber)

                val dbDialogues = gameRepository.getDialogues(sessionId)
                android.util.Log.d("RegenDebug", "Dialogues after delete: " +
                    dbDialogues.joinToString { "turn${it.turnNumber}(${if (it.isPlayer) "P" else if (it.isNarrative) "N" else "D"})" })

                val dialogueDisplays = dbDialogues.map {
                    DialogueDisplay(
                        id = it.id,
                        turnNumber = it.turnNumber,
                        speaker = it.speaker,
                        content = it.content,
                        isPlayer = it.isPlayer,
                        isNarrative = it.isNarrative
                    )
                }

                refreshGameData()

                _uiState.value = _uiState.value.copy(
                    dialogues = dialogueDisplays,
                    choices = emptyList(),
                    isStreaming = false,
                    isLoading = false,
                    pendingRegeneratePrompt = pendingPrompt
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(error = "重新生成失败: ${e.message}")
            }
        }
    }

    fun consumePendingRegeneratePrompt() {
        if (_uiState.value.pendingRegeneratePrompt != null) {
            _uiState.value = _uiState.value.copy(pendingRegeneratePrompt = null)
        }
    }

    fun onPause() {
        bgmManager.pause()
    }

    fun onResume() {
        bgmManager.resume()
    }

    fun stopBgm() {
        bgmManager.stop()
    }

    override fun onCleared() {
        super.onCleared()
        streamingJob?.cancel()
        // Safety net: ensure the foreground service is released if the ViewModel is
        // torn down (e.g. user navigates away mid-stream) and the finally block above
        // did not get a chance to run.
        StreamingForegroundService.stop(context)
    }
}
