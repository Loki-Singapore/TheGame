package com.textgame.domain.repository;

@kotlin.Metadata(mv = {1, 8, 0}, k = 1, xi = 48, d1 = {"\u0000n\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0006\n\u0002\u0010\b\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b!\bf\u0018\u00002\u00020\u0001J\u0019\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u00a6@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0006J\u0019\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\u0003H\u00a6@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\nJ\u0019\u0010\u000b\u001a\u00020\b2\u0006\u0010\f\u001a\u00020\u0003H\u00a6@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\nJ!\u0010\r\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\u00032\u0006\u0010\u000e\u001a\u00020\u000fH\u00a6@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0010J\u0019\u0010\u0011\u001a\u00020\b2\u0006\u0010\f\u001a\u00020\u0003H\u00a6@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\nJ\u0019\u0010\u0012\u001a\u00020\b2\u0006\u0010\f\u001a\u00020\u0003H\u00a6@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\nJ!\u0010\u0013\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\u00032\u0006\u0010\u000e\u001a\u00020\u000fH\u00a6@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0010J!\u0010\u0014\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\u00032\u0006\u0010\u0015\u001a\u00020\u000fH\u00a6@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0010J!\u0010\u0016\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\u00032\u0006\u0010\u0015\u001a\u00020\u000fH\u00a6@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0010J\u0014\u0010\u0017\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u001a0\u00190\u0018H&J\u001b\u0010\u001b\u001a\u0004\u0018\u00010\u001c2\u0006\u0010\t\u001a\u00020\u0003H\u00a6@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\nJ\u001f\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\u001e0\u00192\u0006\u0010\t\u001a\u00020\u0003H\u00a6@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\nJ\u001b\u0010\u001f\u001a\u0004\u0018\u00010 2\u0006\u0010\t\u001a\u00020\u0003H\u00a6@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\nJ\u001b\u0010!\u001a\u0004\u0018\u00010\"2\u0006\u0010\t\u001a\u00020\u0003H\u00a6@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\nJ\u001b\u0010#\u001a\u0004\u0018\u00010$2\u0006\u0010\f\u001a\u00020\u0003H\u00a6@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\nJ#\u0010%\u001a\u0004\u0018\u00010$2\u0006\u0010\t\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u00a6@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010&J\u001f\u0010\'\u001a\b\u0012\u0004\u0012\u00020$0\u00192\u0006\u0010\t\u001a\u00020\u0003H\u00a6@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\nJ\u001c\u0010(\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020$0\u00190\u00182\u0006\u0010\t\u001a\u00020\u0003H&J\u001b\u0010)\u001a\u0004\u0018\u00010*2\u0006\u0010\t\u001a\u00020\u0003H\u00a6@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\nJ\u001b\u0010+\u001a\u0004\u0018\u00010\u001a2\u0006\u0010\f\u001a\u00020\u0003H\u00a6@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\nJ#\u0010,\u001a\u0004\u0018\u00010-2\u0006\u0010\t\u001a\u00020\u00032\u0006\u0010.\u001a\u00020\u000fH\u00a6@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0010J\u001b\u0010/\u001a\u0004\u0018\u0001002\u0006\u0010\t\u001a\u00020\u0003H\u00a6@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\nJ\u0019\u00101\u001a\u00020\u00032\u0006\u00102\u001a\u00020\u001cH\u00a6@\u00f8\u0001\u0000\u00a2\u0006\u0002\u00103J\u0019\u00104\u001a\u00020\u00032\u0006\u00105\u001a\u00020\u001eH\u00a6@\u00f8\u0001\u0000\u00a2\u0006\u0002\u00106J\u0019\u00107\u001a\u00020\u00032\u0006\u00108\u001a\u00020 H\u00a6@\u00f8\u0001\u0000\u00a2\u0006\u0002\u00109J\u0019\u0010:\u001a\u00020\u00032\u0006\u0010;\u001a\u00020$H\u00a6@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010<J\u0019\u0010=\u001a\u00020\u00032\u0006\u0010>\u001a\u00020*H\u00a6@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010?J\u0019\u0010@\u001a\u00020\u00032\u0006\u0010A\u001a\u00020-H\u00a6@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010BJ\u0019\u0010C\u001a\u00020\u00032\u0006\u0010D\u001a\u00020\"H\u00a6@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010EJ\u0019\u0010F\u001a\u00020\u00032\u0006\u00102\u001a\u000200H\u00a6@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010GJ\u0019\u0010H\u001a\u00020\b2\u0006\u00102\u001a\u00020\u001cH\u00a6@\u00f8\u0001\u0000\u00a2\u0006\u0002\u00103J!\u0010I\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\u00032\u0006\u0010\u0015\u001a\u00020\u000fH\u00a6@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0010J!\u0010J\u001a\u00020\b2\u0006\u0010\f\u001a\u00020\u00032\u0006\u0010K\u001a\u00020\u0005H\u00a6@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010&J\u0019\u0010L\u001a\u00020\b2\u0006\u00108\u001a\u00020 H\u00a6@\u00f8\u0001\u0000\u00a2\u0006\u0002\u00109J\u0019\u0010M\u001a\u00020\b2\u0006\u0010;\u001a\u00020$H\u00a6@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010<J\u0019\u0010N\u001a\u00020\b2\u0006\u0010D\u001a\u00020\"H\u00a6@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010EJ\u0019\u0010O\u001a\u00020\b2\u0006\u00102\u001a\u000200H\u00a6@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010GJ\u0019\u0010P\u001a\u00020\u00032\u0006\u0010D\u001a\u00020\"H\u00a6@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010E\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u0006Q"}, d2 = {"Lcom/textgame/domain/repository/GameRepository;", "", "createGameSession", "", "name", "", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "deleteAllNPCs", "", "sessionId", "(JLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "deleteDialogueById", "id", "deleteDialoguesFromTurn", "fromTurn", "", "(JILkotlin/coroutines/Continuation;)Ljava/lang/Object;", "deleteNPC", "deleteSession", "deleteStateSnapshotsFromTurn", "deleteSummariesAfterTurn", "turn", "deleteSummariesOverlappingTurn", "getAllSessions", "Lkotlinx/coroutines/flow/Flow;", "", "Lcom/textgame/domain/model/GameSession;", "getBackgroundSetting", "Lcom/textgame/domain/model/BackgroundSetting;", "getDialogues", "Lcom/textgame/domain/model/Dialogue;", "getGameState", "Lcom/textgame/domain/model/GameState;", "getLatestSummary", "Lcom/textgame/domain/model/Summary;", "getNPCById", "Lcom/textgame/domain/model/NPC;", "getNPCByName", "(JLjava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getNPCList", "getNPCs", "getProtagonist", "Lcom/textgame/domain/model/Protagonist;", "getSessionById", "getStateSnapshotByTurn", "Lcom/textgame/domain/model/StateSnapshot;", "turnNumber", "getWorldSetting", "Lcom/textgame/domain/model/WorldSetting;", "saveBackgroundSetting", "setting", "(Lcom/textgame/domain/model/BackgroundSetting;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "saveDialogue", "dialogue", "(Lcom/textgame/domain/model/Dialogue;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "saveGameState", "gameState", "(Lcom/textgame/domain/model/GameState;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "saveNPC", "npc", "(Lcom/textgame/domain/model/NPC;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "saveProtagonist", "protagonist", "(Lcom/textgame/domain/model/Protagonist;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "saveStateSnapshot", "snapshot", "(Lcom/textgame/domain/model/StateSnapshot;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "saveSummary", "summary", "(Lcom/textgame/domain/model/Summary;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "saveWorldSetting", "(Lcom/textgame/domain/model/WorldSetting;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "updateBackgroundSetting", "updateCurrentTurn", "updateDialogueContent", "content", "updateGameState", "updateNPC", "updateSummary", "updateWorldSetting", "upsertSummary", "app_debug"})
public abstract interface GameRepository {
    
    @org.jetbrains.annotations.NotNull
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.textgame.domain.model.GameSession>> getAllSessions();
    
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object getSessionById(long id, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super com.textgame.domain.model.GameSession> $completion);
    
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object createGameSession(@org.jetbrains.annotations.NotNull
    java.lang.String name, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super java.lang.Long> $completion);
    
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object deleteSession(long id, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object updateCurrentTurn(long sessionId, int turn, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object getProtagonist(long sessionId, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super com.textgame.domain.model.Protagonist> $completion);
    
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object saveProtagonist(@org.jetbrains.annotations.NotNull
    com.textgame.domain.model.Protagonist protagonist, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super java.lang.Long> $completion);
    
    @org.jetbrains.annotations.NotNull
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.textgame.domain.model.NPC>> getNPCs(long sessionId);
    
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object getNPCList(long sessionId, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super java.util.List<com.textgame.domain.model.NPC>> $completion);
    
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object getNPCById(long id, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super com.textgame.domain.model.NPC> $completion);
    
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object getNPCByName(long sessionId, @org.jetbrains.annotations.NotNull
    java.lang.String name, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super com.textgame.domain.model.NPC> $completion);
    
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object saveNPC(@org.jetbrains.annotations.NotNull
    com.textgame.domain.model.NPC npc, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super java.lang.Long> $completion);
    
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object updateNPC(@org.jetbrains.annotations.NotNull
    com.textgame.domain.model.NPC npc, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object deleteNPC(long id, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object getGameState(long sessionId, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super com.textgame.domain.model.GameState> $completion);
    
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object saveGameState(@org.jetbrains.annotations.NotNull
    com.textgame.domain.model.GameState gameState, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super java.lang.Long> $completion);
    
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object updateGameState(@org.jetbrains.annotations.NotNull
    com.textgame.domain.model.GameState gameState, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object getLatestSummary(long sessionId, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super com.textgame.domain.model.Summary> $completion);
    
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object saveSummary(@org.jetbrains.annotations.NotNull
    com.textgame.domain.model.Summary summary, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super java.lang.Long> $completion);
    
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object updateSummary(@org.jetbrains.annotations.NotNull
    com.textgame.domain.model.Summary summary, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object upsertSummary(@org.jetbrains.annotations.NotNull
    com.textgame.domain.model.Summary summary, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super java.lang.Long> $completion);
    
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object deleteSummariesAfterTurn(long sessionId, int turn, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object deleteSummariesOverlappingTurn(long sessionId, int turn, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object deleteAllNPCs(long sessionId, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object getWorldSetting(long sessionId, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super com.textgame.domain.model.WorldSetting> $completion);
    
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object saveWorldSetting(@org.jetbrains.annotations.NotNull
    com.textgame.domain.model.WorldSetting setting, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super java.lang.Long> $completion);
    
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object updateWorldSetting(@org.jetbrains.annotations.NotNull
    com.textgame.domain.model.WorldSetting setting, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object getBackgroundSetting(long sessionId, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super com.textgame.domain.model.BackgroundSetting> $completion);
    
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object saveBackgroundSetting(@org.jetbrains.annotations.NotNull
    com.textgame.domain.model.BackgroundSetting setting, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super java.lang.Long> $completion);
    
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object updateBackgroundSetting(@org.jetbrains.annotations.NotNull
    com.textgame.domain.model.BackgroundSetting setting, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object getDialogues(long sessionId, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super java.util.List<com.textgame.domain.model.Dialogue>> $completion);
    
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object saveDialogue(@org.jetbrains.annotations.NotNull
    com.textgame.domain.model.Dialogue dialogue, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super java.lang.Long> $completion);
    
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object updateDialogueContent(long id, @org.jetbrains.annotations.NotNull
    java.lang.String content, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object deleteDialogueById(long id, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object deleteDialoguesFromTurn(long sessionId, int fromTurn, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object saveStateSnapshot(@org.jetbrains.annotations.NotNull
    com.textgame.domain.model.StateSnapshot snapshot, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super java.lang.Long> $completion);
    
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object getStateSnapshotByTurn(long sessionId, int turnNumber, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super com.textgame.domain.model.StateSnapshot> $completion);
    
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object deleteStateSnapshotsFromTurn(long sessionId, int fromTurn, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
}