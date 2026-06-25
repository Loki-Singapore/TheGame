package com.textgame.data.remote.ai;

@kotlin.Metadata(mv = {1, 8, 0}, k = 1, xi = 48, d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\bf\u0018\u00002\u00020\u0001J\u001b\u0010\u0002\u001a\u00020\u00032\b\b\u0001\u0010\u0004\u001a\u00020\u0005H\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0006\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u0006\u0007"}, d2 = {"Lcom/textgame/data/remote/ai/DeepSeekApiService;", "", "createChatCompletion", "Lcom/textgame/data/remote/ai/ChatCompletionResponse;", "request", "Lcom/textgame/data/remote/ai/ChatCompletionRequest;", "(Lcom/textgame/data/remote/ai/ChatCompletionRequest;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "app_debug"})
public abstract interface DeepSeekApiService {
    
    @retrofit2.http.POST(value = "chat/completions")
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object createChatCompletion(@retrofit2.http.Body
    @org.jetbrains.annotations.NotNull
    com.textgame.data.remote.ai.ChatCompletionRequest request, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super com.textgame.data.remote.ai.ChatCompletionResponse> $completion);
}