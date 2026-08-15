package com.textgame.domain.model

import com.textgame.i18n.Lang
import kotlin.random.Random

/**
 * 导演层上下文：把 SendDialogueUseCase 已经加载好的状态打包给导演，
 * 让指令掷骰时能看见世界——谁的隐藏动机可以推动、当前处于故事哪个阶段、
 * 之前埋下的伏笔到了该兑现的时候没有。不引入新的 DB 查询。
 */
data class DirectorContext(
    val turnCount: Int,
    val npcs: List<NPC>,
    val protagonist: Protagonist? = null,
    val gameState: GameState? = null,
    val summary: Summary? = null,
    val majorPlotThreads: List<String> = emptyList()
)

/**
 * 导演层：每轮掷一次戏剧指令，作为隐藏 system 消息注入给 AI。
 * 玩家永远看不到指令本身，只能看到 AI 把指令编织进叙事后的结果。
 *
 * 改进点（相比纯均匀随机）：
 * 1. 故事阶段感知：opening/developing/climax/resolution，决定 null 概率和高张力模板的开放。
 * 2. 适用性过滤：需要隐藏动机的指令只挑真有动机的 NPC；需要背景/性格的同理。
 *    场上没人有动机时，"让动机浮出水面"这类指令直接不掷，避免无意义指令。
 * 3. 反重复：最近 3 条指令的模板权重降到 1/4，避免连续两轮掷同一条。
 * 4. 伏笔闭环：埋伏笔的指令会增加 pending 计数，3 轮后开放"兑现伏笔"指令，
 *    兑现后计数减一。原来的版本只埋不兑现。
 *
 * ponytail: 全局可变状态（recentIndices / pendingForeshadowings / lastPlantTurn）
 * 假设单进程单 session。若多 session 并发改成 Map<sessionId, DirectorMem>。
 * ArrayDeque.indexOf / `in` 是 O(k)，k=3，忽略。
 */
object DirectorDirective {

    private enum class Phase { OPENING, DEVELOPING, CLIMAX, RESOLUTION }
    private enum class NpcReq { ANY, AGENDA, BACKSTORY, PERSONALITY }
    private enum class Tag { NORMAL, PLANT_FORESHADOW, PAYOFF_FORESHADOW }

    private data class Template(
        val text: String,
        val phases: Set<Phase>,
        val npcReq: NpcReq = NpcReq.ANY,
        val minNpcs: Int = 1,
        val tag: Tag = Tag.NORMAL
    )

