package com.textgame.presentation.viewmodel;

@kotlin.Metadata(mv = {1, 8, 0}, k = 1, xi = 48, d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\b\n\u0002\u0010 \n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\t\n\u0002\b2\n\u0002\u0010\b\n\u0002\b\u0003\b\u0087\b\u0018\u0000 L2\u00020\u0001:\u0001LB\u00d9\u0001\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0005\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0007\u001a\u00020\u0003\u0012\b\b\u0002\u0010\b\u001a\u00020\u0003\u0012\b\b\u0002\u0010\t\u001a\u00020\u0003\u0012\b\b\u0002\u0010\n\u001a\u00020\u0003\u0012\u000e\b\u0002\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\u00030\f\u0012\b\b\u0002\u0010\r\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u000e\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u000f\u001a\u00020\u0003\u0012\u000e\b\u0002\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00110\f\u0012\u000e\b\u0002\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00130\f\u0012\b\b\u0002\u0010\u0014\u001a\u00020\u0015\u0012\n\b\u0002\u0010\u0016\u001a\u0004\u0018\u00010\u0017\u0012\n\b\u0002\u0010\u0018\u001a\u0004\u0018\u00010\u0003\u0012\b\b\u0002\u0010\u0019\u001a\u00020\u0015\u0012\b\b\u0002\u0010\u001a\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u001bJ\t\u00102\u001a\u00020\u0003H\u00c6\u0003J\t\u00103\u001a\u00020\u0003H\u00c6\u0003J\t\u00104\u001a\u00020\u0003H\u00c6\u0003J\t\u00105\u001a\u00020\u0003H\u00c6\u0003J\u000f\u00106\u001a\b\u0012\u0004\u0012\u00020\u00110\fH\u00c6\u0003J\u000f\u00107\u001a\b\u0012\u0004\u0012\u00020\u00130\fH\u00c6\u0003J\t\u00108\u001a\u00020\u0015H\u00c6\u0003J\u0010\u00109\u001a\u0004\u0018\u00010\u0017H\u00c6\u0003\u00a2\u0006\u0002\u0010\u001fJ\u000b\u0010:\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003J\t\u0010;\u001a\u00020\u0015H\u00c6\u0003J\t\u0010<\u001a\u00020\u0003H\u00c6\u0003J\t\u0010=\u001a\u00020\u0003H\u00c6\u0003J\t\u0010>\u001a\u00020\u0003H\u00c6\u0003J\t\u0010?\u001a\u00020\u0003H\u00c6\u0003J\t\u0010@\u001a\u00020\u0003H\u00c6\u0003J\t\u0010A\u001a\u00020\u0003H\u00c6\u0003J\t\u0010B\u001a\u00020\u0003H\u00c6\u0003J\t\u0010C\u001a\u00020\u0003H\u00c6\u0003J\u000f\u0010D\u001a\b\u0012\u0004\u0012\u00020\u00030\fH\u00c6\u0003J\u00e2\u0001\u0010E\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00032\b\b\u0002\u0010\u0006\u001a\u00020\u00032\b\b\u0002\u0010\u0007\u001a\u00020\u00032\b\b\u0002\u0010\b\u001a\u00020\u00032\b\b\u0002\u0010\t\u001a\u00020\u00032\b\b\u0002\u0010\n\u001a\u00020\u00032\u000e\b\u0002\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\u00030\f2\b\b\u0002\u0010\r\u001a\u00020\u00032\b\b\u0002\u0010\u000e\u001a\u00020\u00032\b\b\u0002\u0010\u000f\u001a\u00020\u00032\u000e\b\u0002\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00110\f2\u000e\b\u0002\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00130\f2\b\b\u0002\u0010\u0014\u001a\u00020\u00152\n\b\u0002\u0010\u0016\u001a\u0004\u0018\u00010\u00172\n\b\u0002\u0010\u0018\u001a\u0004\u0018\u00010\u00032\b\b\u0002\u0010\u0019\u001a\u00020\u00152\b\b\u0002\u0010\u001a\u001a\u00020\u0003H\u00c6\u0001\u00a2\u0006\u0002\u0010FJ\u0013\u0010G\u001a\u00020\u00152\b\u0010H\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010I\u001a\u00020JH\u00d6\u0001J\t\u0010K\u001a\u00020\u0003H\u00d6\u0001R\u0017\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00110\f\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u001dR\u0015\u0010\u0016\u001a\u0004\u0018\u00010\u0017\u00a2\u0006\n\n\u0002\u0010 \u001a\u0004\b\u001e\u0010\u001fR\u0013\u0010\u0018\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b!\u0010\"R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b#\u0010\"R\u0011\u0010\u001a\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b$\u0010\"R\u0011\u0010\u0014\u001a\u00020\u0015\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010%R\u0011\u0010\u0019\u001a\u00020\u0015\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010%R\u0011\u0010\t\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b&\u0010\"R\u0011\u0010\r\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\'\u0010\"R\u0017\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00130\f\u00a2\u0006\b\n\u0000\u001a\u0004\b(\u0010\u001dR\u0011\u0010\u000e\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b)\u0010\"R\u0011\u0010\u0004\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b*\u0010\"R\u0011\u0010\n\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b+\u0010\"R\u0017\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\u00030\f\u00a2\u0006\b\n\u0000\u001a\u0004\b,\u0010\u001dR\u0011\u0010\b\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b-\u0010\"R\u0011\u0010\u0007\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b.\u0010\"R\u0011\u0010\u000f\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b/\u0010\"R\u0011\u0010\u0005\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b0\u0010\"R\u0011\u0010\u0006\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b1\u0010\"\u00a8\u0006M"}, d2 = {"Lcom/textgame/presentation/viewmodel/CreatorUiState;", "", "gameName", "", "protagonistName", "worldName", "worldType", "worldDescription", "timeSetting", "locationSetting", "socialStructure", "specialRules", "", "lore", "protagonistBackground", "worldHistory", "attributeCategories", "Lcom/textgame/domain/model/AttributeCategory;", "npcs", "Lcom/textgame/domain/model/NPC;", "isCreating", "", "createdSessionId", "", "error", "isGenerating", "generationPrompt", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/util/List;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/util/List;Ljava/util/List;ZLjava/lang/Long;Ljava/lang/String;ZLjava/lang/String;)V", "getAttributeCategories", "()Ljava/util/List;", "getCreatedSessionId", "()Ljava/lang/Long;", "Ljava/lang/Long;", "getError", "()Ljava/lang/String;", "getGameName", "getGenerationPrompt", "()Z", "getLocationSetting", "getLore", "getNpcs", "getProtagonistBackground", "getProtagonistName", "getSocialStructure", "getSpecialRules", "getTimeSetting", "getWorldDescription", "getWorldHistory", "getWorldName", "getWorldType", "component1", "component10", "component11", "component12", "component13", "component14", "component15", "component16", "component17", "component18", "component19", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/util/List;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/util/List;Ljava/util/List;ZLjava/lang/Long;Ljava/lang/String;ZLjava/lang/String;)Lcom/textgame/presentation/viewmodel/CreatorUiState;", "equals", "other", "hashCode", "", "toString", "Companion", "app_debug"})
public final class CreatorUiState {
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
    private final java.util.List<com.textgame.domain.model.AttributeCategory> attributeCategories = null;
    @org.jetbrains.annotations.NotNull
    private final java.util.List<com.textgame.domain.model.NPC> npcs = null;
    private final boolean isCreating = false;
    @org.jetbrains.annotations.Nullable
    private final java.lang.Long createdSessionId = null;
    @org.jetbrains.annotations.Nullable
    private final java.lang.String error = null;
    private final boolean isGenerating = false;
    @org.jetbrains.annotations.NotNull
    private final java.lang.String generationPrompt = null;
    @org.jetbrains.annotations.NotNull
    public static final com.textgame.presentation.viewmodel.CreatorUiState.Companion Companion = null;
    
