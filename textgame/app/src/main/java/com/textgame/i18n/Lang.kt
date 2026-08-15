package com.textgame.i18n

import java.util.Locale

/**
 * Lightweight language switch for non-UI layers (AI prompts, data fallbacks, logs).
 *
 * UI text is resolved through Android resources (values/strings.xml is English,
 * values-zh/strings.xml is Chinese). This helper mirrors the same rule: any locale
 * other than Chinese is treated as English.
 */
object Lang {
    private val language: String = Locale.getDefault().language.lowercase(Locale.ROOT)

    fun isEnglish(): Boolean = language != "zh"

    fun isChinese(): Boolean = !isEnglish()

    fun text(english: String, chinese: String): String = if (isEnglish()) english else chinese
}
