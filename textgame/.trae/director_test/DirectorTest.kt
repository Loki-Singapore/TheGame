package com.textgame.dirtest

import com.textgame.domain.model.NPC
import com.textgame.domain.model.DirectorContext
import com.textgame.domain.model.DirectorDirective

fun main() {
    // 1. 空 NPC 列表 → null
    check(DirectorDirective.roll(1, emptyList()) == null) { "empty list should be null" }

    // 2. NPC 名字为空 → 不应残留 {npc} 占位符
    val anonNpc = NPC(name = "", role = "路人")
    var got: String? = null
    var tries = 0
    while (got == null && tries < 50) { got = DirectorDirective.roll(1, listOf(anonNpc)); tries++ }
    check(got != null) { "50 tries all null — random distribution looks broken" }
    check(!got!!.contains("{npc}")) { "unreplaced placeholder with anon npc: $got" }

    // 3. NPC 有名字 → 不应残留 {npc} 占位符
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

    // 5. null 概率：opening (turn=1) 单 NPC，跑 300 次，null 比例应在 [20%, 50%] 之间
    var nullCount = 0
    repeat(300) {
        if (DirectorDirective.roll(1, listOf(namedNpc)) == null) nullCount++
    }
    val ratio = nullCount.toDouble() / 300.0
    check(ratio in 0.20..0.50) { "null ratio out of expected range: $ratio ($nullCount/300)" }

    // 6. 故事阶段感知：CLIMAX (turn=25) 的 null 概率应明显低于 OPENING (turn=1)
    //    CLIMAX null=0.25, OPENING null=0.33，跑够样本量应能稳定观察到差异
    var openingNull = 0
    var climaxNull = 0
    repeat(400) {
        if (DirectorDirective.roll(1, listOf(namedNpc)) == null) openingNull++
        if (DirectorDirective.roll(25, listOf(namedNpc)) == null) climaxNull++
    }
    val openingRatio = openingNull / 400.0
    val climaxRatio = climaxNull / 400.0
    check(climaxRatio < openingRatio) {
        "CLIMAX null ratio ($climaxRatio) should be < OPENING ($openingRatio)"
    }
    println("phase null ratio: OPENING=${"%.2f".format(openingRatio)} CLIMAX=${"%.2f".format(climaxRatio)}")

    // 7. 适用性过滤：NPC 没有隐藏动机时，"隐藏动机"类指令绝不应出现
    //    turn=10 (DEVELOPING) 开放了 AGENDA 模板，但 NPC 无 agenda → 应被过滤
    val noAgendaNpc = NPC(name = "无名", role = "路人", personality = "寡言", backstory = "不知来历")
    var agendaLeak = false
    repeat(300) {
        val d = DirectorDirective.roll(
            DirectorContext(turnCount = 10, npcs = listOf(noAgendaNpc))
        )
        if (d != null && d.contains("隐藏动机")) agendaLeak = true
    }
    check(!agendaLeak) { "AGENDA 指令在无 agenda 的 NPC 上泄露了" }

    // 8. 正向用例：NPC 有隐藏动机时，"隐藏动机"指令应在足够多次掷骰中出现至少一次
    val agendaNpc = NPC(
        name = "影",
        role = "刺客",
        personality = "冷峻",
        backstory = "亡国之臣",
        hiddenAgenda = "伺机刺杀主角以复国"
    )
    var agendaHit = 0
    repeat(400) {
        val d = DirectorDirective.roll(
            DirectorContext(turnCount = 10, npcs = listOf(agendaNpc))
        )
        if (d != null && d.contains("隐藏动机")) agendaHit++
    }
    check(agendaHit > 0) { "有 agenda 的 NPC 上 400 次掷骰竟无一次隐藏动机指令" }
    println("agenda directive hits: $agendaHit/400")

    // 9. 反重复：连续 100 次非 null 输出中，不应有任何一条模板连续出现
    //    （recentIndices 把最近 3 条权重降到 1/4，连续同模板极不可能）
    val repeatCtx = DirectorContext(turnCount = 10, npcs = listOf(agendaNpc))
    var prev: String? = null
    var consecutiveDup = false
    repeat(200) {
        val d = DirectorDirective.roll(repeatCtx)
        if (d != null && d == prev) consecutiveDup = true
        if (d != null) prev = d
    }
    check(!consecutiveDup) { "检测到同一指令连续两轮出现，反重复逻辑失效" }

    // 10. 伏笔闭环：连续推进轮次，应能看到兑现指令至少出现一次
    //     埋伏笔只在 OPENING/DEVELOPING，兑现需 pending>0 且距上次埋设≥3轮
    var payoffHit = false
    for (turn in 1..60) {
        val d = DirectorDirective.roll(
            DirectorContext(turnCount = turn, npcs = listOf(agendaNpc))
        )
        if (d != null && d.contains("之前埋下的某个伏笔")) payoffHit = true
    }
    check(payoffHit) { "60 轮推进下从未掷出兑现伏笔指令" }
    println("foreshadow payoff observed in 60-turn sweep")

    println("ALL CHECKS PASSED. opening null ratio = ${"%.2f".format(ratio)} ($nullCount/300)")
}
