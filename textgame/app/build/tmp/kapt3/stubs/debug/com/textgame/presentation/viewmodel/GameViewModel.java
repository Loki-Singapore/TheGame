package com.textgame.presentation.viewmodel;

@kotlin.Metadata(mv = {1, 8, 0}, k = 1, xi = 48, d1 = {"\u0000V\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u000b\n\u0002\b\u0005\b\u0007\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J \u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u00132\u0006\u0010\u0015\u001a\u00020\u0016H\u0002J\u0018\u0010\u0017\u001a\u00020\u00112\u0006\u0010\u0014\u001a\u00020\u00132\u0006\u0010\u0015\u001a\u00020\u0016H\u0002J\u0018\u0010\u0018\u001a\u00020\u00112\u0006\u0010\u0014\u001a\u00020\u00132\u0006\u0010\u0015\u001a\u00020\u0016H\u0002J\u0006\u0010\u0019\u001a\u00020\u0011J\u000e\u0010\u001a\u001a\u00020\u00112\u0006\u0010\u001b\u001a\u00020\u0003J\u0016\u0010\u001c\u001a\u00020\u00112\u0006\u0010\u001b\u001a\u00020\u00032\u0006\u0010\u001d\u001a\u00020\u0013J!\u0010\u001e\u001a\u00020\u00112\u0006\u0010\u001f\u001a\u00020 2\u0006\u0010\u0015\u001a\u00020\u0016H\u0082@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010!J\b\u0010\"\u001a\u00020\u0011H\u0002J\u0011\u0010#\u001a\u00020\u0011H\u0082@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010$J\u000e\u0010%\u001a\u00020\u00112\u0006\u0010\u0015\u001a\u00020\u0016J9\u0010&\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u00132\u0006\u0010\'\u001a\u00020(2\u0006\u0010)\u001a\u00020(2\u0006\u0010\u0015\u001a\u00020\u0016H\u0082@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010*J\u000e\u0010+\u001a\u00020\u00112\u0006\u0010,\u001a\u00020\u0013R\u0014\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u00070\r\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000f\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u0006-"}, d2 = {"Lcom/textgame/presentation/viewmodel/GameViewModel;", "Landroidx/lifecycle/ViewModel;", "sessionId", "", "(J)V", "_uiState", "Lkotlinx/coroutines/flow/MutableStateFlow;", "Lcom/textgame/presentation/viewmodel/GameUiState;", "gameRepository", "Lcom/textgame/domain/repository/GameRepository;", "sendDialogueUseCase", "Lcom/textgame/domain/usecase/SendDialogueUseCase;", "uiState", "Lkotlinx/coroutines/flow/StateFlow;", "getUiState", "()Lkotlinx/coroutines/flow/StateFlow;", "addNPCDialogue", "", "speaker", "", "content", "turnNumber", "", "addNarrative", "addPlayerDialogue", "consumePendingRegeneratePrompt", "deleteDialogue", "dialogueId", "editDialogue", "newContent", "handleAIResponse", "response", "Lcom/textgame/domain/model/AIResponse;", "(Lcom/textgame/domain/model/AIResponse;ILkotlin/coroutines/Continuation;)Ljava/lang/Object;", "loadGameData", "refreshGameData", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "regenerateFromTurn", "saveDialogueToDb", "isPlayer", "", "isNarrative", "(Ljava/lang/String;Ljava/lang/String;ZZILkotlin/coroutines/Continuation;)Ljava/lang/Object;", "sendMessage", "input", "app_debug"})
public final class GameViewModel extends androidx.lifecycle.ViewModel {
    private final long sessionId = 0L;
    @org.jetbrains.annotations.NotNull
    private final com.textgame.domain.repository.GameRepository gameRepository = null;
    @org.jetbrains.annotations.NotNull
    private final com.textgame.domain.usecase.SendDialogueUseCase sendDialogueUseCase = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.MutableStateFlow<com.textgame.presentation.viewmodel.GameUiState> _uiState = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.StateFlow<com.textgame.presentation.viewmodel.GameUiState> uiState = null;
    
    public GameViewModel(long sessionId) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.flow.StateFlow<com.textgame.presentation.viewmodel.GameUiState> getUiState() {
        return null;
    }
    
    private final void loadGameData() {
    }
    
    public final void sendMessage(@org.jetbrains.annotations.NotNull
    java.lang.String input) {
    }
    
    private final java.lang.Object handleAIResponse(com.textgame.domain.model.AIResponse response, int turnNumber, kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    private final java.lang.Object saveDialogueToDb(java.lang.String speaker, java.lang.String content, boolean isPlayer, boolean isNarrative, int turnNumber, kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    private final void addPlayerDialogue(java.lang.String content, int turnNumber) {
    }
    
    private final void addNPCDialogue(java.lang.String speaker, java.lang.String content, int turnNumber) {
    }
    
    private final void addNarrative(java.lang.String content, int turnNumber) {
    }
    
    private final java.lang.Object refreshGameData(kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    public final void deleteDialogue(long dialogueId) {
    }
    
    public final void editDialogue(long dialogueId, @org.jetbrains.annotations.NotNull
    java.lang.String newContent) {
    }
    
    public final void regenerateFromTurn(int turnNumber) {
    }
    
    public final void consumePendingRegeneratePrompt() {
    }
}