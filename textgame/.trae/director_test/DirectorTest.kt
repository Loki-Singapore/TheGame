package com.textgame.dirtest

import com.textgame.domain.model.NPC
import com.textgame.domain.model.DirectorDirective

fun main() {
    // 1. 空 NPC 列表 → null
    check(DirectorDirective.roll(1, emptyList()) == null) { "empty list should be null" }

    // 2. NPC 名字为空 → 含 {npc} 的模板应替换为"在场某位NPC"，无 {npc} 的模板保持原样
    val anonNpc = NPC(name = "", role = "路人")
    var got: String? = null
    var tries = 0
    while (got == null && tries < 50) { got = DirectorDirective.roll(1, listOf(anonNpc)); tries++ }
    check(got != null) { "50 tries all null — random distribution looks broken" }
    check(!got!!.contains("{npc}")) { "unreplaced placeholder with anon npc: $got" }
    // 含 {npc} 的模板替换后必含"在场某位NPC"；无 {npc} 的模板自然不含。两者皆合法。

    // 3. NPC 有名字 → 含 {npc} 的模板替换后必含"李雷"，无 {npc} 的模板自然不含
    val namedNpc = NPC(name = "李雷", role = "剑客")
    var got2: String? = null
    var tries2 = 0
    while (got2 == null && tries2 < 50) { got2 = DirectorDirective.roll(1, listOf(namedNpc)); tries2++ }
    check(got2 != null) { "named npc 50 tries all null" }
    check(!got2!!.contains("{npc}")) { "unreplaced placeholder with named npc: $got2" }

    // 4. 多个 NPC 时，所有指令都不应残留 {npc} 占位符
    val npcs = listOf(
        NPC(name = "甲", role = ""),
        NPC(name = "乙", role = ""),
        NPC(name = "丙", role = "")
    )
    repeat(50) {
        val d = DirectorDirective.roll(1, npcs)
        if (d != null) {
            check(!d.contains("{npc}")) { "unreplaced placeholder: $d" }
        }
    }

    // 5. null 概率约 1/3：跑 300 次，null 比例应在 [20%, 50%] 之间
    var nullCount = 0
    repeat(300) {
        if (DirectorDirective.roll(1, listOf(namedNpc)) == null) nullCount++
    }
    val ratio = nullCount.toDouble() / 300.0
    check(ratio in 0.20..0.50) { "null ratio out of expected range: $ratio ($nullCount/300)" }

    println("ALL CHECKS PASSED. null ratio = ${"%.2f".format(ratio)} ($nullCount/300)")
}
