package com.textgame.data.local.db.entity;

@kotlin.Metadata(mv = {1, 8, 0}, k = 1, xi = 48, d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0002\b \n\u0002\u0010\u000b\n\u0002\b\u0004\b\u0087\b\u0018\u00002\u00020\u0001Bi\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0005\u001a\u00020\u0006\u0012\b\b\u0002\u0010\u0007\u001a\u00020\b\u0012\b\b\u0002\u0010\t\u001a\u00020\b\u0012\b\b\u0002\u0010\n\u001a\u00020\b\u0012\b\b\u0002\u0010\u000b\u001a\u00020\b\u0012\b\b\u0002\u0010\f\u001a\u00020\b\u0012\b\b\u0002\u0010\r\u001a\u00020\b\u0012\b\b\u0002\u0010\u000e\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u000fJ\t\u0010\u001d\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u001e\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u001f\u001a\u00020\u0003H\u00c6\u0003J\t\u0010 \u001a\u00020\u0006H\u00c6\u0003J\t\u0010!\u001a\u00020\bH\u00c6\u0003J\t\u0010\"\u001a\u00020\bH\u00c6\u0003J\t\u0010#\u001a\u00020\bH\u00c6\u0003J\t\u0010$\u001a\u00020\bH\u00c6\u0003J\t\u0010%\u001a\u00020\bH\u00c6\u0003J\t\u0010&\u001a\u00020\bH\u00c6\u0003Jm\u0010\'\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00062\b\b\u0002\u0010\u0007\u001a\u00020\b2\b\b\u0002\u0010\t\u001a\u00020\b2\b\b\u0002\u0010\n\u001a\u00020\b2\b\b\u0002\u0010\u000b\u001a\u00020\b2\b\b\u0002\u0010\f\u001a\u00020\b2\b\b\u0002\u0010\r\u001a\u00020\b2\b\b\u0002\u0010\u000e\u001a\u00020\u0003H\u00c6\u0001J\u0013\u0010(\u001a\u00020)2\b\u0010*\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010+\u001a\u00020\u0006H\u00d6\u0001J\t\u0010,\u001a\u00020\bH\u00d6\u0001R\u0011\u0010\f\u001a\u00020\b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u0011\u0010\u000e\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013R\u0011\u0010\n\u001a\u00020\b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0011R\u0016\u0010\u0002\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0013R\u0011\u0010\t\u001a\u00020\b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0011R\u0011\u0010\u0007\u001a\u00020\b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0011R\u0011\u0010\u0004\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0013R\u0011\u0010\r\u001a\u00020\b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u0011R\u0011\u0010\u0005\u001a\u00020\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u001bR\u0011\u0010\u000b\u001a\u00020\b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u0011\u00a8\u0006-"}, d2 = {"Lcom/textgame/data/local/db/entity/StateSnapshotEntity;", "", "id", "", "sessionId", "turnNumber", "", "protagonistJson", "", "npcsJson", "gameStateJson", "worldSettingJson", "backgroundSettingJson", "summaryJson", "createdAt", "(JJILjava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;J)V", "getBackgroundSettingJson", "()Ljava/lang/String;", "getCreatedAt", "()J", "getGameStateJson", "getId", "getNpcsJson", "getProtagonistJson", "getSessionId", "getSummaryJson", "getTurnNumber", "()I", "getWorldSettingJson", "component1", "component10", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "equals", "", "other", "hashCode", "toString", "app_debug"})
@androidx.room.Entity(tableName = "state_snapshots")
public final class StateSnapshotEntity {
    @androidx.room.PrimaryKey(autoGenerate = true)
    private final long id = 0L;
    private final long sessionId = 0L;
    private final int turnNumber = 0;
    @org.jetbrains.annotations.NotNull
    private final java.lang.String protagonistJson = null;
    @org.jetbrains.annotations.NotNull
    private final java.lang.String npcsJson = null;
    @org.jetbrains.annotations.NotNull
    private final java.lang.String gameStateJson = null;
    @org.jetbrains.annotations.NotNull
    private final java.lang.String worldSettingJson = null;
    @org.jetbrains.annotations.NotNull
    private final java.lang.String backgroundSettingJson = null;
    @org.jetbrains.annotations.NotNull
    private final java.lang.String summaryJson = null;
    private final long createdAt = 0L;
    
    public StateSnapshotEntity(long id, long sessionId, int turnNumber, @org.jetbrains.annotations.NotNull
    java.lang.String protagonistJson, @org.jetbrains.annotations.NotNull
    java.lang.String npcsJson, @org.jetbrains.annotations.NotNull
    java.lang.String gameStateJson, @org.jetbrains.annotations.NotNull
    java.lang.String worldSettingJson, @org.jetbrains.annotations.NotNull
    java.lang.String backgroundSettingJson, @org.jetbrains.annotations.NotNull
    java.lang.String summaryJson, long createdAt) {
        super();
    }
    
    public final long getId() {
        return 0L;
    }
    
    public final long getSessionId() {
        return 0L;
    }
    
    public final int getTurnNumber() {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String getProtagonistJson() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String getNpcsJson() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String getGameStateJson() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String getWorldSettingJson() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String getBackgroundSettingJson() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String getSummaryJson() {
        return null;
    }
    
    public final long getCreatedAt() {
        return 0L;
    }
    
    public StateSnapshotEntity() {
        super();
    }
    
    public final long component1() {
        return 0L;
    }
    
    public final long component10() {
        return 0L;
    }
    
    public final long component2() {
        return 0L;
    }
    
    public final int component3() {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String component4() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String component5() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String component6() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String component7() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String component8() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String component9() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final com.textgame.data.local.db.entity.StateSnapshotEntity copy(long id, long sessionId, int turnNumber, @org.jetbrains.annotations.NotNull
    java.lang.String protagonistJson, @org.jetbrains.annotations.NotNull
    java.lang.String npcsJson, @org.jetbrains.annotations.NotNull
    java.lang.String gameStateJson, @org.jetbrains.annotations.NotNull
    java.lang.String worldSettingJson, @org.jetbrains.annotations.NotNull
    java.lang.String backgroundSettingJson, @org.jetbrains.annotations.NotNull
    java.lang.String summaryJson, long createdAt) {
        return null;
    }
    
    @java.lang.Override
    public boolean equals(@org.jetbrains.annotations.Nullable
    java.lang.Object other) {
        return false;
    }
    
    @java.lang.Override
    public int hashCode() {
        return 0;
    }
    
    @java.lang.Override
    @org.jetbrains.annotations.NotNull
    public java.lang.String toString() {
        return null;
    }
}