package com.textgame.presentation.viewmodel;

@kotlin.Metadata(mv = {1, 8, 0}, k = 1, xi = 48, d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u0007\n\u0000\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b.\b\u0087\b\u0018\u00002\u00020\u0001B\u009b\u0001\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0005\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0007\u0012\b\b\u0002\u0010\b\u001a\u00020\t\u0012\b\b\u0002\u0010\n\u001a\u00020\u0007\u0012\b\b\u0002\u0010\u000b\u001a\u00020\t\u0012\b\b\u0002\u0010\f\u001a\u00020\t\u0012\b\b\u0002\u0010\r\u001a\u00020\t\u0012\b\b\u0002\u0010\u000e\u001a\u00020\u000f\u0012\b\b\u0002\u0010\u0010\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0011\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0012\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0013\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0014\u001a\u00020\u000f\u00a2\u0006\u0002\u0010\u0015J\t\u0010)\u001a\u00020\u0003H\u00c6\u0003J\t\u0010*\u001a\u00020\u000fH\u00c6\u0003J\t\u0010+\u001a\u00020\u0003H\u00c6\u0003J\t\u0010,\u001a\u00020\u0003H\u00c6\u0003J\t\u0010-\u001a\u00020\u0003H\u00c6\u0003J\t\u0010.\u001a\u00020\u0003H\u00c6\u0003J\t\u0010/\u001a\u00020\u000fH\u00c6\u0003J\t\u00100\u001a\u00020\u0003H\u00c6\u0003J\t\u00101\u001a\u00020\u0003H\u00c6\u0003J\t\u00102\u001a\u00020\u0007H\u00c6\u0003J\t\u00103\u001a\u00020\tH\u00c6\u0003J\t\u00104\u001a\u00020\u0007H\u00c6\u0003J\t\u00105\u001a\u00020\tH\u00c6\u0003J\t\u00106\u001a\u00020\tH\u00c6\u0003J\t\u00107\u001a\u00020\tH\u00c6\u0003J\u009f\u0001\u00108\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00032\b\b\u0002\u0010\u0006\u001a\u00020\u00072\b\b\u0002\u0010\b\u001a\u00020\t2\b\b\u0002\u0010\n\u001a\u00020\u00072\b\b\u0002\u0010\u000b\u001a\u00020\t2\b\b\u0002\u0010\f\u001a\u00020\t2\b\b\u0002\u0010\r\u001a\u00020\t2\b\b\u0002\u0010\u000e\u001a\u00020\u000f2\b\b\u0002\u0010\u0010\u001a\u00020\u00032\b\b\u0002\u0010\u0011\u001a\u00020\u00032\b\b\u0002\u0010\u0012\u001a\u00020\u00032\b\b\u0002\u0010\u0013\u001a\u00020\u00032\b\b\u0002\u0010\u0014\u001a\u00020\u000fH\u00c6\u0001J\u0013\u00109\u001a\u00020\u000f2\b\u0010:\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010;\u001a\u00020\tH\u00d6\u0001J\t\u0010<\u001a\u00020\u0003H\u00d6\u0001R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0017R\u0011\u0010\u0004\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0017R\u0011\u0010\b\u001a\u00020\t\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u001aR\u0011\u0010\f\u001a\u00020\t\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u001aR\u0011\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u001dR\u0011\u0010\u0011\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001e\u0010\u0017R\u0011\u0010\u0012\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001f\u0010\u0017R\u0011\u0010\u0013\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b \u0010\u0017R\u0011\u0010\u0005\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b!\u0010\u0017R\u0011\u0010\u0010\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\"\u0010\u0017R\u0011\u0010\u0014\u001a\u00020\u000f\u00a2\u0006\b\n\u0000\u001a\u0004\b#\u0010$R\u0011\u0010\u000b\u001a\u00020\t\u00a2\u0006\b\n\u0000\u001a\u0004\b%\u0010\u001aR\u0011\u0010\r\u001a\u00020\t\u00a2\u0006\b\n\u0000\u001a\u0004\b&\u0010\u001aR\u0011\u0010\n\u001a\u00020\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\'\u0010\u001dR\u0011\u0010\u000e\u001a\u00020\u000f\u00a2\u0006\b\n\u0000\u001a\u0004\b(\u0010$\u00a8\u0006="}, d2 = {"Lcom/textgame/presentation/viewmodel/SettingsUiState;", "", "apiKey", "", "baseUrl", "model", "dialogueTemperature", "", "dialogueMaxTokens", "", "summaryTemperature", "summaryMaxTokens", "dialogueMaxTokensLimit", "summaryMaxTokensLimit", "thinkingEnabled", "", "reasoningEffort", "imageApiKey", "imageBaseUrl", "imageModel", "saved", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;FIFIIIZLjava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Z)V", "getApiKey", "()Ljava/lang/String;", "getBaseUrl", "getDialogueMaxTokens", "()I", "getDialogueMaxTokensLimit", "getDialogueTemperature", "()F", "getImageApiKey", "getImageBaseUrl", "getImageModel", "getModel", "getReasoningEffort", "getSaved", "()Z", "getSummaryMaxTokens", "getSummaryMaxTokensLimit", "getSummaryTemperature", "getThinkingEnabled", "component1", "component10", "component11", "component12", "component13", "component14", "component15", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "equals", "other", "hashCode", "toString", "app_debug"})
public final class SettingsUiState {
    @org.jetbrains.annotations.NotNull
    private final java.lang.String apiKey = null;
    @org.jetbrains.annotations.NotNull
    private final java.lang.String baseUrl = null;
    @org.jetbrains.annotations.NotNull
    private final java.lang.String model = null;
    private final float dialogueTemperature = 0.0F;
    private final int dialogueMaxTokens = 0;
    private final float summaryTemperature = 0.0F;
    private final int summaryMaxTokens = 0;
    private final int dialogueMaxTokensLimit = 0;
    private final int summaryMaxTokensLimit = 0;
    private final boolean thinkingEnabled = false;
    @org.jetbrains.annotations.NotNull
    private final java.lang.String reasoningEffort = null;
    @org.jetbrains.annotations.NotNull
    private final java.lang.String imageApiKey = null;
    @org.jetbrains.annotations.NotNull
    private final java.lang.String imageBaseUrl = null;
    @org.jetbrains.annotations.NotNull
    private final java.lang.String imageModel = null;
    private final boolean saved = false;
    
