package com.textgame.data.remote.ai

import com.google.gson.annotations.SerializedName
import okhttp3.ResponseBody
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Streaming
import retrofit2.Call

interface DeepSeekApiService {
    @POST("chat/completions")
    suspend fun createChatCompletion(@Body request: ChatCompletionRequest): ChatCompletionResponse

    @Streaming
    @POST("chat/completions")
    fun createChatCompletionStream(@Body request: ChatCompletionRequest): Call<ResponseBody>
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
    val thinking: ThinkingConfig? = null,
    val stream: Boolean = false,
    @SerializedName("stream_options")
    val streamOptions: StreamOptions? = null
)

data class StreamOptions(
    @SerializedName("include_usage")
    val includeUsage: Boolean = true
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
    val delta: ChatMessage? = null,
    @SerializedName("finish_reason")
    val finishReason: String? = null
)

data class ChatCompletionStreamResponse(
    val id: String? = null,
    val choices: List<ChatChoice> = emptyList(),
    val usage: Usage? = null
)

data class Usage(
    @SerializedName("prompt_tokens")
    val promptTokens: Int = 0,
    @SerializedName("completion_tokens")
    val completionTokens: Int = 0,
    @SerializedName("total_tokens")
    val totalTokens: Int = 0
)
