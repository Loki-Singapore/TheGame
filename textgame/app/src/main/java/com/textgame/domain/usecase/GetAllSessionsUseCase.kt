package com.textgame.domain.usecase

import com.textgame.domain.model.GameSession
import com.textgame.domain.repository.GameRepository
import kotlinx.coroutines.flow.Flow

class GetAllSessionsUseCase(
    private val gameRepository: GameRepository
) {
    fun execute(): Flow<List<GameSession>> {
        return gameRepository.getAllSessions()
    }
}
