package com.textgame.domain.usecase

import com.textgame.data.remote.ai.AIService
import com.textgame.domain.model.AIResponse
import com.textgame.domain.model.Dialogue
import com.textgame.domain.model.DirectorDirective
import com.textgame.domain.model.StateSnapshot
import com.textgame.domain.model.StreamingChunk
import com.textgame.domain.repository.GameRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class SendDialogueUseCase(
    private val gameRepository: GameRepository,
    private val aiService: AIService,
    private val updateStateUseCase: UpdateStateUseCase,
    private val generateSummaryUseCase: GenerateSummaryUseCase,
    private val syncSettingsUseCase: SyncSettingsUseCase
) {
    suspend fun execute(sessionId: Long, userInput: String, turnNumber: Int): AIResponse {
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

        // 保存当前状态快照（该轮开始前的状态）。
        // turnNumber 由调用方传入，与玩家对话、AI对话使用同一个值，
        // 避免快照turnNumber与对话turnNumber因取自不同数据源(UI vs DB)而错位，
        // 进而导致"从此轮重新生成"时找不到快照、回退到上一轮。
        val snapshot = StateSnapshot(
            sessionId = sessionId,
            turnNumber = turnNumber,
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

        val directive = DirectorDirective.roll(turnNumber, npcs)

        val aiResponse = aiService.generateDialogueResponse(
            worldSetting = worldSetting,
            backgroundSetting = backgroundSetting,
            summary = latestSummary,
            preSummaryDialogues = preSummaryDialogues,
            postSummaryDialogues = postSummaryDialogues,
            protagonist = protagonist,
            npcs = npcs,
            gameState = gameState,
            userInput = userInput,
            directorDirective = directive
        )

        gameRepository.updateCurrentTurn(sessionId, turnNumber)

        updateStateUseCase.execute(sessionId, aiResponse, userInput)

        val updatedGameState = gameRepository.getGameState(sessionId) ?: gameState
        val shouldGenerateSummary = generateSummaryUseCase.shouldGenerateSummary(
            currentTurn = turnNumber,
            lastSummaryTurn = latestSummary?.turnRangeEnd ?: 0
        )

        if (shouldGenerateSummary) {
            generateSummaryUseCase.execute(sessionId)
        }

        syncSettingsUseCase.execute(sessionId, aiResponse)

        return aiResponse
    }

    fun executeStream(sessionId: Long, userInput: String, turnNumber: Int): Flow<StreamingChunk> = flow {
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

        // turnNumber 由调用方传入，与玩家对话、AI对话使用同一个值，
        // 避免快照turnNumber与对话turnNumber因取自不同数据源(UI vs DB)而错位。
        val snapshot = StateSnapshot(
            sessionId = sessionId,
            turnNumber = turnNumber,
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

        val directive = DirectorDirective.roll(turnNumber, npcs)

        val flow = aiService.streamDialogueResponse(
            worldSetting = worldSetting,
            backgroundSetting = backgroundSetting,
            summary = latestSummary,
            preSummaryDialogues = preSummaryDialogues,
            postSummaryDialogues = postSummaryDialogues,
            protagonist = protagonist,
            npcs = npcs,
            gameState = gameState,
            userInput = userInput,
            directorDirective = directive
        )

        flow.collect { chunk ->
            emit(chunk)
            if (chunk is StreamingChunk.Complete) {
                val aiResponse = chunk.response
                gameRepository.updateCurrentTurn(sessionId, turnNumber)

                updateStateUseCase.execute(sessionId, aiResponse, userInput)

                val updatedGameState = gameRepository.getGameState(sessionId) ?: gameState
                val shouldGenerateSummary = generateSummaryUseCase.shouldGenerateSummary(
                    currentTurn = turnNumber,
                    lastSummaryTurn = latestSummary?.turnRangeEnd ?: 0
                )

                if (shouldGenerateSummary) {
                    generateSummaryUseCase.execute(sessionId)
                }

                syncSettingsUseCase.execute(sessionId, aiResponse)
            }
        }
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
