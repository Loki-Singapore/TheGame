package com.textgame.domain.model

import kotlin.random.Random

/**
 * 导演层：每轮掷一次戏剧指令，作为隐藏 system 消息注入给 AI。
 * 玩家永远看不到指令本身，只能看到 AI 把指令编织进叙事后的结果。
 *
 * ponytail: 全局 Random，单进程游戏不需要线程安全。若多 session 并发改用 ThreadLocalRandom。
 */
object DirectorDirective {

    /**
     * 模板表。{npc} 占位符会被随机替换为在场某位 NPC 的名字。
     * 表内条目刻意覆盖：背叛/伏笔/时间压力/资源短缺/信息不对称/玩家不涉足处的事件/NPC主动行动。
     */
    private val templates: List<String> = listOf(
        "本轮让 {npc} 做出一个玩家未要求、但符合其性格的举动，让玩家感到这个人有自己的生活。",
        "本轮让 {npc} 的隐藏动机开始悄悄影响其言行（不要直接揭穿动机本身）。",
        "本轮安排 {npc} 对主角撒一个谎，或隐瞒一条关键信息。玩家若有怀疑可在后续追问。",
        "本轮让 {npc} 主动推进其隐藏动机的一步，主角这次只是旁观者或被波及者，不是发起方。",
        "本轮引入一个时间压力或倒计时（如某人即将抵达、某人即将死去、某事即将发生），不要立刻兑现。",
        "本轮让某种资源/物品/关系出现短缺或代价，让主角感到世界有阻力。",
        "本轮制造一个两难：主角若选 A 则牺牲 B，没有完美选项。",
        "本轮让 {npc} 的过去突然找上门来（旧识、仇家、债主、亲人任选），打破当前节奏。",
        "本轮在玩家未涉足的地方发生一件大事，事后通过 {npc} 的反应或环境变化体现出来，玩家需要拼凑。",
        "本轮安排一个伏笔：埋下一个看似无关的细节，3-5 轮后才能显出意义。本轮绝不解释它。",
        "本轮让 {npc} 做出一个让主角意想不到但事后想来合理的决定，制造'原来如此'的瞬间。",
        "本轮让 {npc} 之间发生一次玩家不在场的私下交流，玩家只能看到结果（情绪变化、立场转变等），不知过程。",
        "本轮让世界对主角的行为产生一次反作用力：之前做过的事带来预料外的后果，不只是好或坏。",
        "本轮把一个原本可靠的东西变得不可靠（盟友动摇、道具失灵、规则有例外、安全地点不再安全）。"
    )

    /**
     * 掷一次指令。约 1/3 概率返回 null（让 AI 自然推进，避免每轮都强制反转导致疲劳）。
     *
     * @param turnCount 当前轮次，目前未使用，留作未来按节奏调节（如前 2 轮不掷指令）。
     * @param npcs 在场 NPC 列表，用于填充 {npc} 占位符。
     */
    fun roll(turnCount: Int, npcs: List<NPC>): String? {
        if (npcs.isEmpty()) return null
        // ponytail: 1/3 null，2/3 取一条指令。简单均匀分布够用。
        if (Random.nextInt(3) == 0) return null
        val template = templates[Random.nextInt(templates.size)]
        val pickNpc = npcs[Random.nextInt(npcs.size)].name.ifBlank { "在场某位NPC" }
        return template.replace("{npc}", pickNpc)
    }
}
