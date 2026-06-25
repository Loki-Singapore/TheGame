package com.textgame.data.local.db.dao;

@kotlin.Metadata(mv = {1, 8, 0}, k = 1, xi = 48, d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\bg\u0018\u00002\u00020\u0001J\u0019\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0006J\u001b\u0010\u0007\u001a\u0004\u0018\u00010\b2\u0006\u0010\u0004\u001a\u00020\u0005H\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0006J\u0019\u0010\t\u001a\u00020\u00052\u0006\u0010\n\u001a\u00020\bH\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u000bJ\u0019\u0010\f\u001a\u00020\u00032\u0006\u0010\n\u001a\u00020\bH\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u000b\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u0006\r"}, d2 = {"Lcom/textgame/data/local/db/dao/WorldSettingDao;", "", "deleteWorldSettingBySessionId", "", "sessionId", "", "(JLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getWorldSettingBySessionId", "Lcom/textgame/data/local/db/entity/WorldSettingEntity;", "insertWorldSetting", "setting", "(Lcom/textgame/data/local/db/entity/WorldSettingEntity;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "updateWorldSetting", "app_debug"})
@androidx.room.Dao
public abstract interface WorldSettingDao {
    
    @androidx.room.Query(value = "SELECT * FROM world_settings WHERE sessionId = :sessionId LIMIT 1")
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object getWorldSettingBySessionId(long sessionId, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super com.textgame.data.local.db.entity.WorldSettingEntity> $completion);
    
    @androidx.room.Insert
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object insertWorldSetting(@org.jetbrains.annotations.NotNull
    com.textgame.data.local.db.entity.WorldSettingEntity setting, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super java.lang.Long> $completion);
    
    @androidx.room.Update
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object updateWorldSetting(@org.jetbrains.annotations.NotNull
    com.textgame.data.local.db.entity.WorldSettingEntity setting, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "DELETE FROM world_settings WHERE sessionId = :sessionId")
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object deleteWorldSettingBySessionId(long sessionId, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
}