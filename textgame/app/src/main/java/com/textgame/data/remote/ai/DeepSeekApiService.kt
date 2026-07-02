package com.textgame.data.remote.ai

import com.google.gson.annotations.SerializedName
import retrofit2.http.Body
import retrofit2.http.POST

interface DeepSeekApiService {
    @POST("chat/completions")
    suspend fun createChatCompletion(@Body request: ChatCompletionRequest): ChatCompletionResponse
}

data class ResponseFormat(
    val type: String
)

data class ThinkingConfig(
    val type: String // "enabled" or "disabled"
)

data class ChatCompletionRequest(
    val model: String,
    val messages: List<ChatMessage>,
    val temperature: Float? = null,
    @SerializedName("max_tokens")
    val maxTokens: Int = 2000,
    @SerializedName("response_format")
    val responseFormat: ResponseFormat? = null,
    @SerializedName("reasoning_effort")
    val reasoningEffort: String? = null,
    val thinking: ThinkingConfig? = null
)

data class ChatMessage(
    val role: String,
    val content: String
)

data class ChatCompletionResponse(
    val id: String? = null,
    val choices: List<ChatChoice> = emptyList(),
    val usage: Usage? = null
)

data class ChatChoice(
    val message: ChatMessage? = null,
    @SerializedName("finish_reason")
    val finishReason: String? = null
)

data class Usage(
    @SerializedName("prompt_tokens")
    val promptTokens: Int = 0,
    @SerializedName("completion_tokens")
    val completionTokens: Int = 0,
    @SerializedName("total_tokens")
    val totalTokens: Int = 0
)
