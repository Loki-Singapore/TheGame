package com.textgame.data.remote.ai;

@kotlin.Metadata(mv = {1, 8, 0}, k = 1, xi = 48, d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0007\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b#\b\u0087\b\u0018\u00002\u00020\u0001Bk\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005\u0012\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\b\u0012\b\b\u0002\u0010\t\u001a\u00020\n\u0012\n\b\u0002\u0010\u000b\u001a\u0004\u0018\u00010\f\u0012\n\b\u0002\u0010\r\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u000e\u001a\u0004\u0018\u00010\u000f\u0012\b\b\u0002\u0010\u0010\u001a\u00020\u0011\u0012\n\b\u0002\u0010\u0012\u001a\u0004\u0018\u00010\u0013\u00a2\u0006\u0002\u0010\u0014J\t\u0010\'\u001a\u00020\u0003H\u00c6\u0003J\u000f\u0010(\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005H\u00c6\u0003J\u0010\u0010)\u001a\u0004\u0018\u00010\bH\u00c6\u0003\u00a2\u0006\u0002\u0010#J\t\u0010*\u001a\u00020\nH\u00c6\u0003J\u000b\u0010+\u001a\u0004\u0018\u00010\fH\u00c6\u0003J\u000b\u0010,\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003J\u000b\u0010-\u001a\u0004\u0018\u00010\u000fH\u00c6\u0003J\t\u0010.\u001a\u00020\u0011H\u00c6\u0003J\u000b\u0010/\u001a\u0004\u0018\u00010\u0013H\u00c6\u0003Jx\u00100\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\u000e\b\u0002\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u00052\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\b2\b\b\u0002\u0010\t\u001a\u00020\n2\n\b\u0002\u0010\u000b\u001a\u0004\u0018\u00010\f2\n\b\u0002\u0010\r\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u000e\u001a\u0004\u0018\u00010\u000f2\b\b\u0002\u0010\u0010\u001a\u00020\u00112\n\b\u0002\u0010\u0012\u001a\u0004\u0018\u00010\u0013H\u00c6\u0001\u00a2\u0006\u0002\u00101J\u0013\u00102\u001a\u00020\u00112\b\u00103\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u00104\u001a\u00020\nH\u00d6\u0001J\t\u00105\u001a\u00020\u0003H\u00d6\u0001R\u0016\u0010\t\u001a\u00020\n8\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0016R\u0017\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0018R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u001aR\u0018\u0010\r\u001a\u0004\u0018\u00010\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u001aR\u0018\u0010\u000b\u001a\u0004\u0018\u00010\f8\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u001dR\u0011\u0010\u0010\u001a\u00020\u0011\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001e\u0010\u001fR\u0018\u0010\u0012\u001a\u0004\u0018\u00010\u00138\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b \u0010!R\u0015\u0010\u0007\u001a\u0004\u0018\u00010\b\u00a2\u0006\n\n\u0002\u0010$\u001a\u0004\b\"\u0010#R\u0013\u0010\u000e\u001a\u0004\u0018\u00010\u000f\u00a2\u0006\b\n\u0000\u001a\u0004\b%\u0010&\u00a8\u00066"}, d2 = {"Lcom/textgame/data/remote/ai/ChatCompletionRequest;", "", "model", "", "messages", "", "Lcom/textgame/data/remote/ai/ChatMessage;", "temperature", "", "maxTokens", "", "responseFormat", "Lcom/textgame/data/remote/ai/ResponseFormat;", "reasoningEffort", "thinking", "Lcom/textgame/data/remote/ai/ThinkingConfig;", "stream", "", "streamOptions", "Lcom/textgame/data/remote/ai/StreamOptions;", "(Ljava/lang/String;Ljava/util/List;Ljava/lang/Float;ILcom/textgame/data/remote/ai/ResponseFormat;Ljava/lang/String;Lcom/textgame/data/remote/ai/ThinkingConfig;ZLcom/textgame/data/remote/ai/StreamOptions;)V", "getMaxTokens", "()I", "getMessages", "()Ljava/util/List;", "getModel", "()Ljava/lang/String;", "getReasoningEffort", "getResponseFormat", "()Lcom/textgame/data/remote/ai/ResponseFormat;", "getStream", "()Z", "getStreamOptions", "()Lcom/textgame/data/remote/ai/StreamOptions;", "getTemperature", "()Ljava/lang/Float;", "Ljava/lang/Float;", "getThinking", "()Lcom/textgame/data/remote/ai/ThinkingConfig;", "component1", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "(Ljava/lang/String;Ljava/util/List;Ljava/lang/Float;ILcom/textgame/data/remote/ai/ResponseFormat;Ljava/lang/String;Lcom/textgame/data/remote/ai/ThinkingConfig;ZLcom/textgame/data/remote/ai/StreamOptions;)Lcom/textgame/data/remote/ai/ChatCompletionRequest;", "equals", "other", "hashCode", "toString", "app_debug"})
public final class ChatCompletionRequest {
    @org.jetbrains.annotations.NotNull
    private final java.lang.String model = null;
    @org.jetbrains.annotations.NotNull
    private final java.util.List<com.textgame.data.remote.ai.ChatMessage> messages = null;
    @org.jetbrains.annotations.Nullable
    private final java.lang.Float temperature = null;
    @com.google.gson.annotations.SerializedName(value = "max_tokens")
    private final int maxTokens = 0;
    @com.google.gson.annotations.SerializedName(value = "response_format")
    @org.jetbrains.annotations.Nullable
    private final com.textgame.data.remote.ai.ResponseFormat responseFormat = null;
    @com.google.gson.annotations.SerializedName(value = "reasoning_effort")
    @org.jetbrains.annotations.Nullable
    private final java.lang.String reasoningEffort = null;
    @org.jetbrains.annotations.Nullable
    private final com.textgame.data.remote.ai.ThinkingConfig thinking = null;
    private final boolean stream = false;
    @com.google.gson.annotations.SerializedName(value = "stream_options")
    @org.jetbrains.annotations.Nullable
    private final com.textgame.data.remote.ai.StreamOptions streamOptions = null;
    
    public ChatCompletionRequest(@org.jetbrains.annotations.NotNull
    java.lang.String model, @org.jetbrains.annotations.NotNull
    java.util.List<com.textgame.data.remote.ai.ChatMessage> messages, @org.jetbrains.annotations.Nullable
    java.lang.Float temperature, int maxTokens, @org.jetbrains.annotations.Nullable
    com.textgame.data.remote.ai.ResponseFormat responseFormat, @org.jetbrains.annotations.Nullable
    java.lang.String reasoningEffort, @org.jetbrains.annotations.Nullable
    com.textgame.data.remote.ai.ThinkingConfig thinking, boolean stream, @org.jetbrains.annotations.Nullable
    com.textgame.data.remote.ai.StreamOptions streamOptions) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String getModel() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.util.List<com.textgame.data.remote.ai.ChatMessage> getMessages() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.Float getTemperature() {
        return null;
    }
    
    public final int getMaxTokens() {
        return 0;
    }
    
    @org.jetbrains.annotations.Nullable
    public final com.textgame.data.remote.ai.ResponseFormat getResponseFormat() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.String getReasoningEffort() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final com.textgame.data.remote.ai.ThinkingConfig getThinking() {
        return null;
    }
    
    public final boolean getStream() {
        return false;
    }
    
    @org.jetbrains.annotations.Nullable
    public final com.textgame.data.remote.ai.StreamOptions getStreamOptions() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String component1() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.util.List<com.textgame.data.remote.ai.ChatMessage> component2() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.Float component3() {
        return null;
    }
    
    public final int component4() {
        return 0;
    }
    
    @org.jetbrains.annotations.Nullable
    public final com.textgame.data.remote.ai.ResponseFormat component5() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.String component6() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final com.textgame.data.remote.ai.ThinkingConfig component7() {
        return null;
    }
    
    public final boolean component8() {
        return false;
    }
    
    @org.jetbrains.annotations.Nullable
    public final com.textgame.data.remote.ai.StreamOptions component9() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final com.textgame.data.remote.ai.ChatCompletionRequest copy(@org.jetbrains.annotations.NotNull
    java.lang.String model, @org.jetbrains.annotations.NotNull
    java.util.List<com.textgame.data.remote.ai.ChatMessage> messages, @org.jetbrains.annotations.Nullable
    java.lang.Float temperature, int maxTokens, @org.jetbrains.annotations.Nullable
    com.textgame.data.remote.ai.ResponseFormat responseFormat, @org.jetbrains.annotations.Nullable
    java.lang.String reasoningEffort, @org.jetbrains.annotations.Nullable
    com.textgame.data.remote.ai.ThinkingConfig thinking, boolean stream, @org.jetbrains.annotations.Nullable
    com.textgame.data.remote.ai.StreamOptions streamOptions) {
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