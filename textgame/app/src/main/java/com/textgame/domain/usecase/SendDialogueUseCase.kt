package com.textgame.domain.usecase

import com.textgame.data.remote.ai.AIService
import com.textgame.domain.model.AIResponse
import com.textgame.domain.model.Dialogue
import com.textgame.domain.model.StateSnapshot
import com.textgame.domain.repository.GameRepository

class SendDialogueUseCase(
    private val gameRepository: GameRepository,
    private val aiService: AIService,
    private val updateStateUseCase: UpdateStateUseCase,
    private val generateSummaryUseCase: GenerateSummaryUseCase,
    private val syncSettingsUseCase: SyncSettingsUseCase
) {
    suspend fun execute(sessionId: Long, userInput: String): AIResponse {
        val worldSetting = gameRepository.getWorldSetting(sessionId)
            ?: throw IllegalStateException("World setting not found")
        val backgroundSetting = gameRepository.getBackgroundSetting(sessionId)
            ?: throw IllegalStateException("Background setting not found")
        val protagonist = gameRepository.getProtagonist(sessionId)
            ?: throw IllegalStateException("Protagonist not found")
        val npcs = gameRepository.getNPCList(sessionId)
        val gameState = gameRepository.getGameState(sessionId)
            ?: throw IllegalStateException("Game state not found")
        val latestSummary = gameRepository.getLatestSummary(sessionId)

        // 保存当前状态快照（该轮开始前的状态）
        val snapshot = StateSnapshot(
            sessionId = sessionId,
            turnNumber = gameState.turnCount + 1,
            protagonist = protagonist,
            npcs = npcs,
            gameState = gameState,
            worldSetting = worldSetting,
            backgroundSetting = backgroundSetting,
            summary = latestSummary
        )
        gameRepository.saveStateSnapshot(snapshot)

        val allDialogues = gameRepository.getDialogues(sessionId)
        val (preSummaryDialogues, postSummaryDialogues) = buildDialogueHistory(
            allDialogues, latestSummary
        )

        val aiResponse = aiService.generateDialogueResponse(
            worldSetting = worldSetting,
            backgroundSetting = backgroundSetting,
            summary = latestSummary,
            preSummaryDialogues = preSummaryDialogues,
            postSummaryDialogues = postSummaryDialogues,
            protagonist = protagonist,
            npcs = npcs,
            gameState = gameState,
            userInput = userInput
        )

        val newTurn = gameState.turnCount + 1
        gameRepository.updateCurrentTurn(sessionId, newTurn)

        updateStateUseCase.execute(sessionId, aiResponse, userInput)

        val updatedGameState = gameRepository.getGameState(sessionId) ?: gameState
        val shouldGenerateSummary = generateSummaryUseCase.shouldGenerateSummary(
            currentTurn = newTurn,
            lastSummaryTurn = latestSummary?.turnRangeEnd ?: 0
        )

        if (shouldGenerateSummary) {
            generateSummaryUseCase.execute(sessionId)
        }

        syncSettingsUseCase.execute(sessionId, aiResponse)

        return aiResponse
    }

    private fun buildDialogueHistory(
        allDialogues: List<Dialogue>,
        latestSummary: com.textgame.domain.model.Summary?
    ): Pair<List<String>, List<String>> {
        val formatDialogue: (Dialogue) -> String = { dialogue ->
            val prefix = when {
                dialogue.isNarrative -> "【旁白】"
                dialogue.isPlayer -> "【玩家】"
                else -> "【${dialogue.speaker}】"
            }
            "$prefix${dialogue.content}"
        }

        return if (latestSummary != null && latestSummary.turnRangeEnd > 0) {
            // 有总结：分pre和post两部分
            val preSummaryRaw = allDialogues.filter {
                it.turnNumber > latestSummary.turnRangeEnd - 10
                        && it.turnNumber <= latestSummary.turnRangeEnd
            }
            val preSummaryDialogues = preSummaryRaw.map(formatDialogue)

            val postSummaryRaw = allDialogues.filter {
                it.turnNumber > latestSummary.turnRangeEnd
            }
            val postSummaryDialogues = postSummaryRaw.map(formatDialogue)

            Pair(preSummaryDialogues, postSummaryDialogues)
        } else {
            // 无总结：pre为空，post为全部对话
            Pair(emptyList(), allDialogues.map(formatDialogue))
        }
    }
}
