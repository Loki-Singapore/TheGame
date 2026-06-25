package com.textgame.data.local.db.dao;

@kotlin.Metadata(mv = {1, 8, 0}, k = 1, xi = 48, d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\bg\u0018\u00002\u00020\u0001J\u0019\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0006J!\u0010\u0007\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\b\u001a\u00020\tH\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\nJ\u001b\u0010\u000b\u001a\u0004\u0018\u00010\f2\u0006\u0010\u0004\u001a\u00020\u0005H\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0006J#\u0010\r\u001a\u0004\u0018\u00010\f2\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u000e\u001a\u00020\tH\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\nJ\u0019\u0010\u000f\u001a\u00020\u00052\u0006\u0010\u0010\u001a\u00020\fH\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0011\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u0006\u0012"}, d2 = {"Lcom/textgame/data/local/db/dao/StateSnapshotDao;", "", "deleteSnapshotsBySessionId", "", "sessionId", "", "(JLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "deleteSnapshotsFromTurn", "fromTurn", "", "(JILkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getLatestSnapshot", "Lcom/textgame/data/local/db/entity/StateSnapshotEntity;", "getSnapshotByTurn", "turnNumber", "insertSnapshot", "snapshot", "(Lcom/textgame/data/local/db/entity/StateSnapshotEntity;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "app_debug"})
@androidx.room.Dao
public abstract interface StateSnapshotDao {
    
    @androidx.room.Insert
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object insertSnapshot(@org.jetbrains.annotations.NotNull
    com.textgame.data.local.db.entity.StateSnapshotEntity snapshot, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super java.lang.Long> $completion);
    
    @androidx.room.Query(value = "SELECT * FROM state_snapshots WHERE sessionId = :sessionId AND turnNumber = :turnNumber LIMIT 1")
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object getSnapshotByTurn(long sessionId, int turnNumber, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super com.textgame.data.local.db.entity.StateSnapshotEntity> $completion);
    
    @androidx.room.Query(value = "SELECT * FROM state_snapshots WHERE sessionId = :sessionId ORDER BY turnNumber DESC LIMIT 1")
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object getLatestSnapshot(long sessionId, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super com.textgame.data.local.db.entity.StateSnapshotEntity> $completion);
    
    @androidx.room.Query(value = "DELETE FROM state_snapshots WHERE sessionId = :sessionId AND turnNumber >= :fromTurn")
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object deleteSnapshotsFromTurn(long sessionId, int fromTurn, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "DELETE FROM state_snapshots WHERE sessionId = :sessionId")
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object deleteSnapshotsBySessionId(long sessionId, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
}