    public SettingsUiState(@org.jetbrains.annotations.NotNull
    java.lang.String apiKey, @org.jetbrains.annotations.NotNull
    java.lang.String baseUrl, @org.jetbrains.annotations.NotNull
    java.lang.String model, float dialogueTemperature, int dialogueMaxTokens, float summaryTemperature, int summaryMaxTokens, int dialogueMaxTokensLimit, int summaryMaxTokensLimit, boolean thinkingEnabled, @org.jetbrains.annotations.NotNull
    java.lang.String reasoningEffort, @org.jetbrains.annotations.NotNull
    java.lang.String imageApiKey, @org.jetbrains.annotations.NotNull
    java.lang.String imageBaseUrl, @org.jetbrains.annotations.NotNull
    java.lang.String imageModel, boolean saved) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String getApiKey() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String getBaseUrl() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String getModel() {
        return null;
    }
    
    public final float getDialogueTemperature() {
        return 0.0F;
    }
    
    public final int getDialogueMaxTokens() {
        return 0;
    }
    
    public final float getSummaryTemperature() {
        return 0.0F;
    }
    
    public final int getSummaryMaxTokens() {
        return 0;
    }
    
    public final int getDialogueMaxTokensLimit() {
        return 0;
    }
    
    public final int getSummaryMaxTokensLimit() {
        return 0;
    }
    
    public final boolean getThinkingEnabled() {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String getReasoningEffort() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String getImageApiKey() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String getImageBaseUrl() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String getImageModel() {
        return null;
    }
    
    public final boolean getSaved() {
        return false;
    }
    
    public SettingsUiState() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String component1() {
        return null;
    }
    
    public final boolean component10() {
        return false;
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
    public final java.lang.String component13() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String component14() {
        return null;
    }
    
    public final boolean component15() {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String component2() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String component3() {
        return null;
    }
    
    public final float component4() {
        return 0.0F;
    }
    
    public final int component5() {
        return 0;
    }
    
    public final float component6() {
        return 0.0F;
    }
    
    public final int component7() {
        return 0;
    }
    
    public final int component8() {
        return 0;
    }
    
    public final int component9() {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull
    public final com.textgame.presentation.viewmodel.SettingsUiState copy(@org.jetbrains.annotations.NotNull
    java.lang.String apiKey, @org.jetbrains.annotations.NotNull
    java.lang.String baseUrl, @org.jetbrains.annotations.NotNull
    java.lang.String model, float dialogueTemperature, int dialogueMaxTokens, float summaryTemperature, int summaryMaxTokens, int dialogueMaxTokensLimit, int summaryMaxTokensLimit, boolean thinkingEnabled, @org.jetbrains.annotations.NotNull
    java.lang.String reasoningEffort, @org.jetbrains.annotations.NotNull
    java.lang.String imageApiKey, @org.jetbrains.annotations.NotNull
    java.lang.String imageBaseUrl, @org.jetbrains.annotations.NotNull
    java.lang.String imageModel, boolean saved) {
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