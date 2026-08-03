package com.textgame.data.seedream

import android.content.ContentValues
import android.content.Context
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import com.textgame.data.remote.seedream.SeedreamApiService
import com.textgame.data.remote.seedream.SeedreamImageRequest
import com.textgame.data.remote.seedream.SeedreamImageResponse
import com.textgame.data.remote.seedream.SequentialImageOptions
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.Base64
import java.util.concurrent.TimeUnit

/**
 * 生图结果：优先返回图片 URL；若 API 以 b64_json 返回则为 base64 字符串。
 */
data class GeneratedImageResult(
    val url: String? = null,
    val base64: String? = null,
    val revisedPrompt: String? = null
) {
    fun hasUrl(): Boolean = !url.isNullOrBlank()
    fun hasBase64(): Boolean = !base64.isNullOrBlank()
}

class SeedreamService(
    private val apiKey: String,
    private val baseUrl: String,
    private val model: String
) {
    private var apiService: SeedreamApiService? = null

    init {
        rebuild()
    }

    /**
     * 重新构建 Retrofit 实例（配置变更后调用）。
     * baseUrl 形如 "ark.ap-southeast.bytepluses.com"，需要补全协议并确保以 "/" 结尾。
     */
    fun rebuild() {
        if (apiKey.isBlank() || baseUrl.isBlank()) {
            apiService = null
            return
        }
        val host = baseUrl.trim()
        val normalizedHost = if (host.startsWith("http")) host else "https://$host"
        val base = if (normalizedHost.endsWith("/")) normalizedHost else "$normalizedHost/"

        val client = OkHttpClient.Builder()
            .addInterceptor { chain ->
                val request = chain.request().newBuilder()
                    .header("Authorization", "Bearer $apiKey")
                    .header("Content-Type", "application/json")
                    .build()
                chain.proceed(request)
            }
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BASIC
            })
            // 5.0 Pro 生成 2K 图片可能需要 3-5 分钟，readTimeout 设为 10 分钟保险
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(600, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .callTimeout(660, TimeUnit.SECONDS)
            .build()

        // Gson 默认不序列化 null 字段，这样 5.0 Pro 调用时
        // sequential_image_generation 等不支持的参数（值为 null）不会发送
        val gson = com.google.gson.GsonBuilder()
            .disableHtmlEscaping()
            .create()

        val retrofit = Retrofit.Builder()
            .baseUrl(base)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()

        apiService = retrofit.create(SeedreamApiService::class.java)
    }

    fun isConfigured(): Boolean = apiKey.isNotBlank() && baseUrl.isNotBlank()

    suspend fun generateImage(prompt: String, size: String): GeneratedImageResult = withContext(Dispatchers.IO) {
        val service = apiService ?: throw IllegalStateException("生图服务未配置，请在 AI 设置中填写生图 API Key 与域名")
        // 5.0 Pro（dola-seedream-5-0-pro-260628）不支持 sequential_image_generation 参数，
        // 传了会报 HTTP 400；其他模型可传 disabled（仅生成 1 张）
        val isProModel = model.contains("5-0-pro", ignoreCase = true)
        val request = SeedreamImageRequest(
            model = model,
            prompt = prompt,
            size = size,
            sequentialImageGeneration = if (isProModel) null else "disabled",
            sequentialImageGenerationOptions = if (isProModel) null else SequentialImageOptions(maxImages = 1)
        )
        val response: SeedreamImageResponse = try {
            service.generateImage(request)
        } catch (e: retrofit2.HttpException) {
            // 提取服务器返回的错误信息，便于排查 HTTP 400 等问题
            val errorBody = try {
                e.response()?.errorBody()?.string()
            } catch (_: Exception) { null }
            throw IllegalStateException(
                "生图请求失败：HTTP ${e.code()} ${e.message()}" +
                    (errorBody?.let { "\n服务器响应：$it" } ?: ""),
                e
            )
        } catch (e: Exception) {
            throw IllegalStateException("生图请求失败：${e.message}", e)
        }
        val first = response.data.firstOrNull()
            ?: throw IllegalStateException("生图响应为空")
        GeneratedImageResult(
            url = first.url,
            base64 = first.b64Json,
            revisedPrompt = first.revisedPrompt
        )
    }

    /**
     * 保存图片到系统相册（Pictures/TextGame）。
     * @param imageUrl 图片 URL，下载后写入 MediaStore。
     * @param displayName 文件名（不带扩展名）。
     * @return 保存成功后的 contentUri
     */
    suspend fun saveImageToGallery(
        context: Context,
        imageUrl: String,
        displayName: String
    ): Uri = withContext(Dispatchers.IO) {
        val client = OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .build()
        val request = okhttp3.Request.Builder().url(imageUrl).build()
        val response = client.newCall(request).execute()
        if (!response.isSuccessful) {
            response.close()
            throw IllegalStateException("下载图片失败：HTTP ${response.code}")
        }
        val bytes = response.body?.bytes()
            ?: run {
                response.close()
                throw IllegalStateException("下载图片失败：响应为空")
            }
        response.close()

        val resolver = context.contentResolver
        val collection = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            MediaStore.Images.Media.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY)
        } else {
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        }
        val fileName = "$displayName.png"
        val values = ContentValues().apply {
            put(MediaStore.Images.Media.DISPLAY_NAME, fileName)
            put(MediaStore.Images.Media.MIME_TYPE, "image/png")
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                put(MediaStore.Images.Media.RELATIVE_PATH, "${Environment.DIRECTORY_PICTURES}/TextGame")
                put(MediaStore.Images.Media.IS_PENDING, 1)
            }
        }
        val uri = resolver.insert(collection, values)
            ?: throw IllegalStateException("无法创建图库文件")
        resolver.openOutputStream(uri)?.use { out ->
            out.write(bytes)
            out.flush()
        } ?: throw IllegalStateException("无法写入图库文件")

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            values.clear()
            values.put(MediaStore.Images.Media.IS_PENDING, 0)
            resolver.update(uri, values, null, null)
        }
        uri
    }

    /**
     * 保存 base64 编码的图片到系统相册。
     */
    suspend fun saveBase64ToGallery(
        context: Context,
        base64: String,
        displayName: String
    ): Uri = withContext(Dispatchers.IO) {
        val bytes = Base64.getDecoder().decode(base64)
        val resolver = context.contentResolver
        val collection = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            MediaStore.Images.Media.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY)
        } else {
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        }
        val fileName = "$displayName.png"
        val values = ContentValues().apply {
            put(MediaStore.Images.Media.DISPLAY_NAME, fileName)
            put(MediaStore.Images.Media.MIME_TYPE, "image/png")
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                put(MediaStore.Images.Media.RELATIVE_PATH, "${Environment.DIRECTORY_PICTURES}/TextGame")
                put(MediaStore.Images.Media.IS_PENDING, 1)
            }
        }
        val uri = resolver.insert(collection, values)
            ?: throw IllegalStateException("无法创建图库文件")
        resolver.openOutputStream(uri)?.use { out ->
            out.write(bytes)
            out.flush()
        } ?: throw IllegalStateException("无法写入图库文件")

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            values.clear()
            values.put(MediaStore.Images.Media.IS_PENDING, 0)
            resolver.update(uri, values, null, null)
        }
        uri
    }
}
