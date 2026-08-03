package com.textgame.domain.usecase

import com.textgame.data.remote.ai.AIService
import com.textgame.data.remote.ai.ImagePromptStyle
import com.textgame.domain.repository.GameRepository

/**
 * 根据当前游戏场景生成生图提示词。
 * 从仓库读取游戏状态、世界观、主角、NPC，结合本回合场景叙述，
 * 调用对话 AI 生成一段用于文生图模型的提示词。
 */
class GenerateImagePromptUseCase(
    private val gameRepository: GameRepository,
    private val aiService: AIService
) {
    suspend fun execute(
        sessionId: Long,
        sceneNarrative: String,
        style: ImagePromptStyle
    ): String {
        val worldSetting = gameRepository.getWorldSetting(sessionId)
        val protagonist = gameRepository.getProtagonist(sessionId)
        val npcs = gameRepository.getNPCList(sessionId)
        val gameState = gameRepository.getGameState(sessionId)

        return aiService.generateImagePrompt(
            worldSetting = worldSetting,
            protagonist = protagonist,
            npcs = npcs,
            gameState = gameState,
            sceneNarrative = sceneNarrative,
            style = style
        )
    }
}
