package com.textgame.domain.usecase

import com.textgame.data.remote.ai.AIService
import com.textgame.domain.model.Summary
import com.textgame.domain.repository.GameRepository
import kotlin.random.Random

class GenerateSummaryUseCase(
    private val gameRepository: GameRepository,
    private val aiService: AIService
) {
    private var nextSummaryInterval: Int = (10..20).random()

    fun shouldGenerateSummary(currentTurn: Int, lastSummaryTurn: Int): Boolean {
        if (lastSummaryTurn == 0 && currentTurn >= 10) {
            // 首次总结：从第10轮开始
            nextSummaryInterval = (10..20).random()
            return true
        }
        if (currentTurn - lastSummaryTurn >= nextSummaryInterval) {
            nextSummaryInterval = (10..20).random()
            return true
        }
        return false
    }

    suspend fun execute(sessionId: Long): Summary {
        val worldSetting = gameRepository.getWorldSetting(sessionId)
            ?: throw IllegalStateException("World setting not found")
        val protagonist = gameRepository.getProtagonist(sessionId)
            ?: throw IllegalStateException("Protagonist not found")
        val npcs = gameRepository.getNPCList(sessionId)
        val gameState = gameRepository.getGameState(sessionId)
            ?: throw IllegalStateException("Game state not found")
        val lastSummary = gameRepository.getLatestSummary(sessionId)

        val recentDialogues = getRecentDialogues(sessionId)

        val summary = aiService.generateSummary(
            worldSetting = worldSetting,
            recentDialogues = recentDialogues,
            protagonist = protagonist,
            npcs = npcs,
            gameState = gameState,
            previousSummary = lastSummary
        )

        val summaryWithRange = summary.copy(
            sessionId = sessionId,
            turnRangeStart = (lastSummary?.turnRangeEnd ?: 0) + 1,
            turnRangeEnd = gameState.turnCount,
            generatedAt = System.currentTimeMillis()
        )

        gameRepository.saveSummary(summaryWithRange)

        return summaryWithRange
    }

    private suspend fun getRecentDialogues(sessionId: Long): List<String> {
        val allDialogues = gameRepository.getDialogues(sessionId)
        return allDialogues.takeLast(40).map { dialogue ->
            val prefix = when {
                dialogue.isNarrative -> "【旁白】"
                dialogue.isPlayer -> "【玩家】"
                else -> "【${dialogue.speaker}】"
            }
            "$prefix${dialogue.content}"
        }
    }
}