    /**
     * 模板表。覆盖：背叛/伏笔(埋+兑现)/时间压力/资源短缺/信息不对称/
     * 玩家不涉足处的事件/NPC主动行动/感官预兆/微破绽/情感委托。
     * {npc} 占位符会被按 npcReq 挑选的 NPC 名字替换。
     */
    private val templates: List<Template> = listOf(
        Template(
            "本轮让 {npc} 做出一个玩家未要求、但符合其性格的举动，让玩家感到这个人有自己的生活。",
            phases = setOf(Phase.OPENING, Phase.DEVELOPING, Phase.CLIMAX, Phase.RESOLUTION),
            npcReq = NpcReq.PERSONALITY
        ),
        Template(
            "本轮让 {npc} 的隐藏动机开始悄悄影响其言行（不要直接揭穿动机本身）。",
            phases = setOf(Phase.DEVELOPING, Phase.CLIMAX),
            npcReq = NpcReq.AGENDA
        ),
        Template(
            "本轮安排 {npc} 对主角撒一个谎，或隐瞒一条关键信息。玩家若有怀疑可在后续追问。",
            phases = setOf(Phase.DEVELOPING, Phase.CLIMAX, Phase.RESOLUTION),
            npcReq = NpcReq.PERSONALITY
        ),
        Template(
            "本轮让 {npc} 主动推进其隐藏动机的一步，主角这次只是旁观者或被波及者，不是发起方。",
            phases = setOf(Phase.DEVELOPING, Phase.CLIMAX),
            npcReq = NpcReq.AGENDA
        ),
        Template(
            "本轮引入一个时间压力或倒计时（如某人即将抵达、某人即将死去、某事即将发生），不要立刻兑现。",
            phases = setOf(Phase.DEVELOPING, Phase.CLIMAX)
        ),
        Template(
            "本轮让某种资源/物品/关系出现短缺或代价，让主角感到世界有阻力。",
            phases = setOf(Phase.DEVELOPING, Phase.CLIMAX)
        ),
        Template(
            "本轮制造一个两难：主角若选 A 则牺牲 B，没有完美选项。",
            phases = setOf(Phase.CLIMAX)
        ),
        Template(
            "本轮让 {npc} 的过去突然找上门来（旧识、仇家、债主、亲人任选），打破当前节奏。",
            phases = setOf(Phase.DEVELOPING, Phase.CLIMAX, Phase.RESOLUTION),
            npcReq = NpcReq.BACKSTORY
        ),
        Template(
            "本轮在玩家未涉足的地方发生一件大事，事后通过 {npc} 的反应或环境变化体现出来，玩家需要拼凑。",
            phases = setOf(Phase.OPENING, Phase.DEVELOPING, Phase.CLIMAX, Phase.RESOLUTION)
        ),
        Template(
            "本轮安排一个伏笔：埋下一个看似无关的细节，3-5 轮后才能显出意义。本轮绝不解释它。",
            phases = setOf(Phase.OPENING, Phase.DEVELOPING),
            tag = Tag.PLANT_FORESHADOW
        ),
        Template(
            "本轮让 {npc} 做出一个让主角意想不到但事后想来合理的决定，制造'原来如此'的瞬间。",
            phases = setOf(Phase.DEVELOPING, Phase.CLIMAX, Phase.RESOLUTION),
            npcReq = NpcReq.PERSONALITY
        ),
        Template(
            "本轮让 {npc} 之间发生一次玩家不在场的私下交流，玩家只能看到结果（情绪变化、立场转变等），不知过程。",
            phases = setOf(Phase.DEVELOPING, Phase.CLIMAX),
            minNpcs = 2
        ),
        Template(
            "本轮让世界对主角的行为产生一次反作用力：之前做过的事带来预料外的后果，不只是好或坏。",
            phases = setOf(Phase.DEVELOPING, Phase.CLIMAX, Phase.RESOLUTION)
        ),
        Template(
            "本轮把一个原本可靠的东西变得不可靠（盟友动摇、道具失灵、规则有例外、安全地点不再安全）。",
            phases = setOf(Phase.CLIMAX, Phase.RESOLUTION)
        ),
        Template(
            "本轮让你之前埋下的某个伏笔开始显出意义——不要硬解释，让玩家从前后细节中自己拼出来，制造'原来早就暗示过'的恍然。",
            phases = setOf(Phase.DEVELOPING, Phase.CLIMAX, Phase.RESOLUTION),
            tag = Tag.PAYOFF_FORESHADOW
        ),
        Template(
            "本轮让 {npc} 主动向主角提出一个带着个人情感的请求或委托（不是任务面板式的），让主角难以拒绝也不好轻易接受。",
            phases = setOf(Phase.DEVELOPING, Phase.CLIMAX),
            npcReq = NpcReq.PERSONALITY
        ),
        Template(
            "本轮让一个看似无关的感官细节（某种气味、声音、光影、温度）反复出现，营造潜意识层面的不安或预兆。本轮绝不解释它的含义。",
            phases = setOf(Phase.OPENING, Phase.DEVELOPING, Phase.CLIMAX),
            tag = Tag.PLANT_FORESHADOW
        ),
        Template(
            "本轮让 {npc} 说错一句话或露出一个一闪而过的破绽，主角若留意可追问出隐藏信息，若忽略也无伤大雅。把选择权交给玩家。",
            phases = setOf(Phase.DEVELOPING, Phase.CLIMAX, Phase.RESOLUTION),
            npcReq = NpcReq.PERSONALITY
        )
    )

    private const val RECENT_SIZE = 3
    private const val MAX_PENDING = 3
    private const val PAYOFF_DELAY = 3

