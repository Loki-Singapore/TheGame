package com.textgame.presentation.viewmodel;

@kotlin.Metadata(mv = {1, 8, 0}, k = 1, xi = 48, d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\t\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u000e\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u0010R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001d\u0010\u0007\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\n0\t0\b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\f\u00a8\u0006\u0011"}, d2 = {"Lcom/textgame/presentation/viewmodel/MainViewModel;", "Landroidx/lifecycle/ViewModel;", "()V", "deleteSessionUseCase", "Lcom/textgame/domain/usecase/DeleteSessionUseCase;", "getAllSessionsUseCase", "Lcom/textgame/domain/usecase/GetAllSessionsUseCase;", "sessions", "Lkotlinx/coroutines/flow/StateFlow;", "", "Lcom/textgame/domain/model/GameSession;", "getSessions", "()Lkotlinx/coroutines/flow/StateFlow;", "deleteSession", "", "sessionId", "", "app_debug"})
public final class MainViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull
    private final com.textgame.domain.usecase.GetAllSessionsUseCase getAllSessionsUseCase = null;
    @org.jetbrains.annotations.NotNull
    private final com.textgame.domain.usecase.DeleteSessionUseCase deleteSessionUseCase = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.StateFlow<java.util.List<com.textgame.domain.model.GameSession>> sessions = null;
    
    public MainViewModel() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.flow.StateFlow<java.util.List<com.textgame.domain.model.GameSession>> getSessions() {
        return null;
    }
    
    public final void deleteSession(long sessionId) {
    }
}