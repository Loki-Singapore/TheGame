package com.textgame.presentation.viewmodel;

@kotlin.Metadata(mv = {1, 8, 0}, k = 1, xi = 48, d1 = {"\u0000r\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0010\u000b\n\u0002\b\t\b\u0007\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J(\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u00032\u0006\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u001a2\u0006\u0010\u001c\u001a\u00020\u001dH\u0002J,\u0010\u001e\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u00032\u0006\u0010\u001b\u001a\u00020\u001a2\u0006\u0010\u001c\u001a\u00020\u001d2\n\b\u0002\u0010\u001f\u001a\u0004\u0018\u00010 H\u0002J \u0010!\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u00032\u0006\u0010\u001b\u001a\u00020\u001a2\u0006\u0010\u001c\u001a\u00020\u001dH\u0002J\u0006\u0010\"\u001a\u00020\u0017J\u000e\u0010#\u001a\u00020\u00172\u0006\u0010$\u001a\u00020\u0003J\u0016\u0010%\u001a\u00020\u00172\u0006\u0010$\u001a\u00020\u00032\u0006\u0010&\u001a\u00020\u001aJ5\u0010\'\u001a\u00020\u00172\u0006\u0010(\u001a\u00020)2\u0006\u0010\u001c\u001a\u00020\u001d2\b\b\u0002\u0010*\u001a\u00020\u00032\b\b\u0002\u0010+\u001a\u00020\u0003H\u0082@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010,J\b\u0010-\u001a\u00020\u0017H\u0002J\b\u0010.\u001a\u00020\u0017H\u0014J\u0006\u0010/\u001a\u00020\u0017J\u0006\u00100\u001a\u00020\u0017J\u0011\u00101\u001a\u00020\u0017H\u0082@\u00f8\u0001\u0000\u00a2\u0006\u0002\u00102J\u000e\u00103\u001a\u00020\u00172\u0006\u0010\u001c\u001a\u00020\u001dJE\u00104\u001a\u00020\u00032\u0006\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u001a2\u0006\u00105\u001a\u0002062\u0006\u00107\u001a\u0002062\u0006\u0010\u001c\u001a\u00020\u001d2\n\b\u0002\u0010\u001f\u001a\u0004\u0018\u00010 H\u0082@\u00f8\u0001\u0000\u00a2\u0006\u0002\u00108J\u000e\u00109\u001a\u00020\u00172\u0006\u0010:\u001a\u00020\u001aJ\u0006\u0010;\u001a\u00020\u0017J!\u0010<\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u00032\u0006\u0010\u001b\u001a\u00020\u001aH\u0082@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010=J\u0018\u0010>\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u00032\u0006\u0010\u001b\u001a\u00020\u001aH\u0002R\u0014\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\rX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u000fX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0010\u001a\u0004\u0018\u00010\u0011X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0017\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\t0\u0013\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0015\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u0006?"}, d2 = {"Lcom/textgame/presentation/viewmodel/GameViewModel;", "Landroidx/lifecycle/ViewModel;", "sessionId", "", "context", "Landroid/content/Context;", "(JLandroid/content/Context;)V", "_uiState", "Lkotlinx/coroutines/flow/MutableStateFlow;", "Lcom/textgame/presentation/viewmodel/GameUiState;", "bgmManager", "Lcom/textgame/data/audio/BgmManager;", "gameRepository", "Lcom/textgame/domain/repository/GameRepository;", "sendDialogueUseCase", "Lcom/textgame/domain/usecase/SendDialogueUseCase;", "streamingJob", "Lkotlinx/coroutines/Job;", "uiState", "Lkotlinx/coroutines/flow/StateFlow;", "getUiState", "()Lkotlinx/coroutines/flow/StateFlow;", "addNPCDialogueWithId", "", "id", "speaker", "", "content", "turnNumber", "", "addNarrativeWithId", "tokenUsage", "Lcom/textgame/domain/model/TokenUsage;", "addPlayerDialogueWithId", "consumePendingRegeneratePrompt", "deleteDialogue", "dialogueId", "editDialogue", "newContent", "handleAIResponse", "response", "Lcom/textgame/domain/model/AIResponse;", "existingNarrativeId", "existingDialogueId", "(Lcom/textgame/domain/model/AIResponse;IJJLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "loadGameData", "onCleared", "onPause", "onResume", "refreshGameData", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "regenerateFromTurn", "saveDialogueToDb", "isPlayer", "", "isNarrative", "(Ljava/lang/String;Ljava/lang/String;ZZILcom/textgame/domain/model/TokenUsage;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "sendMessage", "input", "stopBgm", "updateDialogueContent", "(JLjava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "updateDialogueDisplayContent", "app_debug"})
public final class GameViewModel extends androidx.lifecycle.ViewModel {
    private final long sessionId = 0L;
    @org.jetbrains.annotations.NotNull
    private final android.content.Context context = null;
    @org.jetbrains.annotations.NotNull
    private final com.textgame.domain.repository.GameRepository gameRepository = null;
    @org.jetbrains.annotations.NotNull
    private final com.textgame.domain.usecase.SendDialogueUseCase sendDialogueUseCase = null;
    @org.jetbrains.annotations.NotNull
    private final com.textgame.data.audio.BgmManager bgmManager = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.MutableStateFlow<com.textgame.presentation.viewmodel.GameUiState> _uiState = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.StateFlow<com.textgame.presentation.viewmodel.GameUiState> uiState = null;
    @org.jetbrains.annotations.Nullable
    private kotlinx.coroutines.Job streamingJob;
    
    public GameViewModel(long sessionId, @org.jetbrains.annotations.NotNull
    android.content.Context context) {
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
    
    private final java.lang.Object updateDialogueContent(long id, java.lang.String content, kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    private final void updateDialogueDisplayContent(long id, java.lang.String content) {
    }
    
    private final java.lang.Object handleAIResponse(com.textgame.domain.model.AIResponse response, int turnNumber, long existingNarrativeId, long existingDialogueId, kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    private final java.lang.Object saveDialogueToDb(java.lang.String speaker, java.lang.String content, boolean isPlayer, boolean isNarrative, int turnNumber, com.textgame.domain.model.TokenUsage tokenUsage, kotlin.coroutines.Continuation<? super java.lang.Long> $completion) {
        return null;
    }
    
    private final void addPlayerDialogueWithId(long id, java.lang.String content, int turnNumber) {
    }
    
    private final void addNPCDialogueWithId(long id, java.lang.String speaker, java.lang.String content, int turnNumber) {
    }
    
    private final void addNarrativeWithId(long id, java.lang.String content, int turnNumber, com.textgame.domain.model.TokenUsage tokenUsage) {
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
    
    public final void onPause() {
    }
    
    public final void onResume() {
    }
    
    public final void stopBgm() {
    }
    
    @java.lang.Override
    protected void onCleared() {
    }
}