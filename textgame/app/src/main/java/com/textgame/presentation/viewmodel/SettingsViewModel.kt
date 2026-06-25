package com.textgame.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.textgame.data.local.SettingsManager
import com.textgame.data.local.SettingsPreferences
import com.textgame.di.AppModule
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class SettingsViewModel(application: Application) : AndroidViewModel(application) {

    private val _uiState = MutableStateFlow(SettingsUiState())
    val uiState: StateFlow<SettingsUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            val settings = SettingsManager.getSettingsFlow(getApplication()).first()
            _uiState.value = SettingsUiState(
                apiKey = settings.apiKey,
                baseUrl = settings.baseUrl,
                model = settings.model,
                dialogueTemperature = settings.dialogueTemperature,
                dialogueMaxTokens = settings.dialogueMaxTokens,
                summaryTemperature = settings.summaryTemperature,
                summaryMaxTokens = settings.summaryMaxTokens,
                dialogueMaxTokensLimit = SettingsPreferences.MODEL_MAX_OUTPUT[settings.model] ?: 384000,
                summaryMaxTokensLimit = SettingsPreferences.MODEL_MAX_OUTPUT[settings.model] ?: 384000
            )
        }
    }

    fun updateApiKey(value: String) {
        _uiState.value = _uiState.value.copy(apiKey = value)
    }

    fun updateBaseUrl(value: String) {
        _uiState.value = _uiState.value.copy(baseUrl = value)
    }

    fun updateModel(value: String) {
        val defaultDialogueMax = SettingsPreferences.getDefaultDialogueMaxTokens(value)
        val defaultSummaryMax = SettingsPreferences.getDefaultSummaryMaxTokens(value)
        val maxOutput = SettingsPreferences.MODEL_MAX_OUTPUT[value] ?: 384000
        _uiState.value = _uiState.value.copy(
            model = value,
            dialogueMaxTokens = defaultDialogueMax,
            summaryMaxTokens = defaultSummaryMax,
            dialogueMaxTokensLimit = maxOutput,
            summaryMaxTokensLimit = maxOutput
        )
    }

    fun updateDialogueTemperature(value: Float) {
        _uiState.value = _uiState.value.copy(dialogueTemperature = value)
    }

    fun updateDialogueMaxTokens(value: Int) {
        val limit = _uiState.value.dialogueMaxTokensLimit
        _uiState.value = _uiState.value.copy(dialogueMaxTokens = value.coerceAtMost(limit))
    }

    fun updateSummaryTemperature(value: Float) {
        _uiState.value = _uiState.value.copy(summaryTemperature = value)
    }

    fun updateSummaryMaxTokens(value: Int) {
        val limit = _uiState.value.summaryMaxTokensLimit
        _uiState.value = _uiState.value.copy(summaryMaxTokens = value.coerceAtMost(limit))
    }

    fun saveSettings() {
        val state = _uiState.value
        val settings = SettingsPreferences(
            apiKey = state.apiKey,
            baseUrl = state.baseUrl,
            model = state.model,
            dialogueTemperature = state.dialogueTemperature,
            dialogueMaxTokens = state.dialogueMaxTokens,
            summaryTemperature = state.summaryTemperature,
            summaryMaxTokens = state.summaryMaxTokens
        )
        viewModelScope.launch {
            SettingsManager.saveSettings(getApplication(), settings)
            AppModule.configureAI(settings)
            _uiState.value = state.copy(saved = true)
        }
    }

    fun resetToDefaults() {
        val defaults = SettingsPreferences.DEFAULTS
        val maxOutput = SettingsPreferences.MODEL_MAX_OUTPUT[defaults.model] ?: 384000
        _uiState.value = SettingsUiState(
            apiKey = defaults.apiKey,
            baseUrl = defaults.baseUrl,
            model = defaults.model,
            dialogueTemperature = defaults.dialogueTemperature,
            dialogueMaxTokens = defaults.dialogueMaxTokens,
            summaryTemperature = defaults.summaryTemperature,
            summaryMaxTokens = defaults.summaryMaxTokens,
            dialogueMaxTokensLimit = maxOutput,
            summaryMaxTokensLimit = maxOutput
        )
    }
}

data class SettingsUiState(
    val apiKey: String = "",
    val baseUrl: String = "https://api.deepseek.com/",
    val model: String = "deepseek-chat",
    val dialogueTemperature: Float = 1.0f,
    val dialogueMaxTokens: Int = 125000,
    val summaryTemperature: Float = 0.8f,
    val summaryMaxTokens: Int = 125000,
    val dialogueMaxTokensLimit: Int = 384000,
    val summaryMaxTokensLimit: Int = 384000,
    val saved: Boolean = false
)
