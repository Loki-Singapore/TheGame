package com.textgame.data.local;

@kotlin.Metadata(mv = {1, 8, 0}, k = 1, xi = 48, d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u0007\n\u0000\n\u0002\u0010\b\n\u0002\b\u0016\n\u0002\u0010\u000b\n\u0002\b\u0005\b\u0087\b\u0018\u0000 $2\u00020\u0001:\u0001$BK\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0005\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0007\u0012\b\b\u0002\u0010\b\u001a\u00020\t\u0012\b\b\u0002\u0010\n\u001a\u00020\u0007\u0012\b\b\u0002\u0010\u000b\u001a\u00020\t\u00a2\u0006\u0002\u0010\fJ\t\u0010\u0017\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u0018\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u0019\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u001a\u001a\u00020\u0007H\u00c6\u0003J\t\u0010\u001b\u001a\u00020\tH\u00c6\u0003J\t\u0010\u001c\u001a\u00020\u0007H\u00c6\u0003J\t\u0010\u001d\u001a\u00020\tH\u00c6\u0003JO\u0010\u001e\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00032\b\b\u0002\u0010\u0006\u001a\u00020\u00072\b\b\u0002\u0010\b\u001a\u00020\t2\b\b\u0002\u0010\n\u001a\u00020\u00072\b\b\u0002\u0010\u000b\u001a\u00020\tH\u00c6\u0001J\u0013\u0010\u001f\u001a\u00020 2\b\u0010!\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010\"\u001a\u00020\tH\u00d6\u0001J\t\u0010#\u001a\u00020\u0003H\u00d6\u0001R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u0011\u0010\u0004\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u000eR\u0011\u0010\b\u001a\u00020\t\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u0011\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013R\u0011\u0010\u0005\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u000eR\u0011\u0010\u000b\u001a\u00020\t\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0011R\u0011\u0010\n\u001a\u00020\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0013\u00a8\u0006%"}, d2 = {"Lcom/textgame/data/local/SettingsPreferences;", "", "apiKey", "", "baseUrl", "model", "dialogueTemperature", "", "dialogueMaxTokens", "", "summaryTemperature", "summaryMaxTokens", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;FIFI)V", "getApiKey", "()Ljava/lang/String;", "getBaseUrl", "getDialogueMaxTokens", "()I", "getDialogueTemperature", "()F", "getModel", "getSummaryMaxTokens", "getSummaryTemperature", "component1", "component2", "component3", "component4", "component5", "component6", "component7", "copy", "equals", "", "other", "hashCode", "toString", "Companion", "app_debug"})
public final class SettingsPreferences {
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
    @org.jetbrains.annotations.NotNull
    private static final com.textgame.data.local.SettingsPreferences DEFAULTS = null;
    @org.jetbrains.annotations.NotNull
    private static final java.util.List<java.lang.String> PRESET_MODELS = null;
    @org.jetbrains.annotations.NotNull
    private static final java.util.Map<java.lang.String, java.lang.Integer> MODEL_MAX_OUTPUT = null;
    @org.jetbrains.annotations.NotNull
    private static final java.util.Map<java.lang.String, java.lang.Integer> MODEL_DEFAULT_DIALOGUE_MAX_TOKENS = null;
    @org.jetbrains.annotations.NotNull
    private static final java.util.Map<java.lang.String, java.lang.Integer> MODEL_DEFAULT_SUMMARY_MAX_TOKENS = null;
    @org.jetbrains.annotations.NotNull
    public static final com.textgame.data.local.SettingsPreferences.Companion Companion = null;
    
    public SettingsPreferences(@org.jetbrains.annotations.NotNull
    java.lang.String apiKey, @org.jetbrains.annotations.NotNull
    java.lang.String baseUrl, @org.jetbrains.annotations.NotNull
    java.lang.String model, float dialogueTemperature, int dialogueMaxTokens, float summaryTemperature, int summaryMaxTokens) {
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
    
    public SettingsPreferences() {
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
    
    @org.jetbrains.annotations.NotNull
    public final com.textgame.data.local.SettingsPreferences copy(@org.jetbrains.annotations.NotNull
    java.lang.String apiKey, @org.jetbrains.annotations.NotNull
    java.lang.String baseUrl, @org.jetbrains.annotations.NotNull
    java.lang.String model, float dialogueTemperature, int dialogueMaxTokens, float summaryTemperature, int summaryMaxTokens) {
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
    
    @kotlin.Metadata(mv = {1, 8, 0}, k = 1, xi = 48, d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010$\n\u0002\u0010\u000e\n\u0002\u0010\b\n\u0002\b\u0007\n\u0002\u0010 \n\u0002\b\u0006\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u000e\u0010\u0015\u001a\u00020\n2\u0006\u0010\u0016\u001a\u00020\tJ\u000e\u0010\u0017\u001a\u00020\n2\u0006\u0010\u0016\u001a\u00020\tR\u0011\u0010\u0003\u001a\u00020\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R\u001d\u0010\u0007\u001a\u000e\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\n0\b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u001d\u0010\r\u001a\u000e\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\n0\b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\fR\u001d\u0010\u000f\u001a\u000e\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\n0\b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\fR\u0017\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\t0\u0012\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014\u00a8\u0006\u0018"}, d2 = {"Lcom/textgame/data/local/SettingsPreferences$Companion;", "", "()V", "DEFAULTS", "Lcom/textgame/data/local/SettingsPreferences;", "getDEFAULTS", "()Lcom/textgame/data/local/SettingsPreferences;", "MODEL_DEFAULT_DIALOGUE_MAX_TOKENS", "", "", "", "getMODEL_DEFAULT_DIALOGUE_MAX_TOKENS", "()Ljava/util/Map;", "MODEL_DEFAULT_SUMMARY_MAX_TOKENS", "getMODEL_DEFAULT_SUMMARY_MAX_TOKENS", "MODEL_MAX_OUTPUT", "getMODEL_MAX_OUTPUT", "PRESET_MODELS", "", "getPRESET_MODELS", "()Ljava/util/List;", "getDefaultDialogueMaxTokens", "model", "getDefaultSummaryMaxTokens", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
        
        @org.jetbrains.annotations.NotNull
        public final com.textgame.data.local.SettingsPreferences getDEFAULTS() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull
        public final java.util.List<java.lang.String> getPRESET_MODELS() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull
        public final java.util.Map<java.lang.String, java.lang.Integer> getMODEL_MAX_OUTPUT() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull
        public final java.util.Map<java.lang.String, java.lang.Integer> getMODEL_DEFAULT_DIALOGUE_MAX_TOKENS() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull
        public final java.util.Map<java.lang.String, java.lang.Integer> getMODEL_DEFAULT_SUMMARY_MAX_TOKENS() {
            return null;
        }
        
        public final int getDefaultDialogueMaxTokens(@org.jetbrains.annotations.NotNull
        java.lang.String model) {
            return 0;
        }
        
        public final int getDefaultSummaryMaxTokens(@org.jetbrains.annotations.NotNull
        java.lang.String model) {
            return 0;
        }
    }
}