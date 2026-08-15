package com.textgame.data.audio

import com.textgame.R
import com.textgame.i18n.Lang

enum class BgmTrack(
    val keywordEn: String,
    val keywordZh: String,
    val resId: Int
) {
    MAIN("Main Theme", "主界面", R.raw.bgm_main),
    BATTLE("Battle", "战斗", R.raw.bgm_battle),
    DANGER("Danger Approaching", "危险临近", R.raw.bgm_danger),
    VICTORY("Victory", "胜利", R.raw.bgm_victory),
    ROMANCE("Romance", "爱情", R.raw.bgm_romance),
    FEAR("Fear", "恐惧", R.raw.bgm_unknown_fear),
    SPRING_SUMMER("Spring/Summer", "春夏", R.raw.bgm_spring_summer),
    AUTUMN("Autumn", "秋天", R.raw.bgm_autumn),
    WINTER("Winter", "冬天", R.raw.bgm_winter);

    fun localizedKeyword(): String = if (Lang.isEnglish()) keywordEn else keywordZh

    companion object {
        fun fromKeyword(keyword: String?): BgmTrack? {
            if (keyword.isNullOrBlank()) return null
            val normalized = keyword.trim().lowercase()
            return values().firstOrNull {
                it.keywordEn.equals(normalized, ignoreCase = true) ||
                    it.keywordZh.equals(normalized, ignoreCase = true)
            }
        }

        /**
         * Returns the keyword list for the current locale so the AI outputs a value
         * that [fromKeyword] can resolve. MAIN is the menu theme and is not offered
         * to the AI for scene changes.
         */
        fun availableKeywords(english: Boolean = Lang.isEnglish()): List<String> {
            return values().filter { it != MAIN }.map {
                if (english) it.keywordEn else it.keywordZh
            }
        }
    }
}
