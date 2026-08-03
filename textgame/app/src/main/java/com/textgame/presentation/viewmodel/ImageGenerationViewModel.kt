package com.textgame.presentation.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.textgame.data.local.SettingsPreferences
import com.textgame.data.remote.ai.ImagePromptStyle
import com.textgame.data.seedream.SeedreamService
import com.textgame.di.AppModule
import com.textgame.domain.usecase.GenerateImagePromptUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

enum class ImageGenPhase {
    PROMPT_INPUT,
    IMAGE_GENERATION
}

data class ImageGenerationUiState(
    val phase: ImageGenPhase = ImageGenPhase.PROMPT_INPUT,
    val style: ImagePromptStyle = ImagePromptStyle.REALISTIC,
    val prompt: String = "",
    val isGeneratingPrompt: Boolean = false,
    val isGeneratingImage: Boolean = false,
    val isSaving: Boolean = false,
    val imageUrl: String? = null,
    val imageBase64: String? = null,
    val revisedPrompt: String? = null,
    val size: String = "2K",
    val availableSizes: List<String> = listOf("1K", "2K", "4K"),
    val savedMessage: String? = null,
    val error: String? = null
)

class ImageGenerationViewModel(
    private val sessionId: Long,
    private val sceneNarrative: String,
    private val context: Context
) : ViewModel() {

    private val _uiState = MutableStateFlow(ImageGenerationUiState())
    val uiState: StateFlow<ImageGenerationUiState> = _uiState.asStateFlow()

    init {
        // 根据已配置的生图模型初始化可用尺寸
        refreshAvailableSizes()
    }

    private fun refreshAvailableSizes() {
        val settings = AppModule.getCurrentSettings()
        val sizes = SettingsPreferences.getImageModelSizes(settings.imageModel)
        val defaultSize = if (sizes.contains("2K")) "2K" else sizes.firstOrNull() ?: "2K"
        _uiState.value = _uiState.value.copy(
            availableSizes = sizes,
            size = defaultSize
        )
    }

    fun updateStyle(style: ImagePromptStyle) {
        _uiState.value = _uiState.value.copy(style = style)
    }

    fun updatePrompt(text: String) {
        _uiState.value = _uiState.value.copy(prompt = text)
    }

    fun selectSize(size: String) {
        _uiState.value = _uiState.value.copy(size = size)
    }

    fun generatePrompt() {
        if (_uiState.value.isGeneratingPrompt) return
        _uiState.value = _uiState.value.copy(isGeneratingPrompt = true, error = null)
        viewModelScope.launch {
            try {
                val useCase = GenerateImagePromptUseCase(
                    AppModule.getGameRepository(),
                    AppModule.getAIService()
                )
                val prompt = useCase.execute(
                    sessionId = sessionId,
                    sceneNarrative = sceneNarrative,
                    style = _uiState.value.style
                )
                _uiState.value = _uiState.value.copy(
                    prompt = prompt,
                    isGeneratingPrompt = false,
                    error = null
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isGeneratingPrompt = false,
                    error = "提示词生成失败：${e.message}"
                )
            }
        }
    }

    /** 返回提示词生成界面（从生图界面退回修改提示词） */
    fun backToPromptInput() {
        _uiState.value = _uiState.value.copy(
            phase = ImageGenPhase.PROMPT_INPUT,
            imageUrl = null,
            imageBase64 = null,
            revisedPrompt = null,
            error = null
        )
    }

    /** 确认提示词，进入生图界面 */
    fun confirmPrompt() {
        if (_uiState.value.prompt.isBlank()) {
            _uiState.value = _uiState.value.copy(error = "提示词不能为空")
            return
        }
        // 切换模型时尺寸可能变化，刷新一次确保当前尺寸可用
        refreshAvailableSizes()
        _uiState.value = _uiState.value.copy(
            phase = ImageGenPhase.IMAGE_GENERATION,
            error = null
        )
    }

    fun generateImage() {
        if (_uiState.value.isGeneratingImage) return
        // 确保使用最新配置的生图服务
        AppModule.getSeedreamService()
        val service: SeedreamService = AppModule.getSeedreamService()
        if (!service.isConfigured()) {
            _uiState.value = _uiState.value.copy(
                error = "生图服务未配置，请在 AI 设置中填写生图 API Key 与域名"
            )
            return
        }
        _uiState.value = _uiState.value.copy(
            isGeneratingImage = true,
            error = null,
            imageUrl = null,
            imageBase64 = null,
            revisedPrompt = null,
            savedMessage = null
        )
        viewModelScope.launch {
            try {
                val result = service.generateImage(
                    prompt = _uiState.value.prompt,
                    size = _uiState.value.size
                )
                _uiState.value = _uiState.value.copy(
                    isGeneratingImage = false,
                    imageUrl = result.url,
                    imageBase64 = result.base64,
                    revisedPrompt = result.revisedPrompt
                )
                if (!result.hasUrl() && !result.hasBase64()) {
                    _uiState.value = _uiState.value.copy(
                        error = "生图响应中未包含图片数据"
                    )
                }
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isGeneratingImage = false,
                    error = e.message ?: "生图失败"
                )
            }
        }
    }

    fun saveImage() {
        if (_uiState.value.isSaving) return
        val state = _uiState.value
        if (!state.hasImage()) {
            _uiState.value = state.copy(error = "还没有可保存的图片")
            return
        }
        _uiState.value = state.copy(isSaving = true, error = null, savedMessage = null)
        viewModelScope.launch {
            try {
                val service = AppModule.getSeedreamService()
                val displayName = "textgame_${System.currentTimeMillis()}"
                val uri = when {
                    state.imageUrl != null -> service.saveImageToGallery(
                        context = context,
                        imageUrl = state.imageUrl,
                        displayName = displayName
                    )
                    state.imageBase64 != null -> service.saveBase64ToGallery(
                        context = context,
                        base64 = state.imageBase64,
                        displayName = displayName
                    )
                    else -> throw IllegalStateException("无可保存的图片数据")
                }
                _uiState.value = _uiState.value.copy(
                    isSaving = false,
                    savedMessage = "图片已保存到相册 Pictures/TextGame/$displayName.png"
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isSaving = false,
                    error = "保存失败：${e.message}"
                )
            }
        }
    }

    fun clearError() {
        _uiState.value = _uiState.value.copy(error = null)
    }

    fun clearSavedMessage() {
        _uiState.value = _uiState.value.copy(savedMessage = null)
    }

    private fun ImageGenerationUiState.hasImage(): Boolean =
        !imageUrl.isNullOrBlank() || !imageBase64.isNullOrBlank()
}

class ImageGenerationViewModelFactory(
    private val sessionId: Long,
    private val sceneNarrative: String,
    private val context: Context
) : androidx.lifecycle.ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : androidx.lifecycle.ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ImageGenerationViewModel::class.java)) {
            return ImageGenerationViewModel(sessionId, sceneNarrative, context) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
