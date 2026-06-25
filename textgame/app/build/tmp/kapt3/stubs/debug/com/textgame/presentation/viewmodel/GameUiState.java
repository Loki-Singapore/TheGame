package com.textgame.presentation.viewmodel;

@kotlin.Metadata(mv = {1, 8, 0}, k = 1, xi = 48, d1 = {"\u0000N\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0002\b$\n\u0002\u0010\b\n\u0002\b\u0002\b\u0087\b\u0018\u00002\u00020\u0001B\u0093\u0001\u0012\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\u000e\b\u0002\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005\u0012\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\b\u0012\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\n\u0012\n\b\u0002\u0010\u000b\u001a\u0004\u0018\u00010\f\u0012\n\b\u0002\u0010\r\u001a\u0004\u0018\u00010\u000e\u0012\u000e\b\u0002\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00100\u0005\u0012\u000e\b\u0002\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00120\u0005\u0012\b\b\u0002\u0010\u0013\u001a\u00020\u0014\u0012\n\b\u0002\u0010\u0015\u001a\u0004\u0018\u00010\u0012\u0012\n\b\u0002\u0010\u0016\u001a\u0004\u0018\u00010\u0012\u00a2\u0006\u0002\u0010\u0017J\u000b\u0010*\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003J\u000b\u0010+\u001a\u0004\u0018\u00010\u0012H\u00c6\u0003J\u000b\u0010,\u001a\u0004\u0018\u00010\u0012H\u00c6\u0003J\u000f\u0010-\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005H\u00c6\u0003J\u000b\u0010.\u001a\u0004\u0018\u00010\bH\u00c6\u0003J\u000b\u0010/\u001a\u0004\u0018\u00010\nH\u00c6\u0003J\u000b\u00100\u001a\u0004\u0018\u00010\fH\u00c6\u0003J\u000b\u00101\u001a\u0004\u0018\u00010\u000eH\u00c6\u0003J\u000f\u00102\u001a\b\u0012\u0004\u0012\u00020\u00100\u0005H\u00c6\u0003J\u000f\u00103\u001a\b\u0012\u0004\u0012\u00020\u00120\u0005H\u00c6\u0003J\t\u00104\u001a\u00020\u0014H\u00c6\u0003J\u0097\u0001\u00105\u001a\u00020\u00002\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u00032\u000e\b\u0002\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u00052\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\b2\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\n2\n\b\u0002\u0010\u000b\u001a\u0004\u0018\u00010\f2\n\b\u0002\u0010\r\u001a\u0004\u0018\u00010\u000e2\u000e\b\u0002\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00100\u00052\u000e\b\u0002\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00120\u00052\b\b\u0002\u0010\u0013\u001a\u00020\u00142\n\b\u0002\u0010\u0015\u001a\u0004\u0018\u00010\u00122\n\b\u0002\u0010\u0016\u001a\u0004\u0018\u00010\u0012H\u00c6\u0001J\u0013\u00106\u001a\u00020\u00142\b\u00107\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u00108\u001a\u000209H\u00d6\u0001J\t\u0010:\u001a\u00020\u0012H\u00d6\u0001R\u0013\u0010\r\u001a\u0004\u0018\u00010\u000e\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0019R\u0017\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00120\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u001bR\u0017\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00100\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u001bR\u0013\u0010\u0015\u001a\u0004\u0018\u00010\u0012\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001d\u0010\u001eR\u0013\u0010\u0007\u001a\u0004\u0018\u00010\b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001f\u0010 R\u0011\u0010\u0013\u001a\u00020\u0014\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010!R\u0017\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\"\u0010\u001bR\u0013\u0010\u0016\u001a\u0004\u0018\u00010\u0012\u00a2\u0006\b\n\u0000\u001a\u0004\b#\u0010\u001eR\u0013\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b$\u0010%R\u0013\u0010\t\u001a\u0004\u0018\u00010\n\u00a2\u0006\b\n\u0000\u001a\u0004\b&\u0010\'R\u0013\u0010\u000b\u001a\u0004\u0018\u00010\f\u00a2\u0006\b\n\u0000\u001a\u0004\b(\u0010)\u00a8\u0006;"}, d2 = {"Lcom/textgame/presentation/viewmodel/GameUiState;", "", "protagonist", "Lcom/textgame/domain/model/Protagonist;", "npcs", "", "Lcom/textgame/domain/model/NPC;", "gameState", "Lcom/textgame/domain/model/GameState;", "summary", "Lcom/textgame/domain/model/Summary;", "worldSetting", "Lcom/textgame/domain/model/WorldSetting;", "backgroundSetting", "Lcom/textgame/domain/model/BackgroundSetting;", "dialogues", "Lcom/textgame/presentation/viewmodel/DialogueDisplay;", "choices", "", "isLoading", "", "error", "pendingRegeneratePrompt", "(Lcom/textgame/domain/model/Protagonist;Ljava/util/List;Lcom/textgame/domain/model/GameState;Lcom/textgame/domain/model/Summary;Lcom/textgame/domain/model/WorldSetting;Lcom/textgame/domain/model/BackgroundSetting;Ljava/util/List;Ljava/util/List;ZLjava/lang/String;Ljava/lang/String;)V", "getBackgroundSetting", "()Lcom/textgame/domain/model/BackgroundSetting;", "getChoices", "()Ljava/util/List;", "getDialogues", "getError", "()Ljava/lang/String;", "getGameState", "()Lcom/textgame/domain/model/GameState;", "()Z", "getNpcs", "getPendingRegeneratePrompt", "getProtagonist", "()Lcom/textgame/domain/model/Protagonist;", "getSummary", "()Lcom/textgame/domain/model/Summary;", "getWorldSetting", "()Lcom/textgame/domain/model/WorldSetting;", "component1", "component10", "component11", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "equals", "other", "hashCode", "", "toString", "app_debug"})
public final class GameUiState {
    @org.jetbrains.annotations.Nullable
    private final com.textgame.domain.model.Protagonist protagonist = null;
    @org.jetbrains.annotations.NotNull
    private final java.util.List<com.textgame.domain.model.NPC> npcs = null;
    @org.jetbrains.annotations.Nullable
    private final com.textgame.domain.model.GameState gameState = null;
    @org.jetbrains.annotations.Nullable
    private final com.textgame.domain.model.Summary summary = null;
    @org.jetbrains.annotations.Nullable
    private final com.textgame.domain.model.WorldSetting worldSetting = null;
    @org.jetbrains.annotations.Nullable
    private final com.textgame.domain.model.BackgroundSetting backgroundSetting = null;
    @org.jetbrains.annotations.NotNull
    private final java.util.List<com.textgame.presentation.viewmodel.DialogueDisplay> dialogues = null;
    @org.jetbrains.annotations.NotNull
    private final java.util.List<java.lang.String> choices = null;
    private final boolean isLoading = false;
    @org.jetbrains.annotations.Nullable
    private final java.lang.String error = null;
    @org.jetbrains.annotations.Nullable
    private final java.lang.String pendingRegeneratePrompt = null;
    
