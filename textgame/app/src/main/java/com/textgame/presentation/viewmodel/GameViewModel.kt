package com.textgame.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.textgame.di.AppModule
import com.textgame.domain.model.AIResponse
import com.textgame.domain.model.BackgroundSetting
import com.textgame.domain.model.Dialogue
import com.textgame.domain.model.GameState
import com.textgame.domain.model.NPC
import com.textgame.domain.model.Protagonist
import com.textgame.domain.model.Summary
import com.textgame.domain.model.WorldSetting
import com.textgame.domain.repository.GameRepository
import com.textgame.domain.usecase.SendDialogueUseCase
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
    private val sessionId: Long
) : ViewModel() {
    private val gameRepository: GameRepository = AppModule.getGameRepository()
    private val sendDialogueUseCase: SendDialogueUseCase = AppModule.getSendDialogueUseCase()

    private val _uiState = MutableStateFlow(GameUiState())
    val uiState: StateFlow<GameUiState> = _uiState.asStateFlow()

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
                    addNarrative(narrative, 0)
                    saveDialogueToDb(
                        speaker = "",
                        content = narrative,
                        isPlayer = false,
                        isNarrative = true,
                        turnNumber = 0
                    )
                }
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(error = e.message)
            }
        }
    }

    fun sendMessage(input: String) {
        if (input.isBlank() || _uiState.value.isLoading) return

        val currentTurn = _uiState.value.gameState?.turnCount ?: 0
        val nextTurn = currentTurn + 1

        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)

            addPlayerDialogue(input, nextTurn)
            saveDialogueToDb(
                speaker = "你",
                content = input,
                isPlayer = true,
                isNarrative = false,
                turnNumber = nextTurn
            )

            try {
                val response = sendDialogueUseCase.execute(sessionId, input)
                handleAIResponse(response, nextTurn)
                refreshGameData()
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    error = "AI响应错误: ${e.message}",
                    isLoading = false
                )
            }
        }
    }

    private suspend fun handleAIResponse(response: AIResponse, turnNumber: Int) {
        if (response.dialogue.isNotEmpty()) {
            val npcName = _uiState.value.npcs.firstOrNull()?.name ?: "NPC"
            addNPCDialogue(npcName, response.dialogue, turnNumber)
            saveDialogueToDb(
                speaker = npcName,
                content = response.dialogue,
                isPlayer = false,
                isNarrative = false,
                turnNumber = turnNumber
            )
        }
        if (response.narrative.isNotEmpty()) {
            addNarrative(response.narrative, turnNumber, response.tokenUsage)
            saveDialogueToDb(
                speaker = "",
                content = response.narrative,
                isPlayer = false,
                isNarrative = true,
                turnNumber = turnNumber
            )
        }
        val newChoices = response.choices ?: emptyList()
        _uiState.value = _uiState.value.copy(isLoading = false, choices = newChoices)
    }

    private suspend fun saveDialogueToDb(
        speaker: String,
        content: String,
        isPlayer: Boolean,
        isNarrative: Boolean,
        turnNumber: Int
    ) {
        val dialogue = Dialogue(
            sessionId = sessionId,
            turnNumber = turnNumber,
            speaker = speaker,
            content = content,
            isPlayer = isPlayer,
            isNarrative = isNarrative,
            createdAt = System.currentTimeMillis()
        )
        gameRepository.saveDialogue(dialogue)
    }

    private fun addPlayerDialogue(content: String, turnNumber: Int) {
        val newDialogues = _uiState.value.dialogues + DialogueDisplay(
            speaker = "你",
            content = content,
            isPlayer = true,
            turnNumber = turnNumber
        )
        _uiState.value = _uiState.value.copy(dialogues = newDialogues)
    }

    private fun addNPCDialogue(speaker: String, content: String, turnNumber: Int) {
        val newDialogues = _uiState.value.dialogues + DialogueDisplay(
            speaker = speaker,
            content = content,
            isPlayer = false,
            turnNumber = turnNumber
        )
        _uiState.value = _uiState.value.copy(dialogues = newDialogues)
    }

    private fun addNarrative(content: String, turnNumber: Int, tokenUsage: com.textgame.domain.model.TokenUsage? = null) {
        val newDialogues = _uiState.value.dialogues + DialogueDisplay(
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
        viewModelScope.launch {
            try {
                // 尝试精确匹配该轮的快照
                var snapshot = gameRepository.getStateSnapshotByTurn(sessionId, turnNumber)

                // 如果没有精确匹配，尝试找该轮之前最近的快照
                if (snapshot == null) {
                    val allDialogues = gameRepository.getDialogues(sessionId)
                    val previousTurns = allDialogues.map { it.turnNumber }.filter { it < turnNumber }.toSet()
                    for (prevTurn in previousTurns.sortedDescending()) {
                        snapshot = gameRepository.getStateSnapshotByTurn(sessionId, prevTurn)
                        if (snapshot != null) break
                    }
                }

                if (snapshot != null) {
                    snapshot.protagonist?.let { gameRepository.saveProtagonist(it) }
                    snapshot.gameState?.let { gameRepository.updateGameState(it) }
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
                }

                val regeneratingDialogues = gameRepository.getDialogues(sessionId)
                    .filter { it.turnNumber >= turnNumber && it.isPlayer }
                    .sortedBy { it.turnNumber }
                val pendingPrompt = regeneratingDialogues.firstOrNull()?.content
                    ?: regeneratingDialogues.lastOrNull()?.content

                gameRepository.deleteDialoguesFromTurn(sessionId, turnNumber)
                gameRepository.deleteStateSnapshotsFromTurn(sessionId, turnNumber)

                val updatedDialogues = _uiState.value.dialogues.filter { it.turnNumber < turnNumber }
                _uiState.value = _uiState.value.copy(
                    dialogues = updatedDialogues,
                    choices = emptyList(),
                    pendingRegeneratePrompt = pendingPrompt
                )
                refreshGameData()
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
}
