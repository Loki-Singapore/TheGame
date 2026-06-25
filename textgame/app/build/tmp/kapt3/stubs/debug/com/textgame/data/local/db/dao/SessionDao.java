package com.textgame.data.local.db.dao;

@kotlin.Metadata(mv = {1, 8, 0}, k = 1, xi = 48, d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\b\n\u0002\b\u0003\bg\u0018\u00002\u00020\u0001J\u0019\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0006J\u0014\u0010\u0007\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\n0\t0\bH\'J\u001b\u0010\u000b\u001a\u0004\u0018\u00010\n2\u0006\u0010\u0004\u001a\u00020\u0005H\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0006J\u0019\u0010\f\u001a\u00020\u00052\u0006\u0010\r\u001a\u00020\nH\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u000eJ!\u0010\u000f\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0010\u001a\u00020\u0011H\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0012J\u0019\u0010\u0013\u001a\u00020\u00032\u0006\u0010\r\u001a\u00020\nH\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u000e\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u0006\u0014"}, d2 = {"Lcom/textgame/data/local/db/dao/SessionDao;", "", "deleteSession", "", "id", "", "(JLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getAllSessions", "Lkotlinx/coroutines/flow/Flow;", "", "Lcom/textgame/data/local/db/entity/GameSessionEntity;", "getSessionById", "insertSession", "session", "(Lcom/textgame/data/local/db/entity/GameSessionEntity;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "updateCurrentTurn", "turn", "", "(JILkotlin/coroutines/Continuation;)Ljava/lang/Object;", "updateSession", "app_debug"})
@androidx.room.Dao
public abstract interface SessionDao {
    
    @androidx.room.Query(value = "SELECT * FROM game_sessions ORDER BY createdAt DESC")
    @org.jetbrains.annotations.NotNull
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.textgame.data.local.db.entity.GameSessionEntity>> getAllSessions();
    
    @androidx.room.Query(value = "SELECT * FROM game_sessions WHERE id = :id")
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object getSessionById(long id, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super com.textgame.data.local.db.entity.GameSessionEntity> $completion);
    
    @androidx.room.Insert
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object insertSession(@org.jetbrains.annotations.NotNull
    com.textgame.data.local.db.entity.GameSessionEntity session, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super java.lang.Long> $completion);
    
    @androidx.room.Update
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object updateSession(@org.jetbrains.annotations.NotNull
    com.textgame.data.local.db.entity.GameSessionEntity session, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "DELETE FROM game_sessions WHERE id = :id")
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object deleteSession(long id, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "UPDATE game_sessions SET currentTurn = :turn WHERE id = :id")
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object updateCurrentTurn(long id, int turn, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
}