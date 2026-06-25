package com.textgame.data.local.db.dao;

@kotlin.Metadata(mv = {1, 8, 0}, k = 1, xi = 48, d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\bg\u0018\u00002\u00020\u0001J\u0019\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0006J\u0019\u0010\u0007\u001a\u00020\u00032\u0006\u0010\b\u001a\u00020\u0005H\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0006J\u001b\u0010\t\u001a\u0004\u0018\u00010\n2\u0006\u0010\u0004\u001a\u00020\u0005H\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0006J#\u0010\u000b\u001a\u0004\u0018\u00010\n2\u0006\u0010\b\u001a\u00020\u00052\u0006\u0010\f\u001a\u00020\rH\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u000eJ\u001f\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\n0\u00102\u0006\u0010\b\u001a\u00020\u0005H\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0006J\u001c\u0010\u0011\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\n0\u00100\u00122\u0006\u0010\b\u001a\u00020\u0005H\'J\u0019\u0010\u0013\u001a\u00020\u00052\u0006\u0010\u0014\u001a\u00020\nH\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0015J\u0019\u0010\u0016\u001a\u00020\u00032\u0006\u0010\u0014\u001a\u00020\nH\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0015\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u0006\u0017"}, d2 = {"Lcom/textgame/data/local/db/dao/NPCDao;", "", "deleteNPC", "", "id", "", "(JLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "deleteNPCsBySessionId", "sessionId", "getNPCById", "Lcom/textgame/data/local/db/entity/NPCStateEntity;", "getNPCByName", "name", "", "(JLjava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getNPCListBySessionId", "", "getNPCsBySessionId", "Lkotlinx/coroutines/flow/Flow;", "insertNPC", "npc", "(Lcom/textgame/data/local/db/entity/NPCStateEntity;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "updateNPC", "app_debug"})
@androidx.room.Dao
public abstract interface NPCDao {
    
    @androidx.room.Query(value = "SELECT * FROM npc_states WHERE sessionId = :sessionId")
    @org.jetbrains.annotations.NotNull
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.textgame.data.local.db.entity.NPCStateEntity>> getNPCsBySessionId(long sessionId);
    
    @androidx.room.Query(value = "SELECT * FROM npc_states WHERE sessionId = :sessionId")
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object getNPCListBySessionId(long sessionId, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super java.util.List<com.textgame.data.local.db.entity.NPCStateEntity>> $completion);
    
    @androidx.room.Query(value = "SELECT * FROM npc_states WHERE id = :id")
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object getNPCById(long id, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super com.textgame.data.local.db.entity.NPCStateEntity> $completion);
    
    @androidx.room.Query(value = "SELECT * FROM npc_states WHERE sessionId = :sessionId AND name = :name LIMIT 1")
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object getNPCByName(long sessionId, @org.jetbrains.annotations.NotNull
    java.lang.String name, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super com.textgame.data.local.db.entity.NPCStateEntity> $completion);
    
    @androidx.room.Insert
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object insertNPC(@org.jetbrains.annotations.NotNull
    com.textgame.data.local.db.entity.NPCStateEntity npc, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super java.lang.Long> $completion);
    
    @androidx.room.Update
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object updateNPC(@org.jetbrains.annotations.NotNull
    com.textgame.data.local.db.entity.NPCStateEntity npc, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "DELETE FROM npc_states WHERE id = :id")
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object deleteNPC(long id, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "DELETE FROM npc_states WHERE sessionId = :sessionId")
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object deleteNPCsBySessionId(long sessionId, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
}