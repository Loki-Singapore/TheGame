package com.textgame.presentation.viewmodel;

@kotlin.Metadata(mv = {1, 8, 0}, k = 1, xi = 48, d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0007\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001d\u0010\u0005\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u00070\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n\u00a8\u0006\u000b"}, d2 = {"Lcom/textgame/presentation/viewmodel/MainViewModel;", "Landroidx/lifecycle/ViewModel;", "()V", "getAllSessionsUseCase", "Lcom/textgame/domain/usecase/GetAllSessionsUseCase;", "sessions", "Lkotlinx/coroutines/flow/StateFlow;", "", "Lcom/textgame/domain/model/GameSession;", "getSessions", "()Lkotlinx/coroutines/flow/StateFlow;", "app_debug"})
public final class MainViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull
    private final com.textgame.domain.usecase.GetAllSessionsUseCase getAllSessionsUseCase = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.StateFlow<java.util.List<com.textgame.domain.model.GameSession>> sessions = null;
    
    public MainViewModel() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.flow.StateFlow<java.util.List<com.textgame.domain.model.GameSession>> getSessions() {
        return null;
    }
}