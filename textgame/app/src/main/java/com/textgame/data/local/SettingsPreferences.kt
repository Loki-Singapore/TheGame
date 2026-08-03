package com.textgame.data.local

data class SettingsPreferences(
    val apiKey: String = "",
    val baseUrl: String = "https://api.deepseek.com/",
    val model: String = "deepseek-v4-flash",
    val dialogueTemperature: Float = 1.0f,
    val dialogueMaxTokens: Int = 125000,
    val summaryTemperature: Float = 0.8f,
    val summaryMaxTokens: Int = 125000,
    val musicEnabled: Boolean = true,
    val thinkingEnabled: Boolean = false,
    val reasoningEffort: String = "high",
    // 生图设置：使用 BytePlus Seedream 系列模型
    val imageApiKey: String = "",
    val imageBaseUrl: String = "ark.ap-southeast.bytepluses.com",
    val imageModel: String = IMAGE_PRESET_MODELS.first()
) {
    companion object {
        val PRESET_MODELS = listOf(
            "deepseek-v4-flash",
            "deepseek-v4-pro",
            "deepseek-chat",
            "deepseek-reasoner"
        )

        // Seedream 系列模型（BytePlus 火山引擎），名称参考 SeedHub 项目
        val IMAGE_PRESET_MODELS = listOf(
            "doubao-seedream-4-0-250828",
            "seedream-4-0-250828",
            "seedream-4-5-251128",
            "doubao-seedream-4-5-251128",
            "seedream-5-0-260128",
            "dola-seedream-5-0-pro-260628"
        )

        // 各 Seedream 模型支持的尺寸（SeedHub 中 1K=1024x1024 等）
        val IMAGE_MODEL_SIZES = mapOf(
            "dola-seedream-5-0-pro-260628" to listOf("1K", "2K"),
            "seedream-5-0-260128" to listOf("2K", "3K", "4K"),
            "seedream-4-5-251128" to listOf("2K", "4K"),
            "doubao-seedream-4-5-251128" to listOf("2K", "4K"),
            "doubao-seedream-4-0-250828" to listOf("1K", "2K", "4K"),
            "seedream-4-0-250828" to listOf("1K", "2K", "4K")
        )

        // 尺寸对应的像素值（用于展示）
        val IMAGE_SIZE_LABELS = mapOf(
            "1K" to "1K (1024x1024)",
            "2K" to "2K (2048x2048)",
            "3K" to "3K (3072x3072)",
            "4K" to "4K (4096x4096)"
        )

        fun getImageModelSizes(model: String): List<String> {
            return IMAGE_MODEL_SIZES[model] ?: listOf("1K", "2K", "4K")
        }

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

        // 放在 companion object 末尾，确保 IMAGE_PRESET_MODELS 等常量先于默认值初始化
        val DEFAULTS = SettingsPreferences()
    }
}
