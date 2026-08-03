package com.textgame.data.remote.seedream

import com.google.gson.annotations.SerializedName
import retrofit2.http.Body
import retrofit2.http.POST

/**
 * BytePlus / 火山引擎 Seedream 文生图 API。
 * 接入参考 SeedHub 项目：https://github.com/Rain-31/SeedHub
 *
 * 端点：POST https://{host}/api/v3/images/generations
 * 鉴权：Authorization: Bearer {apiKey}（由 OkHttp 拦截器统一注入）
 */
interface SeedreamApiService {
    @POST("api/v3/images/generations")
    suspend fun generateImage(@Body request: SeedreamImageRequest): SeedreamImageResponse
}

data class SeedreamImageRequest(
    val model: String,
    val prompt: String,
    @SerializedName("response_format")
    val responseFormat: String = "url",
    val size: String = "2K",
    // 关闭水印
    val watermark: Boolean = false,
    @SerializedName("sequential_image_generation")
    val sequentialImageGeneration: String = "disabled",
    @SerializedName("sequential_image_generation_options")
    val sequentialImageGenerationOptions: SequentialImageOptions = SequentialImageOptions()
)

data class SequentialImageOptions(
    @SerializedName("max_images")
    val maxImages: Int = 1
)

data class SeedreamImageResponse(
    val data: List<SeedreamImageData> = emptyList(),
    val usage: SeedreamUsage? = null
)

data class SeedreamImageData(
    val url: String? = null,
    @SerializedName("b64_json")
    val b64Json: String? = null,
    @SerializedName("revised_prompt")
    val revisedPrompt: String? = null
)

data class SeedreamUsage(
    @SerializedName("generated_images")
    val generatedImages: Int = 0
)
