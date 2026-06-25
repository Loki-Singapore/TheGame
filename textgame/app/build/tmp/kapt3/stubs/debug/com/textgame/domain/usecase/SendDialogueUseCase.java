package com.textgame.domain.usecase;

@kotlin.Metadata(mv = {1, 8, 0}, k = 1, xi = 48, d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\b\u0007\u0018\u00002\u00020\u0001B-\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\u0006\u0010\n\u001a\u00020\u000b\u00a2\u0006\u0002\u0010\fJ!\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0012H\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0013R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u0006\u0014"}, d2 = {"Lcom/textgame/domain/usecase/SendDialogueUseCase;", "", "gameRepository", "Lcom/textgame/domain/repository/GameRepository;", "aiService", "Lcom/textgame/data/remote/ai/AIService;", "updateStateUseCase", "Lcom/textgame/domain/usecase/UpdateStateUseCase;", "generateSummaryUseCase", "Lcom/textgame/domain/usecase/GenerateSummaryUseCase;", "syncSettingsUseCase", "Lcom/textgame/domain/usecase/SyncSettingsUseCase;", "(Lcom/textgame/domain/repository/GameRepository;Lcom/textgame/data/remote/ai/AIService;Lcom/textgame/domain/usecase/UpdateStateUseCase;Lcom/textgame/domain/usecase/GenerateSummaryUseCase;Lcom/textgame/domain/usecase/SyncSettingsUseCase;)V", "execute", "Lcom/textgame/domain/model/AIResponse;", "sessionId", "", "userInput", "", "(JLjava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "app_debug"})
public final class SendDialogueUseCase {
    @org.jetbrains.annotations.NotNull
    private final com.textgame.domain.repository.GameRepository gameRepository = null;
    @org.jetbrains.annotations.NotNull
    private final com.textgame.data.remote.ai.AIService aiService = null;
    @org.jetbrains.annotations.NotNull
    private final com.textgame.domain.usecase.UpdateStateUseCase updateStateUseCase = null;
    @org.jetbrains.annotations.NotNull
    private final com.textgame.domain.usecase.GenerateSummaryUseCase generateSummaryUseCase = null;
    @org.jetbrains.annotations.NotNull
    private final com.textgame.domain.usecase.SyncSettingsUseCase syncSettingsUseCase = null;
    
    public SendDialogueUseCase(@org.jetbrains.annotations.NotNull
    com.textgame.domain.repository.GameRepository gameRepository, @org.jetbrains.annotations.NotNull
    com.textgame.data.remote.ai.AIService aiService, @org.jetbrains.annotations.NotNull
    com.textgame.domain.usecase.UpdateStateUseCase updateStateUseCase, @org.jetbrains.annotations.NotNull
    com.textgame.domain.usecase.GenerateSummaryUseCase generateSummaryUseCase, @org.jetbrains.annotations.NotNull
    com.textgame.domain.usecase.SyncSettingsUseCase syncSettingsUseCase) {
        super();
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.Object execute(long sessionId, @org.jetbrains.annotations.NotNull
    java.lang.String userInput, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super com.textgame.domain.model.AIResponse> $completion) {
        return null;
    }
}