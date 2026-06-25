package com.textgame.domain.model;

@kotlin.Metadata(mv = {1, 8, 0}, k = 1, xi = 48, d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010$\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\b\u001b\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0087\b\u0018\u00002\u00020\u0001Bw\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0005\u001a\u00020\u0006\u0012\u0014\b\u0002\u0010\u0007\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00060\b\u0012\b\b\u0002\u0010\t\u001a\u00020\u0006\u0012\b\b\u0002\u0010\n\u001a\u00020\u0006\u0012\u000e\b\u0002\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\u00060\f\u0012\u000e\b\u0002\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u00060\f\u0012\b\b\u0002\u0010\u000e\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u000fJ\t\u0010\u001d\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u001e\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u001f\u001a\u00020\u0006H\u00c6\u0003J\u0015\u0010 \u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00060\bH\u00c6\u0003J\t\u0010!\u001a\u00020\u0006H\u00c6\u0003J\t\u0010\"\u001a\u00020\u0006H\u00c6\u0003J\u000f\u0010#\u001a\b\u0012\u0004\u0012\u00020\u00060\fH\u00c6\u0003J\u000f\u0010$\u001a\b\u0012\u0004\u0012\u00020\u00060\fH\u00c6\u0003J\t\u0010%\u001a\u00020\u0003H\u00c6\u0003J{\u0010&\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00062\u0014\b\u0002\u0010\u0007\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00060\b2\b\b\u0002\u0010\t\u001a\u00020\u00062\b\b\u0002\u0010\n\u001a\u00020\u00062\u000e\b\u0002\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\u00060\f2\u000e\b\u0002\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u00060\f2\b\b\u0002\u0010\u000e\u001a\u00020\u0003H\u00c6\u0001J\u0013\u0010\'\u001a\u00020(2\b\u0010)\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010*\u001a\u00020+H\u00d6\u0001J\t\u0010,\u001a\u00020\u0006H\u00d6\u0001R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u0017\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\u00060\f\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013R\u001d\u0010\u0007\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00060\b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0015R\u0011\u0010\u0005\u001a\u00020\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0017R\u0011\u0010\n\u001a\u00020\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0017R\u0011\u0010\u0004\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u0011R\u0017\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u00060\f\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u0013R\u0011\u0010\u000e\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u0011R\u0011\u0010\t\u001a\u00020\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u0017\u00a8\u0006-"}, d2 = {"Lcom/textgame/domain/model/BackgroundSetting;", "", "id", "", "sessionId", "protagonistBackground", "", "npcBackgrounds", "", "worldHistory", "relationshipHistory", "majorPlotThreads", "", "unlockedLore", "updatedAt", "(JJLjava/lang/String;Ljava/util/Map;Ljava/lang/String;Ljava/lang/String;Ljava/util/List;Ljava/util/List;J)V", "getId", "()J", "getMajorPlotThreads", "()Ljava/util/List;", "getNpcBackgrounds", "()Ljava/util/Map;", "getProtagonistBackground", "()Ljava/lang/String;", "getRelationshipHistory", "getSessionId", "getUnlockedLore", "getUpdatedAt", "getWorldHistory", "component1", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "equals", "", "other", "hashCode", "", "toString", "app_debug"})
public final class BackgroundSetting {
    private final long id = 0L;
    private final long sessionId = 0L;
    @org.jetbrains.annotations.NotNull
    private final java.lang.String protagonistBackground = null;
    @org.jetbrains.annotations.NotNull
    private final java.util.Map<java.lang.String, java.lang.String> npcBackgrounds = null;
    @org.jetbrains.annotations.NotNull
    private final java.lang.String worldHistory = null;
    @org.jetbrains.annotations.NotNull
    private final java.lang.String relationshipHistory = null;
    @org.jetbrains.annotations.NotNull
    private final java.util.List<java.lang.String> majorPlotThreads = null;
    @org.jetbrains.annotations.NotNull
    private final java.util.List<java.lang.String> unlockedLore = null;
    private final long updatedAt = 0L;
    
    public BackgroundSetting(long id, long sessionId, @org.jetbrains.annotations.NotNull
    java.lang.String protagonistBackground, @org.jetbrains.annotations.NotNull
    java.util.Map<java.lang.String, java.lang.String> npcBackgrounds, @org.jetbrains.annotations.NotNull
    java.lang.String worldHistory, @org.jetbrains.annotations.NotNull
    java.lang.String relationshipHistory, @org.jetbrains.annotations.NotNull
    java.util.List<java.lang.String> majorPlotThreads, @org.jetbrains.annotations.NotNull
    java.util.List<java.lang.String> unlockedLore, long updatedAt) {
        super();
    }
    
    public final long getId() {
        return 0L;
    }
    
    public final long getSessionId() {
        return 0L;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String getProtagonistBackground() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.util.Map<java.lang.String, java.lang.String> getNpcBackgrounds() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String getWorldHistory() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String getRelationshipHistory() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.util.List<java.lang.String> getMajorPlotThreads() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.util.List<java.lang.String> getUnlockedLore() {
        return null;
    }
    
    public final long getUpdatedAt() {
        return 0L;
    }
    
    public BackgroundSetting() {
        super();
    }
    
    public final long component1() {
        return 0L;
    }
    
    public final long component2() {
        return 0L;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String component3() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.util.Map<java.lang.String, java.lang.String> component4() {
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
    public final java.util.List<java.lang.String> component7() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.util.List<java.lang.String> component8() {
        return null;
    }
    
    public final long component9() {
        return 0L;
    }
    
    @org.jetbrains.annotations.NotNull
    public final com.textgame.domain.model.BackgroundSetting copy(long id, long sessionId, @org.jetbrains.annotations.NotNull
    java.lang.String protagonistBackground, @org.jetbrains.annotations.NotNull
    java.util.Map<java.lang.String, java.lang.String> npcBackgrounds, @org.jetbrains.annotations.NotNull
    java.lang.String worldHistory, @org.jetbrains.annotations.NotNull
    java.lang.String relationshipHistory, @org.jetbrains.annotations.NotNull
    java.util.List<java.lang.String> majorPlotThreads, @org.jetbrains.annotations.NotNull
    java.util.List<java.lang.String> unlockedLore, long updatedAt) {
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