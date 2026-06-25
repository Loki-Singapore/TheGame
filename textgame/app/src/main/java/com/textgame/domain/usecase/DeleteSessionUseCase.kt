package com.textgame.domain.usecase

import com.textgame.domain.repository.GameRepository

class DeleteSessionUseCase(
    private val gameRepository: GameRepository
) {
    suspend fun execute(sessionId: Long) {
        gameRepository.deleteSession(sessionId)
    }
}
