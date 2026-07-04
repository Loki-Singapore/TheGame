package com.textgame.di

import android.content.Context
import androidx.room.Room
import com.textgame.data.local.SettingsPreferences
import com.textgame.data.local.db.GameDatabase
import com.textgame.data.local.db.dao.BackgroundSettingDao
import com.textgame.data.local.db.dao.DialogueDao
import com.textgame.data.local.db.dao.GameStateDao
import com.textgame.data.local.db.dao.NPCDao
import com.textgame.data.local.db.dao.ProtagonistDao
import com.textgame.data.local.db.dao.SessionDao
import com.textgame.data.local.db.dao.StateSnapshotDao
import com.textgame.data.local.db.dao.SummaryDao
import com.textgame.data.local.db.dao.WorldSettingDao
import com.textgame.data.remote.ai.AIService
import com.textgame.data.remote.ai.DeepSeekApiService
import com.textgame.data.repository.GameRepositoryImpl
import com.textgame.domain.repository.GameRepository
import com.textgame.domain.usecase.CreateGameUseCase
import com.textgame.domain.usecase.DeleteSessionUseCase
import com.textgame.domain.usecase.GenerateSummaryUseCase
import com.textgame.domain.usecase.GetAllSessionsUseCase
import com.textgame.domain.usecase.SendDialogueUseCase
import com.textgame.domain.usecase.SyncSettingsUseCase
import com.textgame.domain.usecase.UpdateStateUseCase
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object AppModule {
    private var database: GameDatabase? = null
    private var gameRepository: GameRepository? = null
    private var aiService: AIService? = null

    private var createGameUseCase: CreateGameUseCase? = null
    private var sendDialogueUseCase: SendDialogueUseCase? = null
    private var updateStateUseCase: UpdateStateUseCase? = null
    private var generateSummaryUseCase: GenerateSummaryUseCase? = null
    private var syncSettingsUseCase: SyncSettingsUseCase? = null
    private var getAllSessionsUseCase: GetAllSessionsUseCase? = null
    private var deleteSessionUseCase: DeleteSessionUseCase? = null

    private var currentSettings: SettingsPreferences = SettingsPreferences.DEFAULTS

    fun initialize(context: Context) {
        if (database == null) {
            database = Room.databaseBuilder(
                context.applicationContext,
                GameDatabase::class.java,
                "text_game_database"
            ).fallbackToDestructiveMigration().build()
        }
    }

    fun configureAI(settings: SettingsPreferences) {
        this.currentSettings = settings
        aiService = null
        // 清除依赖AIService的UseCase缓存，下次获取时会用新配置重建
        sendDialogueUseCase = null
        generateSummaryUseCase = null
    }

    fun getCurrentSettings(): SettingsPreferences = currentSettings

    private fun getDatabase(): GameDatabase {
        return database ?: throw IllegalStateException("AppModule not initialized")
    }

    fun getSessionDao(): SessionDao = getDatabase().sessionDao()
    fun getProtagonistDao(): ProtagonistDao = getDatabase().protagonistDao()
    fun getNPCDao(): NPCDao = getDatabase().npcDao()
    fun getGameStateDao(): GameStateDao = getDatabase().gameStateDao()
    fun getSummaryDao(): SummaryDao = getDatabase().summaryDao()
    fun getWorldSettingDao(): WorldSettingDao = getDatabase().worldSettingDao()
    fun getBackgroundSettingDao(): BackgroundSettingDao = getDatabase().backgroundSettingDao()
    fun getDialogueDao(): DialogueDao = getDatabase().dialogueDao()
    fun getStateSnapshotDao(): StateSnapshotDao = getDatabase().stateSnapshotDao()

    fun getGameRepository(): GameRepository {
        if (gameRepository == null) {
            gameRepository = GameRepositoryImpl(
                sessionDao = getSessionDao(),
                protagonistDao = getProtagonistDao(),
                npcDao = getNPCDao(),
                gameStateDao = getGameStateDao(),
                summaryDao = getSummaryDao(),
                worldSettingDao = getWorldSettingDao(),
                backgroundSettingDao = getBackgroundSettingDao(),
                dialogueDao = getDialogueDao(),
                stateSnapshotDao = getStateSnapshotDao()
            )
        }
        return gameRepository!!
    }

    fun getAIService(): AIService {
        if (aiService == null) {
            val settings = currentSettings

            // 普通请求的OkHttpClient（带HEADERS日志）
            val logging = HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.HEADERS
            }

            // 流式请求的OkHttpClient（不带BODY日志，避免缓冲整个响应体）
            val streamingClient = OkHttpClient.Builder()
                .addInterceptor { chain ->
                    val request = chain.request().newBuilder()
                        .header("Authorization", "Bearer ${settings.apiKey}")
                        .header("Content-Type", "application/json")
                        .build()
                    chain.proceed(request)
                }
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(300, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .build()

            val client = OkHttpClient.Builder()
                .addInterceptor(logging)
                .addInterceptor { chain ->
                    val request = chain.request().newBuilder()
                        .header("Authorization", "Bearer ${settings.apiKey}")
                        .header("Content-Type", "application/json")
                        .build()
                    chain.proceed(request)
                }
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(120, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .build()

            val retrofit = Retrofit.Builder()
                .baseUrl(settings.baseUrl)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            val streamingRetrofit = Retrofit.Builder()
                .baseUrl(settings.baseUrl)
                .client(streamingClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            val apiService = retrofit.create(DeepSeekApiService::class.java)
            val streamingApiService = streamingRetrofit.create(DeepSeekApiService::class.java)

            aiService = AIService(
                apiService = apiService,
                streamingApiService = streamingApiService,
                apiKey = settings.apiKey,
                model = settings.model,
                dialogueTemperature = settings.dialogueTemperature,
                dialogueMaxTokens = settings.dialogueMaxTokens,
                summaryTemperature = settings.summaryTemperature,
                summaryMaxTokens = settings.summaryMaxTokens,
                thinkingEnabled = settings.thinkingEnabled,
                reasoningEffort = settings.reasoningEffort
            )
        }
        return aiService!!
    }

    fun getCreateGameUseCase(): CreateGameUseCase {
        if (createGameUseCase == null) {
            createGameUseCase = CreateGameUseCase(getGameRepository())
        }
        return createGameUseCase!!
    }

    fun getUpdateStateUseCase(): UpdateStateUseCase {
        if (updateStateUseCase == null) {
            updateStateUseCase = UpdateStateUseCase(getGameRepository())
        }
        return updateStateUseCase!!
    }

    fun getGenerateSummaryUseCase(): GenerateSummaryUseCase {
        if (generateSummaryUseCase == null) {
            generateSummaryUseCase = GenerateSummaryUseCase(
                getGameRepository(),
                getAIService()
            )
        }
        return generateSummaryUseCase!!
    }

    fun getSyncSettingsUseCase(): SyncSettingsUseCase {
        if (syncSettingsUseCase == null) {
            syncSettingsUseCase = SyncSettingsUseCase(getGameRepository())
        }
        return syncSettingsUseCase!!
    }

    fun getSendDialogueUseCase(): SendDialogueUseCase {
        if (sendDialogueUseCase == null) {
            sendDialogueUseCase = SendDialogueUseCase(
                getGameRepository(),
                getAIService(),
                getUpdateStateUseCase(),
                getGenerateSummaryUseCase(),
                getSyncSettingsUseCase()
            )
        }
        return sendDialogueUseCase!!
    }

    fun getGetAllSessionsUseCase(): GetAllSessionsUseCase {
        if (getAllSessionsUseCase == null) {
            getAllSessionsUseCase = GetAllSessionsUseCase(getGameRepository())
        }
        return getAllSessionsUseCase!!
    }

    fun getDeleteSessionUseCase(): DeleteSessionUseCase {
        if (deleteSessionUseCase == null) {
            deleteSessionUseCase = DeleteSessionUseCase(getGameRepository())
        }
        return deleteSessionUseCase!!
    }
}