    public CreatorUiState(@org.jetbrains.annotations.NotNull
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
    java.util.List<com.textgame.domain.model.AttributeCategory> attributeCategories, @org.jetbrains.annotations.NotNull
    java.util.List<com.textgame.domain.model.NPC> npcs, boolean isCreating, @org.jetbrains.annotations.Nullable
    java.lang.Long createdSessionId, @org.jetbrains.annotations.Nullable
    java.lang.String error, boolean isGenerating, @org.jetbrains.annotations.NotNull
    java.lang.String generationPrompt) {
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
    public final java.util.List<com.textgame.domain.model.AttributeCategory> getAttributeCategories() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.util.List<com.textgame.domain.model.NPC> getNpcs() {
        return null;
    }
    
    public final boolean isCreating() {
        return false;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.Long getCreatedSessionId() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.String getError() {
        return null;
    }
    
    public final boolean isGenerating() {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String getGenerationPrompt() {
        return null;
    }
    
    public CreatorUiState() {
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
    
    public final boolean component15() {
        return false;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.Long component16() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.String component17() {
        return null;
    }
    
    public final boolean component18() {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String component19() {
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
    public final com.textgame.presentation.viewmodel.CreatorUiState copy(@org.jetbrains.annotations.NotNull
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
    java.util.List<com.textgame.domain.model.AttributeCategory> attributeCategories, @org.jetbrains.annotations.NotNull
    java.util.List<com.textgame.domain.model.NPC> npcs, boolean isCreating, @org.jetbrains.annotations.Nullable
    java.lang.Long createdSessionId, @org.jetbrains.annotations.Nullable
    java.lang.String error, boolean isGenerating, @org.jetbrains.annotations.NotNull
    java.lang.String generationPrompt) {
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
    
    @kotlin.Metadata(mv = {1, 8, 0}, k = 1, xi = 48, d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004\u00a8\u0006\u0006"}, d2 = {"Lcom/textgame/presentation/viewmodel/CreatorUiState$Companion;", "", "()V", "defaultAttributes", "", "Lcom/textgame/domain/model/AttributeCategory;", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
        
        @org.jetbrains.annotations.NotNull
        public final java.util.List<com.textgame.domain.model.AttributeCategory> defaultAttributes() {
            return null;
        }
    }
}