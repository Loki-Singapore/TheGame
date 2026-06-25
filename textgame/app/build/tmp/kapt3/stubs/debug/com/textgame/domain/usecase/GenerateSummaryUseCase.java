package com.textgame.domain.usecase;

@kotlin.Metadata(mv = {1, 8, 0}, k = 1, xi = 48, d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\b\u0007\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J\u0019\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fH\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\rJ\u001f\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00100\u000f2\u0006\u0010\u000b\u001a\u00020\fH\u0082@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\rJ\u0016\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\b2\u0006\u0010\u0014\u001a\u00020\bR\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u000e\u00a2\u0006\u0002\n\u0000\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u0006\u0015"}, d2 = {"Lcom/textgame/domain/usecase/GenerateSummaryUseCase;", "", "gameRepository", "Lcom/textgame/domain/repository/GameRepository;", "aiService", "Lcom/textgame/data/remote/ai/AIService;", "(Lcom/textgame/domain/repository/GameRepository;Lcom/textgame/data/remote/ai/AIService;)V", "nextSummaryInterval", "", "execute", "Lcom/textgame/domain/model/Summary;", "sessionId", "", "(JLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getRecentDialogues", "", "", "shouldGenerateSummary", "", "currentTurn", "lastSummaryTurn", "app_debug"})
public final class GenerateSummaryUseCase {
    @org.jetbrains.annotations.NotNull
    private final com.textgame.domain.repository.GameRepository gameRepository = null;
    @org.jetbrains.annotations.NotNull
    private final com.textgame.data.remote.ai.AIService aiService = null;
    private int nextSummaryInterval;
    
    public GenerateSummaryUseCase(@org.jetbrains.annotations.NotNull
    com.textgame.domain.repository.GameRepository gameRepository, @org.jetbrains.annotations.NotNull
    com.textgame.data.remote.ai.AIService aiService) {
        super();
    }
    
    public final boolean shouldGenerateSummary(int currentTurn, int lastSummaryTurn) {
        return false;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.Object execute(long sessionId, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super com.textgame.domain.model.Summary> $completion) {
        return null;
    }
    
    private final java.lang.Object getRecentDialogues(long sessionId, kotlin.coroutines.Continuation<? super java.util.List<java.lang.String>> $completion) {
        return null;
    }
}