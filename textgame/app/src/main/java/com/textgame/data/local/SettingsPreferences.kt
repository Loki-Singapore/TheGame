package com.textgame.data.local

data class SettingsPreferences(
    val apiKey: String = "",
    val baseUrl: String = "https://api.deepseek.com/",
    val model: String = "deepseek-v4-flash",
    val dialogueTemperature: Float = 1.0f,
    val dialogueMaxTokens: Int = 125000,
    val summaryTemperature: Float = 0.8f,
    val summaryMaxTokens: Int = 125000
) {
    companion object {
        val DEFAULTS = SettingsPreferences()

        val PRESET_MODELS = listOf(
            "deepseek-v4-flash",
            "deepseek-v4-pro",
            "deepseek-chat",
            "deepseek-reasoner"
        )

        // 各模型最大输出限制（API硬性上限）
        val MODEL_MAX_OUTPUT = mapOf(
            "deepseek-v4-flash" to 384000,
            "deepseek-v4-pro" to 384000,
            "deepseek-chat" to 8000,
            "deepseek-reasoner" to 60000
        )

        // 各模型默认对话maxTokens
        val MODEL_DEFAULT_DIALOGUE_MAX_TOKENS = mapOf(
            "deepseek-v4-flash" to 125000,
            "deepseek-v4-pro" to 125000,
            "deepseek-chat" to 8000,
            "deepseek-reasoner" to 60000
        )

        // 各模型默认总结maxTokens
        val MODEL_DEFAULT_SUMMARY_MAX_TOKENS = mapOf(
            "deepseek-v4-flash" to 125000,
            "deepseek-v4-pro" to 125000,
            "deepseek-chat" to 8000,
            "deepseek-reasoner" to 60000
        )

        fun getDefaultDialogueMaxTokens(model: String): Int {
            return MODEL_DEFAULT_DIALOGUE_MAX_TOKENS[model] ?: 125000
        }

        fun getDefaultSummaryMaxTokens(model: String): Int {
            return MODEL_DEFAULT_SUMMARY_MAX_TOKENS[model] ?: 125000
        }
    }
}
