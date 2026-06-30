package com.textgame.data.audio

import com.textgame.R

enum class BgmTrack(val keyword: String, val resId: Int) {
    MAIN("主界面", R.raw.bgm_main),
    BATTLE("战斗", R.raw.bgm_battle),
    DANGER("危险临近", R.raw.bgm_danger),
    VICTORY("胜利", R.raw.bgm_victory),
    ROMANCE("爱情", R.raw.bgm_romance),
    UNKNOWN_FEAR("未知的恐惧", R.raw.bgm_unknown_fear),
    SPRING_SUMMER("春夏", R.raw.bgm_spring_summer),
    AUTUMN("秋天", R.raw.bgm_autumn),
    WINTER("冬天", R.raw.bgm_winter);

    companion object {
        fun fromKeyword(keyword: String?): BgmTrack? {
            if (keyword.isNullOrBlank()) return null
            return values().find { it.keyword == keyword }
        }

        fun availableKeywords(): List<String> {
            return values().filter { it != MAIN }.map { it.keyword }
        }
    }
}
