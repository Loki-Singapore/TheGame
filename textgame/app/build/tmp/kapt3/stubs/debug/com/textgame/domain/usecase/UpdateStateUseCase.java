package com.textgame.domain.usecase;

@kotlin.Metadata(mv = {1, 8, 0}, k = 1, xi = 48, d1 = {"\u0000T\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J)\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fH\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\rJ*\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u000f2\b\u0010\u0011\u001a\u0004\u0018\u00010\u00122\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\u0013\u001a\u00020\bH\u0002J \u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u00152\u0006\u0010\u0011\u001a\u00020\u00172\u0006\u0010\u0013\u001a\u00020\bH\u0002J \u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u00192\u0006\u0010\u0011\u001a\u00020\u001b2\u0006\u0010\u0013\u001a\u00020\bH\u0002R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u0006\u001c"}, d2 = {"Lcom/textgame/domain/usecase/UpdateStateUseCase;", "", "gameRepository", "Lcom/textgame/domain/repository/GameRepository;", "(Lcom/textgame/domain/repository/GameRepository;)V", "execute", "", "sessionId", "", "aiResponse", "Lcom/textgame/domain/model/AIResponse;", "userInput", "", "(JLcom/textgame/domain/model/AIResponse;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "updateGameState", "Lcom/textgame/domain/model/GameState;", "gameState", "changes", "Lcom/textgame/domain/model/GameChanges;", "now", "updateNPC", "Lcom/textgame/domain/model/NPC;", "npc", "Lcom/textgame/domain/model/NPCChanges;", "updateProtagonist", "Lcom/textgame/domain/model/Protagonist;", "protagonist", "Lcom/textgame/domain/model/ProtagonistChanges;", "app_debug"})
public final class UpdateStateUseCase {
    @org.jetbrains.annotations.NotNull
    private final com.textgame.domain.repository.GameRepository gameRepository = null;
    
    public UpdateStateUseCase(@org.jetbrains.annotations.NotNull
    com.textgame.domain.repository.GameRepository gameRepository) {
        super();
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.Object execute(long sessionId, @org.jetbrains.annotations.NotNull
    com.textgame.domain.model.AIResponse aiResponse, @org.jetbrains.annotations.NotNull
    java.lang.String userInput, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    private final com.textgame.domain.model.Protagonist updateProtagonist(com.textgame.domain.model.Protagonist protagonist, com.textgame.domain.model.ProtagonistChanges changes, long now) {
        return null;
    }
    
    private final com.textgame.domain.model.NPC updateNPC(com.textgame.domain.model.NPC npc, com.textgame.domain.model.NPCChanges changes, long now) {
        return null;
    }
    
    private final com.textgame.domain.model.GameState updateGameState(com.textgame.domain.model.GameState gameState, com.textgame.domain.model.GameChanges changes, java.lang.String userInput, long now) {
        return null;
    }
}