    public GameUiState(@org.jetbrains.annotations.Nullable
    com.textgame.domain.model.Protagonist protagonist, @org.jetbrains.annotations.NotNull
    java.util.List<com.textgame.domain.model.NPC> npcs, @org.jetbrains.annotations.Nullable
    com.textgame.domain.model.GameState gameState, @org.jetbrains.annotations.Nullable
    com.textgame.domain.model.Summary summary, @org.jetbrains.annotations.Nullable
    com.textgame.domain.model.WorldSetting worldSetting, @org.jetbrains.annotations.Nullable
    com.textgame.domain.model.BackgroundSetting backgroundSetting, @org.jetbrains.annotations.NotNull
    java.util.List<com.textgame.presentation.viewmodel.DialogueDisplay> dialogues, @org.jetbrains.annotations.NotNull
    java.util.List<java.lang.String> choices, boolean isLoading, @org.jetbrains.annotations.Nullable
    java.lang.String error, @org.jetbrains.annotations.Nullable
    java.lang.String pendingRegeneratePrompt) {
        super();
    }
    
    @org.jetbrains.annotations.Nullable
    public final com.textgame.domain.model.Protagonist getProtagonist() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.util.List<com.textgame.domain.model.NPC> getNpcs() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final com.textgame.domain.model.GameState getGameState() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final com.textgame.domain.model.Summary getSummary() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final com.textgame.domain.model.WorldSetting getWorldSetting() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final com.textgame.domain.model.BackgroundSetting getBackgroundSetting() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.util.List<com.textgame.presentation.viewmodel.DialogueDisplay> getDialogues() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.util.List<java.lang.String> getChoices() {
        return null;
    }
    
    public final boolean isLoading() {
        return false;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.String getError() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.String getPendingRegeneratePrompt() {
        return null;
    }
    
    public GameUiState() {
        super();
    }
    
    @org.jetbrains.annotations.Nullable
    public final com.textgame.domain.model.Protagonist component1() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.String component10() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.String component11() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.util.List<com.textgame.domain.model.NPC> component2() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final com.textgame.domain.model.GameState component3() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final com.textgame.domain.model.Summary component4() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final com.textgame.domain.model.WorldSetting component5() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final com.textgame.domain.model.BackgroundSetting component6() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.util.List<com.textgame.presentation.viewmodel.DialogueDisplay> component7() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.util.List<java.lang.String> component8() {
        return null;
    }
    
    public final boolean component9() {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull
    public final com.textgame.presentation.viewmodel.GameUiState copy(@org.jetbrains.annotations.Nullable
    com.textgame.domain.model.Protagonist protagonist, @org.jetbrains.annotations.NotNull
    java.util.List<com.textgame.domain.model.NPC> npcs, @org.jetbrains.annotations.Nullable
    com.textgame.domain.model.GameState gameState, @org.jetbrains.annotations.Nullable
    com.textgame.domain.model.Summary summary, @org.jetbrains.annotations.Nullable
    com.textgame.domain.model.WorldSetting worldSetting, @org.jetbrains.annotations.Nullable
    com.textgame.domain.model.BackgroundSetting backgroundSetting, @org.jetbrains.annotations.NotNull
    java.util.List<com.textgame.presentation.viewmodel.DialogueDisplay> dialogues, @org.jetbrains.annotations.NotNull
    java.util.List<java.lang.String> choices, boolean isLoading, @org.jetbrains.annotations.Nullable
    java.lang.String error, @org.jetbrains.annotations.Nullable
    java.lang.String pendingRegeneratePrompt) {
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