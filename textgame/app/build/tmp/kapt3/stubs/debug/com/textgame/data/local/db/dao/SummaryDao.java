package com.textgame.data.local.db.dao;

@kotlin.Metadata(mv = {1, 8, 0}, k = 1, xi = 48, d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0007\bg\u0018\u00002\u00020\u0001J!\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0007H\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\bJ\u0019\u0010\t\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\nJ!\u0010\u000b\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0007H\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\bJ\u001f\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u000e0\r2\u0006\u0010\u0004\u001a\u00020\u0005H\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\nJ\u001b\u0010\u000f\u001a\u0004\u0018\u00010\u000e2\u0006\u0010\u0004\u001a\u00020\u0005H\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\nJ\u0019\u0010\u0010\u001a\u00020\u00052\u0006\u0010\u0011\u001a\u00020\u000eH\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0012J\u0019\u0010\u0013\u001a\u00020\u00032\u0006\u0010\u0011\u001a\u00020\u000eH\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0012J\u0019\u0010\u0014\u001a\u00020\u00052\u0006\u0010\u0011\u001a\u00020\u000eH\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0012\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u0006\u0015"}, d2 = {"Lcom/textgame/data/local/db/dao/SummaryDao;", "", "deleteSummariesAfterTurn", "", "sessionId", "", "turn", "", "(JILkotlin/coroutines/Continuation;)Ljava/lang/Object;", "deleteSummariesBySessionId", "(JLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "deleteSummariesOverlappingTurn", "getAllSummaries", "", "Lcom/textgame/data/local/db/entity/SummaryEntity;", "getLatestSummary", "insertSummary", "summary", "(Lcom/textgame/data/local/db/entity/SummaryEntity;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "updateSummary", "upsertSummary", "app_debug"})
@androidx.room.Dao
public abstract interface SummaryDao {
    
    @androidx.room.Query(value = "SELECT * FROM summaries WHERE sessionId = :sessionId ORDER BY generatedAt DESC LIMIT 1")
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object getLatestSummary(long sessionId, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super com.textgame.data.local.db.entity.SummaryEntity> $completion);
    
    @androidx.room.Query(value = "SELECT * FROM summaries WHERE sessionId = :sessionId ORDER BY generatedAt DESC")
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object getAllSummaries(long sessionId, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super java.util.List<com.textgame.data.local.db.entity.SummaryEntity>> $completion);
    
    @androidx.room.Insert
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object insertSummary(@org.jetbrains.annotations.NotNull
    com.textgame.data.local.db.entity.SummaryEntity summary, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super java.lang.Long> $completion);
    
    @androidx.room.Update
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object updateSummary(@org.jetbrains.annotations.NotNull
    com.textgame.data.local.db.entity.SummaryEntity summary, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Upsert
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object upsertSummary(@org.jetbrains.annotations.NotNull
    com.textgame.data.local.db.entity.SummaryEntity summary, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super java.lang.Long> $completion);
    
    @androidx.room.Query(value = "DELETE FROM summaries WHERE sessionId = :sessionId")
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object deleteSummariesBySessionId(long sessionId, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "DELETE FROM summaries WHERE sessionId = :sessionId AND turnRangeStart > :turn")
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object deleteSummariesAfterTurn(long sessionId, int turn, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "DELETE FROM summaries WHERE sessionId = :sessionId AND turnRangeEnd > :turn")
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object deleteSummariesOverlappingTurn(long sessionId, int turn, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
}