    /**
     * English translations of [templates]. Order must match exactly: indices are shared
     * by [recentIndices] and the weighted picker.
     */
    private val templatesEn: List<String> = listOf(
        "This turn, have {npc} do something the player did not ask for but that fits the character, so the player feels this person has a life of their own.",
        "This turn, let {npc}\'s hidden agenda begin to quietly influence their words and actions (without revealing the agenda itself).",
        "This turn, have {npc} tell the protagonist a lie or conceal a key piece of information. The player may notice and ask about it later.",
        "This turn, let {npc} take one step toward their hidden agenda on their own initiative. The protagonist is only a bystander or is affected, not the initiator.",
        "This turn, introduce time pressure or a countdown (someone is about to arrive, someone is about to die, something is about to happen) without resolving it yet.",
        "This turn, make some resource, item, or relationship scarce or costly so the protagonist feels the world pushing back.",
        "This turn, create a dilemma: if the protagonist chooses A, they sacrifice B. There is no perfect option.",
        "This turn, let something from {npc}\'s past suddenly catch up with them (an old acquaintance, enemy, creditor, or relative) and disrupt the current rhythm.",
        "This turn, let a major event happen somewhere the player cannot see; reveal it afterwards through {npc}\'s reaction or changes in the environment, leaving the player to piece it together.",
        "This turn, plant a foreshadowing: hide a seemingly irrelevant detail that will only become meaningful 3-5 turns later. Do not explain it this turn.",
        "This turn, have {npc} make a decision that surprises the protagonist but feels reasonable in hindsight, creating an \"of course\" moment.",
        "This turn, have two NPCs talk privately while the player is absent. The player only sees the result (mood shifts, changed stances) without knowing what happened.",
        "This turn, let the world push back against the protagonist\'s actions: something they did earlier brings an unexpected consequence, neither simply good nor bad.",
        "This turn, make something previously reliable become unreliable (an ally wavers, a tool malfunctions, a rule has an exception, a safe place is no longer safe).",
        "This turn, let a foreshadowing you planted earlier begin to show meaning — do not explain it; let the player piece it together from the details and feel that it was hinted at long ago.",
        "This turn, have {npc} proactively ask the protagonist for a favor or commission with personal emotional weight (not a quest-board style task), hard to refuse and not easy to accept.",
        "This turn, let a seemingly unrelated sensory detail (a smell, sound, light, or temperature) keep recurring, creating subconscious unease or an omen. Do not explain its meaning this turn.",
        "This turn, have {npc} misspeak or show a fleeting slip; an attentive player may ask and uncover hidden information, but ignoring it does no harm. Leave the choice to the player."
    )

    // ponytail: 全局可变状态，单 session 复用。多 session 改 Map<sessionId, Mem>。
    private val recentIndices = ArrayDeque<Int>()
    private var pendingForeshadowings = 0
    private var lastPlantTurn = -1

    /**
     * 掷一次指令。null 表示本轮不干预，让 AI 自然推进。
     * null 概率随故事阶段变化：开篇让场景呼吸，高潮保持张力，结尾放松。
     */
    fun roll(ctx: DirectorContext): String? {
        // ponytail: 空场时直接 null。原行为如此，保留。理论上"时间压力"在空场也能用，
        // 但空场本身少见，且保持契约简单。
        if (ctx.npcs.isEmpty()) return null

        val phase = phaseFor(ctx.turnCount)
        if (Random.nextDouble() < nullProbability(phase)) return null

        val applicableIndices = templates.indices.filter { applicable(templates[it], ctx, phase) }
        if (applicableIndices.isEmpty()) return null

        val weighted = applicableIndices.map { idx ->
            var w = 1.0
            if (idx in recentIndices) w *= 0.25
            if (templates[idx].tag == Tag.PAYOFF_FORESHADOW) w *= 2.0 // 偏向兑现，别让伏笔烂尾
            idx to w
        }
        // 硬禁止连续重复：只要还有别的选择，最近一条就不允许再出现。
        // 单模板场景下退化为允许重复（比永久 null 好）。
        val lastIdx = recentIndices.lastOrNull()
        val candidates = if (lastIdx != null) {
            val nonRecent = weighted.filter { it.first != lastIdx }
            if (nonRecent.isNotEmpty()) nonRecent else weighted
        } else {
            weighted
        }
        val pickedIdx = weightedPick(candidates)
        val picked = templates[pickedIdx]

        remember(pickedIdx, picked.tag, ctx.turnCount)

        return if ("{npc}" in picked.text) {
            val fallback = Lang.text("an NPC present", "在场某位NPC")
            val name = pickNpc(picked, ctx)?.name?.ifBlank { fallback } ?: fallback
            val text = if (Lang.isEnglish()) templatesEn[pickedIdx] else picked.text
            text.replace("{npc}", name)
        } else {
            if (Lang.isEnglish()) templatesEn[pickedIdx] else picked.text
        }
    }

