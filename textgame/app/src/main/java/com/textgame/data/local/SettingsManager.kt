package com.textgame.data.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.floatPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

object SettingsManager {

    private val API_KEY = stringPreferencesKey("api_key")
    private val BASE_URL = stringPreferencesKey("base_url")
    private val MODEL = stringPreferencesKey("model")
    private val DIALOGUE_TEMPERATURE = floatPreferencesKey("dialogue_temperature")
    private val DIALOGUE_MAX_TOKENS = intPreferencesKey("dialogue_max_tokens")
    private val SUMMARY_TEMPERATURE = floatPreferencesKey("summary_temperature")
    private val SUMMARY_MAX_TOKENS = intPreferencesKey("summary_max_tokens")

    val DEFAULTS = SettingsPreferences()

    fun getSettingsFlow(context: Context): Flow<SettingsPreferences> {
        return context.dataStore.data.map { prefs ->
            SettingsPreferences(
                apiKey = prefs[API_KEY] ?: DEFAULTS.apiKey,
                baseUrl = prefs[BASE_URL] ?: DEFAULTS.baseUrl,
                model = prefs[MODEL] ?: DEFAULTS.model,
                dialogueTemperature = prefs[DIALOGUE_TEMPERATURE] ?: DEFAULTS.dialogueTemperature,
                dialogueMaxTokens = prefs[DIALOGUE_MAX_TOKENS] ?: DEFAULTS.dialogueMaxTokens,
                summaryTemperature = prefs[SUMMARY_TEMPERATURE] ?: DEFAULTS.summaryTemperature,
                summaryMaxTokens = prefs[SUMMARY_MAX_TOKENS] ?: DEFAULTS.summaryMaxTokens
            )
        }
    }

    suspend fun saveSettings(context: Context, settings: SettingsPreferences) {
        context.dataStore.edit { prefs ->
            prefs[API_KEY] = settings.apiKey
            prefs[BASE_URL] = settings.baseUrl
            prefs[MODEL] = settings.model
            prefs[DIALOGUE_TEMPERATURE] = settings.dialogueTemperature
            prefs[DIALOGUE_MAX_TOKENS] = settings.dialogueMaxTokens
            prefs[SUMMARY_TEMPERATURE] = settings.summaryTemperature
            prefs[SUMMARY_MAX_TOKENS] = settings.summaryMaxTokens
        }
    }
}
