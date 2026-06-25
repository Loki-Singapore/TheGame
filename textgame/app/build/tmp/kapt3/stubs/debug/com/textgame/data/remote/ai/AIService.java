package com.textgame.data.remote.ai;

@kotlin.Metadata(mv = {1, 8, 0}, k = 1, xi = 48, d1 = {"\u0000l\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0000\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0006\b\u0007\u0018\u00002\u00020\u0001BG\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u0007\u001a\u00020\b\u0012\b\b\u0002\u0010\t\u001a\u00020\n\u0012\b\b\u0002\u0010\u000b\u001a\u00020\b\u0012\b\b\u0002\u0010\f\u001a\u00020\n\u00a2\u0006\u0002\u0010\rJ>\u0010\u0010\u001a\u00020\u00052\b\u0010\u0011\u001a\u0004\u0018\u00010\u00122\u0006\u0010\u0013\u001a\u00020\u00142\f\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00170\u00162\u0006\u0010\u0018\u001a\u00020\u00192\f\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u00050\u0016H\u0002J\b\u0010\u001b\u001a\u00020\u0005H\u0002J<\u0010\u001c\u001a\u00020\u00052\u0006\u0010\u001d\u001a\u00020\u001e2\f\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u00050\u00162\u0006\u0010\u0013\u001a\u00020\u00142\f\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00170\u00162\u0006\u0010\u0018\u001a\u00020\u0019H\u0002J\u0018\u0010\u001f\u001a\u00020\u00052\u0006\u0010\u001d\u001a\u00020\u001e2\u0006\u0010 \u001a\u00020!H\u0002J\u0010\u0010\"\u001a\u00020\u00052\u0006\u0010#\u001a\u00020\u0005H\u0002J\u0010\u0010$\u001a\u00020\u00052\u0006\u0010%\u001a\u00020\u0005H\u0002J_\u0010&\u001a\u00020\'2\u0006\u0010\u001d\u001a\u00020\u001e2\u0006\u0010 \u001a\u00020!2\b\u0010\u0011\u001a\u0004\u0018\u00010\u00122\u0006\u0010\u0013\u001a\u00020\u00142\f\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00170\u00162\u0006\u0010\u0018\u001a\u00020\u00192\f\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u00050\u00162\u0006\u0010#\u001a\u00020\u0005H\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010(JE\u0010)\u001a\u00020\u00122\u0006\u0010\u001d\u001a\u00020\u001e2\f\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u00050\u00162\u0006\u0010\u0013\u001a\u00020\u00142\f\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00170\u00162\u0006\u0010\u0018\u001a\u00020\u0019H\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010*J\u0019\u0010+\u001a\u00020,2\u0006\u0010-\u001a\u00020\u0005H\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010.J\u0010\u0010/\u001a\u00020\'2\u0006\u0010%\u001a\u00020\u0005H\u0002J\u0010\u00100\u001a\u00020,2\u0006\u0010%\u001a\u00020\u0005H\u0002J\u0018\u00101\u001a\u00020\u00122\u0006\u0010%\u001a\u00020\u00052\u0006\u0010\u0018\u001a\u00020\u0019H\u0002R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u000fX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\nX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u00062"}, d2 = {"Lcom/textgame/data/remote/ai/AIService;", "", "apiService", "Lcom/textgame/data/remote/ai/DeepSeekApiService;", "apiKey", "", "model", "dialogueTemperature", "", "dialogueMaxTokens", "", "summaryTemperature", "summaryMaxTokens", "(Lcom/textgame/data/remote/ai/DeepSeekApiService;Ljava/lang/String;Ljava/lang/String;FIFI)V", "gson", "Lcom/google/gson/Gson;", "buildContextPrompt", "summary", "Lcom/textgame/domain/model/Summary;", "protagonist", "Lcom/textgame/domain/model/Protagonist;", "npcs", "", "Lcom/textgame/domain/model/NPC;", "gameState", "Lcom/textgame/domain/model/GameState;", "recentDialogues", "buildOutputInstruction", "buildSummaryPrompt", "worldSetting", "Lcom/textgame/domain/model/WorldSetting;", "buildSystemPrompt", "backgroundSetting", "Lcom/textgame/domain/model/BackgroundSetting;", "buildUserPrompt", "userInput", "extractJson", "content", "generateDialogueResponse", "Lcom/textgame/domain/model/AIResponse;", "(Lcom/textgame/domain/model/WorldSetting;Lcom/textgame/domain/model/BackgroundSetting;Lcom/textgame/domain/model/Summary;Lcom/textgame/domain/model/Protagonist;Ljava/util/List;Lcom/textgame/domain/model/GameState;Ljava/util/List;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "generateSummary", "(Lcom/textgame/domain/model/WorldSetting;Ljava/util/List;Lcom/textgame/domain/model/Protagonist;Ljava/util/List;Lcom/textgame/domain/model/GameState;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "generateWorldFromPrompt", "Lcom/textgame/data/remote/ai/GeneratedWorldResult;", "userPrompt", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "parseAIResponse", "parseGeneratedWorld", "parseSummaryResponse", "app_debug"})
public final class AIService {
    @org.jetbrains.annotations.NotNull
    private final com.textgame.data.remote.ai.DeepSeekApiService apiService = null;
    @org.jetbrains.annotations.NotNull
    private final java.lang.String apiKey = null;
    @org.jetbrains.annotations.NotNull
    private final java.lang.String model = null;
    private final float dialogueTemperature = 0.0F;
    private final int dialogueMaxTokens = 0;
    private final float summaryTemperature = 0.0F;
    private final int summaryMaxTokens = 0;
    @org.jetbrains.annotations.NotNull
    private final com.google.gson.Gson gson = null;
    
    public AIService(@org.jetbrains.annotations.NotNull
    com.textgame.data.remote.ai.DeepSeekApiService apiService, @org.jetbrains.annotations.NotNull
    java.lang.String apiKey, @org.jetbrains.annotations.NotNull
    java.lang.String model, float dialogueTemperature, int dialogueMaxTokens, float summaryTemperature, int summaryMaxTokens) {
        super();
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.Object generateDialogueResponse(@org.jetbrains.annotations.NotNull
    com.textgame.domain.model.WorldSetting worldSetting, @org.jetbrains.annotations.NotNull
    com.textgame.domain.model.BackgroundSetting backgroundSetting, @org.jetbrains.annotations.Nullable
    com.textgame.domain.model.Summary summary, @org.jetbrains.annotations.NotNull
    com.textgame.domain.model.Protagonist protagonist, @org.jetbrains.annotations.NotNull
    java.util.List<com.textgame.domain.model.NPC> npcs, @org.jetbrains.annotations.NotNull
    com.textgame.domain.model.GameState gameState, @org.jetbrains.annotations.NotNull
    java.util.List<java.lang.String> recentDialogues, @org.jetbrains.annotations.NotNull
    java.lang.String userInput, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super com.textgame.domain.model.AIResponse> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.Object generateSummary(@org.jetbrains.annotations.NotNull
    com.textgame.domain.model.WorldSetting worldSetting, @org.jetbrains.annotations.NotNull
    java.util.List<java.lang.String> recentDialogues, @org.jetbrains.annotations.NotNull
    com.textgame.domain.model.Protagonist protagonist, @org.jetbrains.annotations.NotNull
    java.util.List<com.textgame.domain.model.NPC> npcs, @org.jetbrains.annotations.NotNull
    com.textgame.domain.model.GameState gameState, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super com.textgame.domain.model.Summary> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.Object generateWorldFromPrompt(@org.jetbrains.annotations.NotNull
    java.lang.String userPrompt, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super com.textgame.data.remote.ai.GeneratedWorldResult> $completion) {
        return null;
    }
    
    private final java.lang.String buildSystemPrompt(com.textgame.domain.model.WorldSetting worldSetting, com.textgame.domain.model.BackgroundSetting backgroundSetting) {
        return null;
    }
    
    private final java.lang.String buildContextPrompt(com.textgame.domain.model.Summary summary, com.textgame.domain.model.Protagonist protagonist, java.util.List<com.textgame.domain.model.NPC> npcs, com.textgame.domain.model.GameState gameState, java.util.List<java.lang.String> recentDialogues) {
        return null;
    }
    
    private final java.lang.String buildUserPrompt(java.lang.String userInput) {
        return null;
    }
    
    private final java.lang.String buildSummaryPrompt(com.textgame.domain.model.WorldSetting worldSetting, java.util.List<java.lang.String> recentDialogues, com.textgame.domain.model.Protagonist protagonist, java.util.List<com.textgame.domain.model.NPC> npcs, com.textgame.domain.model.GameState gameState) {
        return null;
    }
    
    private final java.lang.String buildOutputInstruction() {
        return null;
    }
    
    private final com.textgame.domain.model.AIResponse parseAIResponse(java.lang.String content) {
        return null;
    }
    
    private final com.textgame.domain.model.Summary parseSummaryResponse(java.lang.String content, com.textgame.domain.model.GameState gameState) {
        return null;
    }
    
    private final com.textgame.data.remote.ai.GeneratedWorldResult parseGeneratedWorld(java.lang.String content) {
        return null;
    }
    
    private final java.lang.String extractJson(java.lang.String content) {
        return null;
    }
}