    /** 旧签名委托到新接口，保留向后兼容。 */
    fun roll(turnCount: Int, npcs: List<NPC>): String? =
        roll(DirectorContext(turnCount = turnCount, npcs = npcs))

    // ponytail: 阶段阈值和 null 概率是经验值，单局文字游戏通常 30-60 轮。
    // 节奏不对就调这两个表，不要改模板。
    private fun phaseFor(turnCount: Int): Phase = when {
        turnCount <= 4 -> Phase.OPENING
        turnCount <= 20 -> Phase.DEVELOPING
        turnCount <= 40 -> Phase.CLIMAX
        else -> Phase.RESOLUTION
    }

    private fun nullProbability(phase: Phase): Double = when (phase) {
        Phase.OPENING -> 0.33
        Phase.DEVELOPING -> 0.33
        Phase.CLIMAX -> 0.25
        Phase.RESOLUTION -> 0.40
    }

    private fun applicable(t: Template, ctx: DirectorContext, phase: Phase): Boolean {
        if (phase !in t.phases) return false
        when (t.tag) {
            Tag.PAYOFF_FORESHADOW -> {
                if (pendingForeshadowings <= 0) return false
                if (ctx.turnCount - lastPlantTurn < PAYOFF_DELAY) return false
            }
            Tag.PLANT_FORESHADOW -> {
                if (pendingForeshadowings >= MAX_PENDING) return false
            }
            Tag.NORMAL -> {}
        }
        if (ctx.npcs.size < t.minNpcs) return false
        when (t.npcReq) {
            NpcReq.AGENDA -> if (ctx.npcs.none { it.hiddenAgenda.isNotEmpty() }) return false
            NpcReq.BACKSTORY -> if (ctx.npcs.none { it.backstory.isNotEmpty() }) return false
            NpcReq.PERSONALITY -> if (ctx.npcs.none { it.personality.isNotEmpty() }) return false
            NpcReq.ANY -> {}
        }
        return true
    }

    private fun pickNpc(t: Template, ctx: DirectorContext): NPC? {
        if (ctx.npcs.isEmpty()) return null
        val pool = when (t.npcReq) {
            NpcReq.AGENDA -> ctx.npcs.filter { it.hiddenAgenda.isNotEmpty() }.ifEmpty { ctx.npcs }
            NpcReq.BACKSTORY -> ctx.npcs.filter { it.backstory.isNotEmpty() }.ifEmpty { ctx.npcs }
            NpcReq.PERSONALITY -> ctx.npcs.filter { it.personality.isNotEmpty() }.ifEmpty { ctx.npcs }
            NpcReq.ANY -> ctx.npcs
        }
        return pool[Random.nextInt(pool.size)]
    }

    private fun remember(pickedIdx: Int, tag: Tag, turnCount: Int) {
        // 去重后再追加：避免同一模板因降权后仍被选中而在 buffer 里出现两次
        recentIndices.remove(pickedIdx)
        recentIndices.addLast(pickedIdx)
        while (recentIndices.size > RECENT_SIZE) recentIndices.removeFirst()
        when (tag) {
            Tag.PLANT_FORESHADOW -> {
                if (pendingForeshadowings < MAX_PENDING) pendingForeshadowings++
                lastPlantTurn = turnCount
            }
            Tag.PAYOFF_FORESHADOW -> pendingForeshadowings = (pendingForeshadowings - 1).coerceAtLeast(0)
            Tag.NORMAL -> {}
        }
    }

    private fun weightedPick(entries: List<Pair<Int, Double>>): Int {
        val total = entries.sumOf { it.second }
        var r = Random.nextDouble() * total
        for ((idx, w) in entries) {
            r -= w
            if (r <= 0.0) return idx
        }
        return entries.last().first
    }
}
