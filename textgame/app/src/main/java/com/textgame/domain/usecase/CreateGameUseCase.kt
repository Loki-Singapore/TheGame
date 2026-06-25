package com.textgame.domain.usecase

import com.textgame.domain.model.GameSession
import com.textgame.domain.model.NPC
import com.textgame.domain.model.Protagonist
import com.textgame.domain.model.GameState
import com.textgame.domain.model.WorldSetting
import com.textgame.domain.model.BackgroundSetting
import com.textgame.domain.repository.GameRepository

class CreateGameUseCase(
    private val gameRepository: GameRepository
) {
    suspend fun execute(
        gameName: String,
        protagonistName: String,
        worldSetting: WorldSetting,
        backgroundSetting: BackgroundSetting,
        initialNPCs: List<NPC>,
        initialProtagonist: Protagonist? = null
    ): Long {
        val sessionId = gameRepository.createGameSession(gameName)
        val now = System.currentTimeMillis()

        val protagonist = initialProtagonist?.copy(
            sessionId = sessionId,
            createdAt = now,
            updatedAt = now
        ) ?: Protagonist(
            sessionId = sessionId,
            name = protagonistName,
            location = worldSetting.locations.firstOrNull() ?: "",
            createdAt = now,
            updatedAt = now
        )
        gameRepository.saveProtagonist(protagonist)

        initialNPCs.forEach { npc ->
            gameRepository.saveNPC(
                npc.copy(
                    sessionId = sessionId,
                    updatedAt = now
                )
            )
        }

        val gameState = GameState(
            sessionId = sessionId,
            currentScene = worldSetting.locations.firstOrNull() ?: "初始场景",
            turnCount = 0,
            updatedAt = now
        )
        gameRepository.saveGameState(gameState)

        gameRepository.saveWorldSetting(
            worldSetting.copy(
                sessionId = sessionId,
                updatedAt = now
            )
        )

        gameRepository.saveBackgroundSetting(
            backgroundSetting.copy(
                sessionId = sessionId,
                updatedAt = now
            )
        )

        return sessionId
    }
}
