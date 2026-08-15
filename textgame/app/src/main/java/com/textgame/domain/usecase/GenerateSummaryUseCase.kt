package com.textgame.domain.usecase

import com.textgame.data.remote.ai.AIService
import com.textgame.domain.model.Summary
import com.textgame.domain.repository.GameRepository
import com.textgame.i18n.Lang

class GenerateSummaryUseCase(
    private val gameRepository: GameRepository,
    private val aiService: AIService
) {
    private var nextSummaryInterval: Int = (28..32).random()

    fun shouldGenerateSummary(currentTurn: Int, lastSummaryTurn: Int): Boolean {
        if (lastSummaryTurn == 0 && currentTurn >= 30) {
            // 首次总结：从第30轮开始
            nextSummaryInterval = (28..32).random()
            return true
        }
        if (currentTurn - lastSummaryTurn >= nextSummaryInterval) {
            nextSummaryInterval = (28..32).random()
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

        val recentDialogues = getRecentDialogues(sessionId, lastSummary)

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

    private suspend fun getRecentDialogues(
        sessionId: Long,
        lastSummary: Summary?
    ): List<String> {
        val allDialogues = gameRepository.getDialogues(sessionId)
        val lastSummarizedTurn = lastSummary?.turnRangeEnd ?: 0
        // 总结间隔已放宽到约30轮，固定"最近40条"会漏掉两次总结之间的早期对话。
        // 改为按轮次截取自上次总结以来的完整记录，保证总结覆盖全部间隔内容。
        return allDialogues
            .filter { it.turnNumber > lastSummarizedTurn }
            .map { dialogue ->
                val prefix = when {
                    dialogue.isNarrative -> Lang.text("【Narrator】", "【旁白】")
                    dialogue.isPlayer -> Lang.text("【Player】", "【玩家】")
                    else -> "【${dialogue.speaker}】"
                }
                "$prefix${dialogue.content}"
            }
    }
}
