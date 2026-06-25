package com.textgame.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.textgame.di.AppModule
import com.textgame.domain.model.GameSession
import com.textgame.domain.usecase.DeleteSessionUseCase
import com.textgame.domain.usecase.GetAllSessionsUseCase
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    private val getAllSessionsUseCase: GetAllSessionsUseCase = AppModule.getGetAllSessionsUseCase()
    private val deleteSessionUseCase: DeleteSessionUseCase = AppModule.getDeleteSessionUseCase()

    val sessions: StateFlow<List<GameSession>> = getAllSessionsUseCase.execute()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    fun deleteSession(sessionId: Long) {
        viewModelScope.launch {
            deleteSessionUseCase.execute(sessionId)
        }
    }
}
