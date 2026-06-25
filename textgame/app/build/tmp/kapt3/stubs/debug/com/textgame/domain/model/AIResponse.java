package com.textgame.domain.model;

@kotlin.Metadata(mv = {1, 8, 0}, k = 1, xi = 48, d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0013\n\u0002\u0010\b\n\u0002\b\u0002\b\u0087\b\u0018\u00002\u00020\u0001BA\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u0006\u0012\u0010\b\u0002\u0010\u0007\u001a\n\u0012\u0004\u0012\u00020\u0003\u0018\u00010\b\u0012\b\b\u0002\u0010\t\u001a\u00020\n\u00a2\u0006\u0002\u0010\u000bJ\t\u0010\u0015\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u0016\u001a\u00020\u0003H\u00c6\u0003J\u000b\u0010\u0017\u001a\u0004\u0018\u00010\u0006H\u00c6\u0003J\u0011\u0010\u0018\u001a\n\u0012\u0004\u0012\u00020\u0003\u0018\u00010\bH\u00c6\u0003J\t\u0010\u0019\u001a\u00020\nH\u00c6\u0003JE\u0010\u001a\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u00062\u0010\b\u0002\u0010\u0007\u001a\n\u0012\u0004\u0012\u00020\u0003\u0018\u00010\b2\b\b\u0002\u0010\t\u001a\u00020\nH\u00c6\u0001J\u0013\u0010\u001b\u001a\u00020\n2\b\u0010\u001c\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010\u001d\u001a\u00020\u001eH\u00d6\u0001J\t\u0010\u001f\u001a\u00020\u0003H\u00d6\u0001R\u0019\u0010\u0007\u001a\n\u0012\u0004\u0012\u00020\u0003\u0018\u00010\b\u00a2\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0011\u0010\u0004\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u000fR\u0018\u0010\u0005\u001a\u0004\u0018\u00010\u00068\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012R\u0016\u0010\t\u001a\u00020\n8\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014\u00a8\u0006 "}, d2 = {"Lcom/textgame/domain/model/AIResponse;", "", "dialogue", "", "narrative", "stateChanges", "Lcom/textgame/domain/model/StateChanges;", "choices", "", "summaryUpdate", "", "(Ljava/lang/String;Ljava/lang/String;Lcom/textgame/domain/model/StateChanges;Ljava/util/List;Z)V", "getChoices", "()Ljava/util/List;", "getDialogue", "()Ljava/lang/String;", "getNarrative", "getStateChanges", "()Lcom/textgame/domain/model/StateChanges;", "getSummaryUpdate", "()Z", "component1", "component2", "component3", "component4", "component5", "copy", "equals", "other", "hashCode", "", "toString", "app_debug"})
public final class AIResponse {
    @org.jetbrains.annotations.NotNull
    private final java.lang.String dialogue = null;
    @org.jetbrains.annotations.NotNull
    private final java.lang.String narrative = null;
    @com.google.gson.annotations.SerializedName(value = "state_changes")
    @org.jetbrains.annotations.Nullable
    private final com.textgame.domain.model.StateChanges stateChanges = null;
    @org.jetbrains.annotations.Nullable
    private final java.util.List<java.lang.String> choices = null;
    @com.google.gson.annotations.SerializedName(value = "summary_update")
    private final boolean summaryUpdate = false;
    
    public AIResponse(@org.jetbrains.annotations.NotNull
    java.lang.String dialogue, @org.jetbrains.annotations.NotNull
    java.lang.String narrative, @org.jetbrains.annotations.Nullable
    com.textgame.domain.model.StateChanges stateChanges, @org.jetbrains.annotations.Nullable
    java.util.List<java.lang.String> choices, boolean summaryUpdate) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String getDialogue() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String getNarrative() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final com.textgame.domain.model.StateChanges getStateChanges() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.util.List<java.lang.String> getChoices() {
        return null;
    }
    
    public final boolean getSummaryUpdate() {
        return false;
    }
    
    public AIResponse() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String component1() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String component2() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final com.textgame.domain.model.StateChanges component3() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.util.List<java.lang.String> component4() {
        return null;
    }
    
    public final boolean component5() {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull
    public final com.textgame.domain.model.AIResponse copy(@org.jetbrains.annotations.NotNull
    java.lang.String dialogue, @org.jetbrains.annotations.NotNull
    java.lang.String narrative, @org.jetbrains.annotations.Nullable
    com.textgame.domain.model.StateChanges stateChanges, @org.jetbrains.annotations.Nullable
    java.util.List<java.lang.String> choices, boolean summaryUpdate) {
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