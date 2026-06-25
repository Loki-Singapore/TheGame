package com.textgame.data.remote.ai;

@kotlin.Metadata(mv = {1, 8, 0}, k = 1, xi = 48, d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\b\n\u0002\u0010 \n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b$\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0087\b\u0018\u00002\u00020\u0001B\u00af\u0001\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0005\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0007\u001a\u00020\u0003\u0012\b\b\u0002\u0010\b\u001a\u00020\u0003\u0012\b\b\u0002\u0010\t\u001a\u00020\u0003\u0012\b\b\u0002\u0010\n\u001a\u00020\u0003\u0012\u000e\b\u0002\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\u00030\f\u0012\b\b\u0002\u0010\r\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u000e\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u000f\u001a\u00020\u0003\u0012\u000e\b\u0002\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00110\f\u0012\u000e\b\u0002\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00130\f\u0012\n\b\u0002\u0010\u0014\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\u0002\u0010\u0015J\t\u0010\'\u001a\u00020\u0003H\u00c6\u0003J\t\u0010(\u001a\u00020\u0003H\u00c6\u0003J\t\u0010)\u001a\u00020\u0003H\u00c6\u0003J\t\u0010*\u001a\u00020\u0003H\u00c6\u0003J\u000f\u0010+\u001a\b\u0012\u0004\u0012\u00020\u00110\fH\u00c6\u0003J\u000f\u0010,\u001a\b\u0012\u0004\u0012\u00020\u00130\fH\u00c6\u0003J\u000b\u0010-\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003J\t\u0010.\u001a\u00020\u0003H\u00c6\u0003J\t\u0010/\u001a\u00020\u0003H\u00c6\u0003J\t\u00100\u001a\u00020\u0003H\u00c6\u0003J\t\u00101\u001a\u00020\u0003H\u00c6\u0003J\t\u00102\u001a\u00020\u0003H\u00c6\u0003J\t\u00103\u001a\u00020\u0003H\u00c6\u0003J\t\u00104\u001a\u00020\u0003H\u00c6\u0003J\u000f\u00105\u001a\b\u0012\u0004\u0012\u00020\u00030\fH\u00c6\u0003J\u00b3\u0001\u00106\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00032\b\b\u0002\u0010\u0006\u001a\u00020\u00032\b\b\u0002\u0010\u0007\u001a\u00020\u00032\b\b\u0002\u0010\b\u001a\u00020\u00032\b\b\u0002\u0010\t\u001a\u00020\u00032\b\b\u0002\u0010\n\u001a\u00020\u00032\u000e\b\u0002\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\u00030\f2\b\b\u0002\u0010\r\u001a\u00020\u00032\b\b\u0002\u0010\u000e\u001a\u00020\u00032\b\b\u0002\u0010\u000f\u001a\u00020\u00032\u000e\b\u0002\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00110\f2\u000e\b\u0002\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00130\f2\n\b\u0002\u0010\u0014\u001a\u0004\u0018\u00010\u0003H\u00c6\u0001J\u0013\u00107\u001a\u0002082\b\u00109\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010:\u001a\u00020;H\u00d6\u0001J\t\u0010<\u001a\u00020\u0003H\u00d6\u0001R\u0017\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00110\f\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0017R\u0013\u0010\u0014\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0019R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u0019R\u0011\u0010\t\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u0019R\u0011\u0010\r\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u0019R\u0017\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00130\f\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001d\u0010\u0017R\u0011\u0010\u000e\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001e\u0010\u0019R\u0011\u0010\u0004\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001f\u0010\u0019R\u0011\u0010\n\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b \u0010\u0019R\u0017\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\u00030\f\u00a2\u0006\b\n\u0000\u001a\u0004\b!\u0010\u0017R\u0011\u0010\b\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\"\u0010\u0019R\u0011\u0010\u0007\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b#\u0010\u0019R\u0011\u0010\u000f\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b$\u0010\u0019R\u0011\u0010\u0005\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b%\u0010\u0019R\u0011\u0010\u0006\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b&\u0010\u0019\u00a8\u0006="}, d2 = {"Lcom/textgame/data/remote/ai/GeneratedWorldResult;", "", "gameName", "", "protagonistName", "worldName", "worldType", "worldDescription", "timeSetting", "locationSetting", "socialStructure", "specialRules", "", "lore", "protagonistBackground", "worldHistory", "attributes", "Lcom/textgame/domain/model/AttributeCategory;", "npcs", "Lcom/textgame/domain/model/NPC;", "error", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/util/List;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/util/List;Ljava/util/List;Ljava/lang/String;)V", "getAttributes", "()Ljava/util/List;", "getError", "()Ljava/lang/String;", "getGameName", "getLocationSetting", "getLore", "getNpcs", "getProtagonistBackground", "getProtagonistName", "getSocialStructure", "getSpecialRules", "getTimeSetting", "getWorldDescription", "getWorldHistory", "getWorldName", "getWorldType", "component1", "component10", "component11", "component12", "component13", "component14", "component15", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "equals", "", "other", "hashCode", "", "toString", "app_debug"})
public final class GeneratedWorldResult {
    @org.jetbrains.annotations.NotNull
    private final java.lang.String gameName = null;
    @org.jetbrains.annotations.NotNull
    private final java.lang.String protagonistName = null;
    @org.jetbrains.annotations.NotNull
    private final java.lang.String worldName = null;
    @org.jetbrains.annotations.NotNull
    private final java.lang.String worldType = null;
    @org.jetbrains.annotations.NotNull
    private final java.lang.String worldDescription = null;
    @org.jetbrains.annotations.NotNull
    private final java.lang.String timeSetting = null;
    @org.jetbrains.annotations.NotNull
    private final java.lang.String locationSetting = null;
    @org.jetbrains.annotations.NotNull
    private final java.lang.String socialStructure = null;
    @org.jetbrains.annotations.NotNull
    private final java.util.List<java.lang.String> specialRules = null;
    @org.jetbrains.annotations.NotNull
    private final java.lang.String lore = null;
    @org.jetbrains.annotations.NotNull
    private final java.lang.String protagonistBackground = null;
    @org.jetbrains.annotations.NotNull
    private final java.lang.String worldHistory = null;
    @org.jetbrains.annotations.NotNull
    private final java.util.List<com.textgame.domain.model.AttributeCategory> attributes = null;
    @org.jetbrains.annotations.NotNull
    private final java.util.List<com.textgame.domain.model.NPC> npcs = null;
    @org.jetbrains.annotations.Nullable
    private final java.lang.String error = null;
    
    public GeneratedWorldResult(@org.jetbrains.annotations.NotNull
    java.lang.String gameName, @org.jetbrains.annotations.NotNull
    java.lang.String protagonistName, @org.jetbrains.annotations.NotNull
    java.lang.String worldName, @org.jetbrains.annotations.NotNull
    java.lang.String worldType, @org.jetbrains.annotations.NotNull
    java.lang.String worldDescription, @org.jetbrains.annotations.NotNull
    java.lang.String timeSetting, @org.jetbrains.annotations.NotNull
    java.lang.String locationSetting, @org.jetbrains.annotations.NotNull
    java.lang.String socialStructure, @org.jetbrains.annotations.NotNull
    java.util.List<java.lang.String> specialRules, @org.jetbrains.annotations.NotNull
    java.lang.String lore, @org.jetbrains.annotations.NotNull
    java.lang.String protagonistBackground, @org.jetbrains.annotations.NotNull
    java.lang.String worldHistory, @org.jetbrains.annotations.NotNull
    java.util.List<com.textgame.domain.model.AttributeCategory> attributes, @org.jetbrains.annotations.NotNull
    java.util.List<com.textgame.domain.model.NPC> npcs, @org.jetbrains.annotations.Nullable
    java.lang.String error) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String getGameName() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String getProtagonistName() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String getWorldName() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String getWorldType() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String getWorldDescription() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String getTimeSetting() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String getLocationSetting() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String getSocialStructure() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.util.List<java.lang.String> getSpecialRules() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String getLore() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String getProtagonistBackground() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String getWorldHistory() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.util.List<com.textgame.domain.model.AttributeCategory> getAttributes() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.util.List<com.textgame.domain.model.NPC> getNpcs() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.String getError() {
        return null;
    }
    
    public GeneratedWorldResult() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String component1() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String component10() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String component11() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String component12() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.util.List<com.textgame.domain.model.AttributeCategory> component13() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.util.List<com.textgame.domain.model.NPC> component14() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.String component15() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String component2() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String component3() {
        return null;
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
    public final java.util.List<java.lang.String> component9() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final com.textgame.data.remote.ai.GeneratedWorldResult copy(@org.jetbrains.annotations.NotNull
    java.lang.String gameName, @org.jetbrains.annotations.NotNull
    java.lang.String protagonistName, @org.jetbrains.annotations.NotNull
    java.lang.String worldName, @org.jetbrains.annotations.NotNull
    java.lang.String worldType, @org.jetbrains.annotations.NotNull
    java.lang.String worldDescription, @org.jetbrains.annotations.NotNull
    java.lang.String timeSetting, @org.jetbrains.annotations.NotNull
    java.lang.String locationSetting, @org.jetbrains.annotations.NotNull
    java.lang.String socialStructure, @org.jetbrains.annotations.NotNull
    java.util.List<java.lang.String> specialRules, @org.jetbrains.annotations.NotNull
    java.lang.String lore, @org.jetbrains.annotations.NotNull
    java.lang.String protagonistBackground, @org.jetbrains.annotations.NotNull
    java.lang.String worldHistory, @org.jetbrains.annotations.NotNull
    java.util.List<com.textgame.domain.model.AttributeCategory> attributes, @org.jetbrains.annotations.NotNull
    java.util.List<com.textgame.domain.model.NPC> npcs, @org.jetbrains.annotations.Nullable
    java.lang.String error) {
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