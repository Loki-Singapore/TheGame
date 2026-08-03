package com.textgame.data.remote.ai;

@kotlin.Metadata(mv = {1, 8, 0}, k = 1, xi = 48, d1 = {"\u0000\u00b8\u0001\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010$\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u0007\u0018\u00002\u00020\u0001:\u0001eBc\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\b\b\u0002\u0010\u0007\u001a\u00020\u0006\u0012\b\b\u0002\u0010\b\u001a\u00020\t\u0012\b\b\u0002\u0010\n\u001a\u00020\u000b\u0012\b\b\u0002\u0010\f\u001a\u00020\t\u0012\b\b\u0002\u0010\r\u001a\u00020\u000b\u0012\b\b\u0002\u0010\u000e\u001a\u00020\u000f\u0012\b\b\u0002\u0010\u0010\u001a\u00020\u0006\u00a2\u0006\u0002\u0010\u0011J\u001a\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00160\u00152\f\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00160\u0015J.\u0010\u0018\u001a\u00020\u00062\b\u0010\u0019\u001a\u0004\u0018\u00010\u001a2\f\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\u00060\u00152\f\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\u00060\u0015H\u0002J*\u0010\u001d\u001a\u00020\u001e2\f\u0010\u001f\u001a\b\u0012\u0004\u0012\u00020 0\u00152\b\b\u0002\u0010!\u001a\u00020\u000f2\b\b\u0002\u0010\"\u001a\u00020\u000bH\u0002JF\u0010#\u001a\u00020\u00062\u0006\u0010$\u001a\u00020%2\f\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00160\u00152\u0006\u0010&\u001a\u00020\'2\u000e\b\u0002\u0010(\u001a\b\u0012\u0004\u0012\u00020)0\u00152\u000e\b\u0002\u0010*\u001a\b\u0012\u0004\u0012\u00020\u00060\u0015H\u0002J\u0010\u0010+\u001a\u00020\u00062\u0006\u0010,\u001a\u00020-H\u0002J<\u0010.\u001a\u00020\u00062\b\u0010/\u001a\u0004\u0018\u0001002\b\u0010$\u001a\u0004\u0018\u00010%2\f\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00160\u00152\b\u0010&\u001a\u0004\u0018\u00010\'2\u0006\u00101\u001a\u00020\u0006H\u0002JH\u00102\u001a\u00020\u00062\u0006\u0010/\u001a\u0002002\f\u00103\u001a\b\u0012\u0004\u0012\u00020\u00060\u00152\u0006\u0010$\u001a\u00020%2\f\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00160\u00152\u0006\u0010&\u001a\u00020\'2\n\b\u0002\u00104\u001a\u0004\u0018\u00010\u001aH\u0002J\u0016\u00105\u001a\u00020\u001e2\f\u0010\u001f\u001a\b\u0012\u0004\u0012\u00020 0\u0015H\u0002J\u0018\u00106\u001a\u00020\u00062\u0006\u0010/\u001a\u0002002\u0006\u00107\u001a\u000208H\u0002J\u0018\u00109\u001a\u00020\u00062\u0006\u0010:\u001a\u00020\u00062\u0006\u0010;\u001a\u00020\u000bH\u0002J\u0016\u0010<\u001a\u00020\u00062\f\u0010=\u001a\b\u0012\u0004\u0012\u00020>0\u0015H\u0002J\u0018\u0010?\u001a\u00020\u00062\u0006\u0010@\u001a\u00020\u00062\u0006\u0010A\u001a\u00020\u000bH\u0002J\u0010\u0010B\u001a\u00020\u00062\u0006\u0010C\u001a\u00020\u0006H\u0002J\u0018\u0010D\u001a\u00020\u00062\u0006\u0010@\u001a\u00020\u00062\u0006\u0010A\u001a\u00020\u000bH\u0002J$\u0010E\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00010F0\u00152\b\u0010G\u001a\u0004\u0018\u00010\u0001H\u0002J,\u0010H\u001a\u00020\u00062\u0006\u0010I\u001a\u00020\u00062\u0006\u0010J\u001a\u00020\u00062\b\u0010G\u001a\u0004\u0018\u00010\u00012\b\u0010K\u001a\u0004\u0018\u00010)H\u0002Jy\u0010L\u001a\u00020M2\u0006\u0010/\u001a\u0002002\u0006\u00107\u001a\u0002082\b\u0010\u0019\u001a\u0004\u0018\u00010\u001a2\f\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\u00060\u00152\f\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\u00060\u00152\u0006\u0010$\u001a\u00020%2\f\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00160\u00152\u0006\u0010&\u001a\u00020\'2\u0006\u0010:\u001a\u00020\u00062\n\b\u0002\u0010N\u001a\u0004\u0018\u00010\u0006H\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010OJM\u0010P\u001a\u00020\u00062\b\u0010/\u001a\u0004\u0018\u0001002\b\u0010$\u001a\u0004\u0018\u00010%2\f\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00160\u00152\b\u0010&\u001a\u0004\u0018\u00010\'2\u0006\u00101\u001a\u00020\u00062\u0006\u0010,\u001a\u00020-H\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010QJ\u0014\u0010R\u001a\u00020\u00062\f\u0010S\u001a\b\u0012\u0004\u0012\u00020\u00160\u0015JQ\u0010T\u001a\u00020\u001a2\u0006\u0010/\u001a\u0002002\f\u00103\u001a\b\u0012\u0004\u0012\u00020\u00060\u00152\u0006\u0010$\u001a\u00020%2\f\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00160\u00152\u0006\u0010&\u001a\u00020\'2\n\b\u0002\u00104\u001a\u0004\u0018\u00010\u001aH\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010UJ\u0019\u0010V\u001a\u00020W2\u0006\u0010X\u001a\u00020\u0006H\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010YJ\u0010\u0010Z\u001a\u00020M2\u0006\u0010C\u001a\u00020\u0006H\u0002J\u0010\u0010[\u001a\u00020W2\u0006\u0010C\u001a\u00020\u0006H\u0002J\u0010\u0010\\\u001a\u00020\u00012\u0006\u0010]\u001a\u00020^H\u0002J\u0018\u0010_\u001a\u00020\u001a2\u0006\u0010C\u001a\u00020\u00062\u0006\u0010&\u001a\u00020\'H\u0002Jt\u0010`\u001a\b\u0012\u0004\u0012\u00020b0a2\u0006\u0010/\u001a\u0002002\u0006\u00107\u001a\u0002082\b\u0010\u0019\u001a\u0004\u0018\u00010\u001a2\f\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\u00060\u00152\f\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\u00060\u00152\u0006\u0010$\u001a\u00020%2\f\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00160\u00152\u0006\u0010&\u001a\u00020\'2\u0006\u0010:\u001a\u00020\u00062\n\b\u0002\u0010N\u001a\u0004\u0018\u00010\u0006J\u0010\u0010c\u001a\u00020\u00062\u0006\u0010d\u001a\u00020\u0006H\u0002R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u0013X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u000bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\tX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u000fX\u0082\u0004\u00a2\u0006\u0002\n\u0000\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u0006f"}, d2 = {"Lcom/textgame/data/remote/ai/AIService;", "", "apiService", "Lcom/textgame/data/remote/ai/DeepSeekApiService;", "streamingApiService", "apiKey", "", "model", "dialogueTemperature", "", "dialogueMaxTokens", "", "summaryTemperature", "summaryMaxTokens", "thinkingEnabled", "", "reasoningEffort", "(Lcom/textgame/data/remote/ai/DeepSeekApiService;Lcom/textgame/data/remote/ai/DeepSeekApiService;Ljava/lang/String;Ljava/lang/String;FIFIZLjava/lang/String;)V", "gson", "Lcom/google/gson/Gson;", "assignNpcIds", "", "Lcom/textgame/domain/model/NPC;", "npcs", "buildDialogueHistoryPrompt", "summary", "Lcom/textgame/domain/model/Summary;", "preSummaryDialogues", "postSummaryDialogues", "buildDialogueRequest", "Lcom/textgame/data/remote/ai/ChatCompletionRequest;", "messages", "Lcom/textgame/data/remote/ai/ChatMessage;", "useJsonFormat", "maxTokens", "buildGameStatePrompt", "protagonist", "Lcom/textgame/domain/model/Protagonist;", "gameState", "Lcom/textgame/domain/model/GameState;", "attributeCategories", "Lcom/textgame/domain/model/AttributeCategory;", "majorPlotThreads", "buildImagePromptSystemPrompt", "style", "Lcom/textgame/data/remote/ai/ImagePromptStyle;", "buildImagePromptUserPrompt", "worldSetting", "Lcom/textgame/domain/model/WorldSetting;", "sceneNarrative", "buildSummaryPrompt", "recentDialogues", "previousSummary", "buildSummaryRequest", "buildSystemPrompt", "backgroundSetting", "Lcom/textgame/domain/model/BackgroundSetting;", "buildUserPrompt", "userInput", "turnCount", "buildWorldRulesPrompt", "worldRules", "Lcom/textgame/domain/model/WorldRule;", "extractDialogueDelta", "fullContent", "lastLen", "extractJson", "content", "extractNarrativeDelta", "extractTableRows", "", "value", "formatAttributeLine", "indent", "key", "cat", "generateDialogueResponse", "Lcom/textgame/domain/model/AIResponse;", "directorDirective", "(Lcom/textgame/domain/model/WorldSetting;Lcom/textgame/domain/model/BackgroundSetting;Lcom/textgame/domain/model/Summary;Ljava/util/List;Ljava/util/List;Lcom/textgame/domain/model/Protagonist;Ljava/util/List;Lcom/textgame/domain/model/GameState;Ljava/lang/String;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "generateImagePrompt", "(Lcom/textgame/domain/model/WorldSetting;Lcom/textgame/domain/model/Protagonist;Ljava/util/List;Lcom/textgame/domain/model/GameState;Ljava/lang/String;Lcom/textgame/data/remote/ai/ImagePromptStyle;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "generateNextNpcId", "existingNpcs", "generateSummary", "(Lcom/textgame/domain/model/WorldSetting;Ljava/util/List;Lcom/textgame/domain/model/Protagonist;Ljava/util/List;Lcom/textgame/domain/model/GameState;Lcom/textgame/domain/model/Summary;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "generateWorldFromPrompt", "Lcom/textgame/data/remote/ai/GeneratedWorldResult;", "userPrompt", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "parseAIResponse", "parseGeneratedWorld", "parseScalarJsonValue", "v", "Lcom/google/gson/JsonElement;", "parseSummaryResponse", "streamDialogueResponse", "Lkotlinx/coroutines/flow/Flow;", "Lcom/textgame/domain/model/StreamingChunk;", "unescapeJsonString", "s", "JsonStreamingParser", "app_debug"})
public final class AIService {
    @org.jetbrains.annotations.NotNull
    private final com.textgame.data.remote.ai.DeepSeekApiService apiService = null;
    @org.jetbrains.annotations.NotNull
    private final com.textgame.data.remote.ai.DeepSeekApiService streamingApiService = null;
    @org.jetbrains.annotations.NotNull
    private final java.lang.String apiKey = null;
    @org.jetbrains.annotations.NotNull
    private final java.lang.String model = null;
    private final float dialogueTemperature = 0.0F;
    private final int dialogueMaxTokens = 0;
    private final float summaryTemperature = 0.0F;
    private final int summaryMaxTokens = 0;
    private final boolean thinkingEnabled = false;
    @org.jetbrains.annotations.NotNull
    private final java.lang.String reasoningEffort = null;
    @org.jetbrains.annotations.NotNull
    private final com.google.gson.Gson gson = null;
    
    public AIService(@org.jetbrains.annotations.NotNull
    com.textgame.data.remote.ai.DeepSeekApiService apiService, @org.jetbrains.annotations.NotNull
    com.textgame.data.remote.ai.DeepSeekApiService streamingApiService, @org.jetbrains.annotations.NotNull
    java.lang.String apiKey, @org.jetbrains.annotations.NotNull
    java.lang.String model, float dialogueTemperature, int dialogueMaxTokens, float summaryTemperature, int summaryMaxTokens, boolean thinkingEnabled, @org.jetbrains.annotations.NotNull
    java.lang.String reasoningEffort) {
        super();
    }
    
    /**
     * 构建带思考模式参数的对话请求
     */
    private final com.textgame.data.remote.ai.ChatCompletionRequest buildDialogueRequest(java.util.List<com.textgame.data.remote.ai.ChatMessage> messages, boolean useJsonFormat, int maxTokens) {
        return null;
    }
    
    /**
     * 构建带思考模式参数的总结请求
     */
    private final com.textgame.data.remote.ai.ChatCompletionRequest buildSummaryRequest(java.util.List<com.textgame.data.remote.ai.ChatMessage> messages) {
        return null;
    }
    
    /**
     * 为NPC列表分配唯一ID（如果尚未分配）
     * 格式：npc_001, npc_002, ...
     */
    @org.jetbrains.annotations.NotNull
    public final java.util.List<com.textgame.domain.model.NPC> assignNpcIds(@org.jetbrains.annotations.NotNull
    java.util.List<com.textgame.domain.model.NPC> npcs) {
        return null;
    }
    
    /**
     * 为单个NPC生成下一个可用的ID
     */
    @org.jetbrains.annotations.NotNull
    public final java.lang.String generateNextNpcId(@org.jetbrains.annotations.NotNull
    java.util.List<com.textgame.domain.model.NPC> existingNpcs) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.Object generateDialogueResponse(@org.jetbrains.annotations.NotNull
    com.textgame.domain.model.WorldSetting worldSetting, @org.jetbrains.annotations.NotNull
    com.textgame.domain.model.BackgroundSetting backgroundSetting, @org.jetbrains.annotations.Nullable
    com.textgame.domain.model.Summary summary, @org.jetbrains.annotations.NotNull
    java.util.List<java.lang.String> preSummaryDialogues, @org.jetbrains.annotations.NotNull
    java.util.List<java.lang.String> postSummaryDialogues, @org.jetbrains.annotations.NotNull
    com.textgame.domain.model.Protagonist protagonist, @org.jetbrains.annotations.NotNull
    java.util.List<com.textgame.domain.model.NPC> npcs, @org.jetbrains.annotations.NotNull
    com.textgame.domain.model.GameState gameState, @org.jetbrains.annotations.NotNull
    java.lang.String userInput, @org.jetbrains.annotations.Nullable
    java.lang.String directorDirective, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super com.textgame.domain.model.AIResponse> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.flow.Flow<com.textgame.domain.model.StreamingChunk> streamDialogueResponse(@org.jetbrains.annotations.NotNull
    com.textgame.domain.model.WorldSetting worldSetting, @org.jetbrains.annotations.NotNull
    com.textgame.domain.model.BackgroundSetting backgroundSetting, @org.jetbrains.annotations.Nullable
    com.textgame.domain.model.Summary summary, @org.jetbrains.annotations.NotNull
    java.util.List<java.lang.String> preSummaryDialogues, @org.jetbrains.annotations.NotNull
    java.util.List<java.lang.String> postSummaryDialogues, @org.jetbrains.annotations.NotNull
    com.textgame.domain.model.Protagonist protagonist, @org.jetbrains.annotations.NotNull
    java.util.List<com.textgame.domain.model.NPC> npcs, @org.jetbrains.annotations.NotNull
    com.textgame.domain.model.GameState gameState, @org.jetbrains.annotations.NotNull
    java.lang.String userInput, @org.jetbrains.annotations.Nullable
    java.lang.String directorDirective) {
        return null;
    }
    
    private final java.lang.String extractNarrativeDelta(java.lang.String fullContent, int lastLen) {
        return null;
    }
    
    private final java.lang.String extractDialogueDelta(java.lang.String fullContent, int lastLen) {
        return null;
    }
    
    private final java.lang.String unescapeJsonString(java.lang.String s) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.Object generateSummary(@org.jetbrains.annotations.NotNull
    com.textgame.domain.model.WorldSetting worldSetting, @org.jetbrains.annotations.NotNull
    java.util.List<java.lang.String> recentDialogues, @org.jetbrains.annotations.NotNull
    com.textgame.domain.model.Protagonist protagonist, @org.jetbrains.annotations.NotNull
    java.util.List<com.textgame.domain.model.NPC> npcs, @org.jetbrains.annotations.NotNull
    com.textgame.domain.model.GameState gameState, @org.jetbrains.annotations.Nullable
    com.textgame.domain.model.Summary previousSummary, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super com.textgame.domain.model.Summary> $completion) {
        return null;
    }
    
    /**
     * 根据当前游戏场景生成 AI 图片提示词。
     * @param style 风格：REALISTIC（高真实感）或 ANIME（动漫化）
     */
    @org.jetbrains.annotations.Nullable
    public final java.lang.Object generateImagePrompt(@org.jetbrains.annotations.Nullable
    com.textgame.domain.model.WorldSetting worldSetting, @org.jetbrains.annotations.Nullable
    com.textgame.domain.model.Protagonist protagonist, @org.jetbrains.annotations.NotNull
    java.util.List<com.textgame.domain.model.NPC> npcs, @org.jetbrains.annotations.Nullable
    com.textgame.domain.model.GameState gameState, @org.jetbrains.annotations.NotNull
    java.lang.String sceneNarrative, @org.jetbrains.annotations.NotNull
    com.textgame.data.remote.ai.ImagePromptStyle style, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super java.lang.String> $completion) {
        return null;
    }
    
    private final java.lang.String buildImagePromptSystemPrompt(com.textgame.data.remote.ai.ImagePromptStyle style) {
        return null;
    }
    
    private final java.lang.String buildImagePromptUserPrompt(com.textgame.domain.model.WorldSetting worldSetting, com.textgame.domain.model.Protagonist protagonist, java.util.List<com.textgame.domain.model.NPC> npcs, com.textgame.domain.model.GameState gameState, java.lang.String sceneNarrative) {
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
    
    private final java.lang.String buildWorldRulesPrompt(java.util.List<com.textgame.domain.model.WorldRule> worldRules) {
        return null;
    }
    
    private final java.lang.String buildDialogueHistoryPrompt(com.textgame.domain.model.Summary summary, java.util.List<java.lang.String> preSummaryDialogues, java.util.List<java.lang.String> postSummaryDialogues) {
        return null;
    }
    
    /**
     * 格式化单条属性供 AI 阅读。
     * - 标量属性：单行展示 "  名称: 值  [类型:... 最小:... 最大:... - 描述]"
     * - TABLE 属性：先展示列定义与元信息，再逐行展示表格内容，便于 AI 理解结构。
     */
    private final java.lang.String formatAttributeLine(java.lang.String indent, java.lang.String key, java.lang.Object value, com.textgame.domain.model.AttributeCategory cat) {
        return null;
    }
    
    /**
     * 把 TABLE 属性值（可能来自 Gson 反序列化为 List<Map<String, Any>>，
     * 也可能来自旧数据为 List<LinkedTreeMap>）统一成 List<Map<String, Any>>。
     */
    private final java.util.List<java.util.Map<java.lang.String, java.lang.Object>> extractTableRows(java.lang.Object value) {
        return null;
    }
    
    private final java.lang.String buildGameStatePrompt(com.textgame.domain.model.Protagonist protagonist, java.util.List<com.textgame.domain.model.NPC> npcs, com.textgame.domain.model.GameState gameState, java.util.List<com.textgame.domain.model.AttributeCategory> attributeCategories, java.util.List<java.lang.String> majorPlotThreads) {
        return null;
    }
    
    private final java.lang.String buildUserPrompt(java.lang.String userInput, int turnCount) {
        return null;
    }
    
    private final java.lang.String buildSummaryPrompt(com.textgame.domain.model.WorldSetting worldSetting, java.util.List<java.lang.String> recentDialogues, com.textgame.domain.model.Protagonist protagonist, java.util.List<com.textgame.domain.model.NPC> npcs, com.textgame.domain.model.GameState gameState, com.textgame.domain.model.Summary previousSummary) {
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
    
    /**
     * 解析 TABLE 单元格的标量值。Gson 的 JsonElement 已经携带类型信息，
     * 这里转成 Kotlin 友好的 Any（Double / Boolean / String）。
     */
    private final java.lang.Object parseScalarJsonValue(com.google.gson.JsonElement v) {
        return null;
    }
    
    @kotlin.Metadata(mv = {1, 8, 0}, k = 1, xi = 48, d1 = {"\u0000D\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010%\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\f\n\u0002\b\u0002\b\u0002\u0018\u00002\u00020\u0001:\u0001\u0018B\u0005\u00a2\u0006\u0002\u0010\u0002J\u000e\u0010\u0012\u001a\u00020\b2\u0006\u0010\u0013\u001a\u00020\bJ\u000e\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u0017R\u0012\u0010\u0003\u001a\u00060\u0004j\u0002`\u0005X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001e\u0010\u0006\u001a\u0012\u0012\u0004\u0012\u00020\b\u0012\b\u0012\u00060\u0004j\u0002`\u00050\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\nX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\nX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0010X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0011\u001a\u0004\u0018\u00010\bX\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0019"}, d2 = {"Lcom/textgame/data/remote/ai/AIService$JsonStreamingParser;", "", "()V", "currentField", "Ljava/lang/StringBuilder;", "Lkotlin/text/StringBuilder;", "fieldValues", "", "", "inEscape", "", "nestingDepth", "", "skipInEscape", "skipInString", "state", "Lcom/textgame/data/remote/ai/AIService$JsonStreamingParser$State;", "targetField", "getFieldValue", "fieldName", "processChar", "", "c", "", "State", "app_debug"})
    static final class JsonStreamingParser {
        @org.jetbrains.annotations.NotNull
        private com.textgame.data.remote.ai.AIService.JsonStreamingParser.State state = com.textgame.data.remote.ai.AIService.JsonStreamingParser.State.INITIAL;
        @org.jetbrains.annotations.NotNull
        private java.lang.StringBuilder currentField;
        @org.jetbrains.annotations.NotNull
        private final java.util.Map<java.lang.String, java.lang.StringBuilder> fieldValues = null;
        private boolean inEscape = false;
        @org.jetbrains.annotations.Nullable
        private java.lang.String targetField;
        private int nestingDepth = 0;
        private boolean skipInString = false;
        private boolean skipInEscape = false;
        
        public JsonStreamingParser() {
            super();
        }
        
        public final void processChar(char c) {
        }
        
        @org.jetbrains.annotations.NotNull
        public final java.lang.String getFieldValue(@org.jetbrains.annotations.NotNull
        java.lang.String fieldName) {
            return null;
        }
        
        @kotlin.Metadata(mv = {1, 8, 0}, k = 1, xi = 48, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\n\b\u0082\u0001\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004j\u0002\b\u0005j\u0002\b\u0006j\u0002\b\u0007j\u0002\b\bj\u0002\b\tj\u0002\b\n\u00a8\u0006\u000b"}, d2 = {"Lcom/textgame/data/remote/ai/AIService$JsonStreamingParser$State;", "", "(Ljava/lang/String;I)V", "INITIAL", "AFTER_BRACE", "IN_FIELD_NAME", "AFTER_FIELD_NAME", "AFTER_COLON", "IN_STRING_VALUE", "AFTER_STRING_VALUE", "SKIPPING_VALUE", "app_debug"})
        static enum State {
            /*public static final*/ INITIAL /* = new INITIAL() */,
            /*public static final*/ AFTER_BRACE /* = new AFTER_BRACE() */,
            /*public static final*/ IN_FIELD_NAME /* = new IN_FIELD_NAME() */,
            /*public static final*/ AFTER_FIELD_NAME /* = new AFTER_FIELD_NAME() */,
            /*public static final*/ AFTER_COLON /* = new AFTER_COLON() */,
            /*public static final*/ IN_STRING_VALUE /* = new IN_STRING_VALUE() */,
            /*public static final*/ AFTER_STRING_VALUE /* = new AFTER_STRING_VALUE() */,
            /*public static final*/ SKIPPING_VALUE /* = new SKIPPING_VALUE() */;
            
            State() {
            }
        }
    }
}