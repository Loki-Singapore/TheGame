package com.textgame.domain.model;

@kotlin.Metadata(mv = {1, 8, 0}, k = 1, xi = 48, d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\t\n\u0002\u0010$\n\u0002\b\u001e\n\u0002\u0010\b\n\u0002\b\u0002\b\u0087\b\u0018\u00002\u00020\u0001B\u009d\u0001\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u0006\u0012\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\u0006\u0012\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\u0006\u0012\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\u0006\u0012\n\b\u0002\u0010\n\u001a\u0004\u0018\u00010\u0006\u0012\n\b\u0002\u0010\u000b\u001a\u0004\u0018\u00010\u0006\u0012\n\b\u0002\u0010\f\u001a\u0004\u0018\u00010\u0006\u0012\n\b\u0002\u0010\r\u001a\u0004\u0018\u00010\u0006\u0012\n\b\u0002\u0010\u000e\u001a\u0004\u0018\u00010\u0006\u0012\u0016\b\u0002\u0010\u000f\u001a\u0010\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u0001\u0018\u00010\u0010\u00a2\u0006\u0002\u0010\u0011J\t\u0010\u001f\u001a\u00020\u0003H\u00c6\u0003J\u000b\u0010 \u001a\u0004\u0018\u00010\u0006H\u00c6\u0003J\u000b\u0010!\u001a\u0004\u0018\u00010\u0006H\u00c6\u0003J\u0017\u0010\"\u001a\u0010\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u0001\u0018\u00010\u0010H\u00c6\u0003J\t\u0010#\u001a\u00020\u0003H\u00c6\u0003J\u000b\u0010$\u001a\u0004\u0018\u00010\u0006H\u00c6\u0003J\u000b\u0010%\u001a\u0004\u0018\u00010\u0006H\u00c6\u0003J\u000b\u0010&\u001a\u0004\u0018\u00010\u0006H\u00c6\u0003J\u000b\u0010\'\u001a\u0004\u0018\u00010\u0006H\u00c6\u0003J\u000b\u0010(\u001a\u0004\u0018\u00010\u0006H\u00c6\u0003J\u000b\u0010)\u001a\u0004\u0018\u00010\u0006H\u00c6\u0003J\u000b\u0010*\u001a\u0004\u0018\u00010\u0006H\u00c6\u0003J\u00a1\u0001\u0010+\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u00062\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\u00062\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\u00062\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\u00062\n\b\u0002\u0010\n\u001a\u0004\u0018\u00010\u00062\n\b\u0002\u0010\u000b\u001a\u0004\u0018\u00010\u00062\n\b\u0002\u0010\f\u001a\u0004\u0018\u00010\u00062\n\b\u0002\u0010\r\u001a\u0004\u0018\u00010\u00062\n\b\u0002\u0010\u000e\u001a\u0004\u0018\u00010\u00062\u0016\b\u0002\u0010\u000f\u001a\u0010\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u0001\u0018\u00010\u0010H\u00c6\u0001J\u0013\u0010,\u001a\u00020\u00032\b\u0010-\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010.\u001a\u00020/H\u00d6\u0001J\t\u00100\u001a\u00020\u0006H\u00d6\u0001R\u0013\u0010\u000b\u001a\u0004\u0018\u00010\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013R\u001f\u0010\u000f\u001a\u0010\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u0001\u0018\u00010\u0010\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0015R\u0013\u0010\n\u001a\u0004\u0018\u00010\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0013R\u0013\u0010\r\u001a\u0004\u0018\u00010\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0013R\u0013\u0010\u0007\u001a\u0004\u0018\u00010\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0013R\u0018\u0010\u000e\u001a\u0004\u0018\u00010\u00068\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u0013R\u0016\u0010\u0004\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0004\u0010\u001aR\u0016\u0010\u0002\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0002\u0010\u001aR\u0013\u0010\t\u001a\u0004\u0018\u00010\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u0013R\u0013\u0010\u0005\u001a\u0004\u0018\u00010\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u0013R\u0013\u0010\f\u001a\u0004\u0018\u00010\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001d\u0010\u0013R\u0013\u0010\b\u001a\u0004\u0018\u00010\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001e\u0010\u0013\u00a8\u00061"}, d2 = {"Lcom/textgame/domain/model/NPCChanges;", "", "isNew", "", "isDeleted", "name", "", "briefing", "role", "mood", "awareness", "appearance", "personality", "backstory", "hiddenAgenda", "attributes", "", "(ZZLjava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/util/Map;)V", "getAppearance", "()Ljava/lang/String;", "getAttributes", "()Ljava/util/Map;", "getAwareness", "getBackstory", "getBriefing", "getHiddenAgenda", "()Z", "getMood", "getName", "getPersonality", "getRole", "component1", "component10", "component11", "component12", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "equals", "other", "hashCode", "", "toString", "app_debug"})
public final class NPCChanges {
    @com.google.gson.annotations.SerializedName(value = "is_new")
    private final boolean isNew = false;
    @com.google.gson.annotations.SerializedName(value = "is_deleted")
    private final boolean isDeleted = false;
    @org.jetbrains.annotations.Nullable
    private final java.lang.String name = null;
    @org.jetbrains.annotations.Nullable
    private final java.lang.String briefing = null;
    @org.jetbrains.annotations.Nullable
    private final java.lang.String role = null;
    @org.jetbrains.annotations.Nullable
    private final java.lang.String mood = null;
    @org.jetbrains.annotations.Nullable
    private final java.lang.String awareness = null;
    @org.jetbrains.annotations.Nullable
    private final java.lang.String appearance = null;
    @org.jetbrains.annotations.Nullable
    private final java.lang.String personality = null;
    @org.jetbrains.annotations.Nullable
    private final java.lang.String backstory = null;
    @com.google.gson.annotations.SerializedName(value = "hidden_agenda")
    @org.jetbrains.annotations.Nullable
    private final java.lang.String hiddenAgenda = null;
    @org.jetbrains.annotations.Nullable
    private final java.util.Map<java.lang.String, java.lang.Object> attributes = null;
    
    public NPCChanges(boolean isNew, boolean isDeleted, @org.jetbrains.annotations.Nullable
    java.lang.String name, @org.jetbrains.annotations.Nullable
    java.lang.String briefing, @org.jetbrains.annotations.Nullable
    java.lang.String role, @org.jetbrains.annotations.Nullable
    java.lang.String mood, @org.jetbrains.annotations.Nullable
    java.lang.String awareness, @org.jetbrains.annotations.Nullable
    java.lang.String appearance, @org.jetbrains.annotations.Nullable
    java.lang.String personality, @org.jetbrains.annotations.Nullable
    java.lang.String backstory, @org.jetbrains.annotations.Nullable
    java.lang.String hiddenAgenda, @org.jetbrains.annotations.Nullable
    java.util.Map<java.lang.String, ? extends java.lang.Object> attributes) {
        super();
    }
    
    public final boolean isNew() {
        return false;
    }
    
    public final boolean isDeleted() {
        return false;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.String getName() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.String getBriefing() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.String getRole() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.String getMood() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.String getAwareness() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.String getAppearance() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.String getPersonality() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.String getBackstory() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.String getHiddenAgenda() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.util.Map<java.lang.String, java.lang.Object> getAttributes() {
        return null;
    }
    
    public NPCChanges() {
        super();
    }
    
    public final boolean component1() {
        return false;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.String component10() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.String component11() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.util.Map<java.lang.String, java.lang.Object> component12() {
        return null;
    }
    
    public final boolean component2() {
        return false;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.String component3() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.String component4() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.String component5() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.String component6() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.String component7() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.String component8() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.String component9() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final com.textgame.domain.model.NPCChanges copy(boolean isNew, boolean isDeleted, @org.jetbrains.annotations.Nullable
    java.lang.String name, @org.jetbrains.annotations.Nullable
    java.lang.String briefing, @org.jetbrains.annotations.Nullable
    java.lang.String role, @org.jetbrains.annotations.Nullable
    java.lang.String mood, @org.jetbrains.annotations.Nullable
    java.lang.String awareness, @org.jetbrains.annotations.Nullable
    java.lang.String appearance, @org.jetbrains.annotations.Nullable
    java.lang.String personality, @org.jetbrains.annotations.Nullable
    java.lang.String backstory, @org.jetbrains.annotations.Nullable
    java.lang.String hiddenAgenda, @org.jetbrains.annotations.Nullable
    java.util.Map<java.lang.String, ? extends java.lang.Object> attributes) {
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