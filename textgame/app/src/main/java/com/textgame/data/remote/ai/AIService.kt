package com.textgame.data.remote.ai

import com.google.gson.Gson
import com.google.gson.JsonParser
import com.textgame.data.audio.BgmTrack
import com.textgame.domain.model.AIResponse
import com.textgame.domain.model.AttributeCategory
import com.textgame.domain.model.BackgroundSetting
import com.textgame.domain.model.GameState
import com.textgame.domain.model.NPC
import com.textgame.domain.model.Protagonist
import com.textgame.domain.model.StreamingChunk
import com.textgame.domain.model.Summary
import com.textgame.domain.model.WorldSetting
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flowOn
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.BufferedReader
import java.io.InputStreamReader

class AIService(
    private val apiService: DeepSeekApiService,
    private val apiKey: String,
    private val model: String = "deepseek-chat",
    private val dialogueTemperature: Float = 0.8f,
    private val dialogueMaxTokens: Int = 2000,
    private val summaryTemperature: Float = 0.5f,
    private val summaryMaxTokens: Int = 1000,
    private val thinkingEnabled: Boolean = false,
    private val reasoningEffort: String = "high"
) {
    private val gson = Gson()

    /**
     * 构建带思考模式参数的对话请求
     */
    private fun buildDialogueRequest(
        messages: List<ChatMessage>,
        useJsonFormat: Boolean = false,
        maxTokens: Int = dialogueMaxTokens
    ): ChatCompletionRequest {
        return if (thinkingEnabled) {
            // 思考模式不支持 temperature、top_p 等参数
            ChatCompletionRequest(
                model = model,
                messages = messages,
                temperature = null,
                maxTokens = maxTokens,
                responseFormat = if (useJsonFormat) ResponseFormat(type = "json_object") else null,
                reasoningEffort = reasoningEffort,
                thinking = ThinkingConfig(type = "enabled")
            )
        } else {
            ChatCompletionRequest(
                model = model,
                messages = messages,
                temperature = dialogueTemperature,
                maxTokens = maxTokens,
                responseFormat = if (useJsonFormat) ResponseFormat(type = "json_object") else null
            )
        }
    }

    /**
     * 构建带思考模式参数的总结请求
     */
    private fun buildSummaryRequest(
        messages: List<ChatMessage>
    ): ChatCompletionRequest {
        return if (thinkingEnabled) {
            ChatCompletionRequest(
                model = model,
                messages = messages,
                temperature = null,
                maxTokens = summaryMaxTokens,
                reasoningEffort = reasoningEffort,
                thinking = ThinkingConfig(type = "enabled")
            )
        } else {
            ChatCompletionRequest(
                model = model,
                messages = messages,
                temperature = summaryTemperature,
                maxTokens = summaryMaxTokens
            )
        }
    }

    /**
     * 为NPC列表分配唯一ID（如果尚未分配）
     * 格式：npc_001, npc_002, ...
     */
    fun assignNpcIds(npcs: List<NPC>): List<NPC> {
        var counter = 1
        return npcs.map { npc ->
            if (npc.npcId.isBlank()) {
                npc.copy(npcId = "npc_${counter++.toString().padStart(3, '0')}")
            } else {
                npc
            }
        }
    }

    /**
     * 为单个NPC生成下一个可用的ID
     */
    fun generateNextNpcId(existingNpcs: List<NPC>): String {
        val maxNum = existingNpcs.mapNotNull {
            it.npcId.removePrefix("npc_").toIntOrNull()
        }.maxOrNull() ?: 0
        return "npc_${(maxNum + 1).toString().padStart(3, '0')}"
    }

    suspend fun generateDialogueResponse(
        worldSetting: WorldSetting,
        backgroundSetting: BackgroundSetting,
        summary: Summary?,
        preSummaryDialogues: List<String>,
        postSummaryDialogues: List<String>,
        protagonist: Protagonist,
        npcs: List<NPC>,
        gameState: GameState,
        userInput: String
    ): AIResponse {
        val systemPrompt = buildSystemPrompt(worldSetting, backgroundSetting)
        val worldRulesPrompt = buildWorldRulesPrompt(worldSetting.worldRules)
        val dialogueHistoryPrompt = buildDialogueHistoryPrompt(summary, preSummaryDialogues, postSummaryDialogues)
        val gameStatePrompt = buildGameStatePrompt(
            protagonist, npcs, gameState,
            worldSetting.attributeCategories, backgroundSetting.majorPlotThreads
        )
        val userPrompt = buildUserPrompt(userInput)

        // 消息按稳定性从高到低排列，最大化DeepSeek上下文缓存命中率：
        // 1. system: 静态规则+世界观+背景设定+输出指令（几乎不变）
        // 2. dialogueHistory: 对话历史（纯追加，已有前缀永远不变，缓存命中最高）
        // 3. worldRules: 世界观细则（偶尔增长或修改已有条目，不如对话历史稳定）
        // 4. gameState: 当前游戏状态（每轮变化）
        // 5. userInput: 玩家输入（每轮不同）
        val messages = listOf(
            ChatMessage(role = "system", content = systemPrompt),
            ChatMessage(role = "user", content = dialogueHistoryPrompt),
            ChatMessage(role = "user", content = worldRulesPrompt),
            ChatMessage(role = "user", content = gameStatePrompt),
            ChatMessage(role = "user", content = userPrompt)
        )

        val request = buildDialogueRequest(messages, useJsonFormat = true)

        val response = apiService.createChatCompletion(request)
        val content = response.choices.firstOrNull()?.message?.content ?: ""
        if (content.isBlank()) {
            // JSON Mode有概率返回空content，降级为普通模式重试
            val fallbackRequest = buildDialogueRequest(messages, useJsonFormat = false)
            val fallbackResponse = apiService.createChatCompletion(fallbackRequest)
            val fallbackContent = fallbackResponse.choices.firstOrNull()?.message?.content ?: ""
            return parseAIResponse(fallbackContent).copy(
                tokenUsage = fallbackResponse.usage?.let {
                    com.textgame.domain.model.TokenUsage(
                        promptTokens = it.promptTokens,
                        completionTokens = it.completionTokens,
                        totalTokens = it.totalTokens
                    )
                }
            )
        }
        val aiResponse = parseAIResponse(content)
        return aiResponse.copy(
            tokenUsage = response.usage?.let {
                com.textgame.domain.model.TokenUsage(
                    promptTokens = it.promptTokens,
                    completionTokens = it.completionTokens,
                    totalTokens = it.totalTokens
                )
            }
        )
    }

    fun streamDialogueResponse(
        worldSetting: WorldSetting,
        backgroundSetting: BackgroundSetting,
        summary: Summary?,
        preSummaryDialogues: List<String>,
        postSummaryDialogues: List<String>,
        protagonist: Protagonist,
        npcs: List<NPC>,
        gameState: GameState,
        userInput: String
    ): Flow<StreamingChunk> = callbackFlow {
        val systemPrompt = buildSystemPrompt(worldSetting, backgroundSetting)
        val worldRulesPrompt = buildWorldRulesPrompt(worldSetting.worldRules)
        val dialogueHistoryPrompt = buildDialogueHistoryPrompt(summary, preSummaryDialogues, postSummaryDialogues)
        val gameStatePrompt = buildGameStatePrompt(
            protagonist, npcs, gameState,
            worldSetting.attributeCategories, backgroundSetting.majorPlotThreads
        )
        val userPrompt = buildUserPrompt(userInput)

        val messages = listOf(
            ChatMessage(role = "system", content = systemPrompt),
            ChatMessage(role = "user", content = dialogueHistoryPrompt),
            ChatMessage(role = "user", content = worldRulesPrompt),
            ChatMessage(role = "user", content = gameStatePrompt),
            ChatMessage(role = "user", content = userPrompt)
        )

        val request = buildDialogueRequest(messages, useJsonFormat = false).copy(stream = true)
        val call = apiService.createChatCompletionStream(request)

        call.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (!response.isSuccessful) {
                    trySend(StreamingChunk.Error("HTTP ${response.code()}: ${response.message()}"))
                    close()
                    return
                }

                val body = response.body() ?: run {
                    trySend(StreamingChunk.Error("Empty response body"))
                    close()
                    return
                }

                Thread {
                    var fullContent = StringBuilder()
                    var lastNarrativeLen = 0
                    var lastDialogueLen = 0
                    val reader = BufferedReader(InputStreamReader(body.byteStream()))
                    var line: String?

                    try {
                        while (reader.readLine().also { line = it } != null) {
                            val currentLine = line ?: continue
                            if (currentLine.startsWith("data: ")) {
                                val data = currentLine.removePrefix("data: ").trim()
                                if (data == "[DONE]") break

                                try {
                                    val streamResponse = gson.fromJson(data, ChatCompletionStreamResponse::class.java)
                                    val delta = streamResponse.choices.firstOrNull()?.delta?.content ?: ""
                                    if (delta.isNotEmpty()) {
                                        fullContent.append(delta)
                                        val currentFull = fullContent.toString()

                                        val narrativeDelta = extractNarrativeDelta(currentFull, lastNarrativeLen)
                                        if (narrativeDelta.isNotEmpty()) {
                                            lastNarrativeLen += narrativeDelta.length
                                            trySend(StreamingChunk.NarrativeDelta(narrativeDelta))
                                        }

                                        val dialogueDelta = extractDialogueDelta(currentFull, lastDialogueLen)
                                        if (dialogueDelta.isNotEmpty()) {
                                            lastDialogueLen += dialogueDelta.length
                                            trySend(StreamingChunk.DialogueDelta(dialogueDelta))
                                        }
                                    }

                                    if (streamResponse.choices.firstOrNull()?.finishReason != null) {
                                        break
                                    }
                                } catch (_: Exception) {
                                }
                            }
                        }

                        val finalContent = fullContent.toString()
                        val aiResponse = parseAIResponse(finalContent)
                        trySend(StreamingChunk.Complete(aiResponse))
                    } catch (e: Exception) {
                        trySend(StreamingChunk.Error(e.message ?: "Stream error"))
                    } finally {
                        try {
                            reader.close()
                        } catch (_: Exception) {}
                        close()
                    }
                }.start()
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                trySend(StreamingChunk.Error(t.message ?: "Network error"))
                close()
            }
        })

        awaitClose {
            if (!call.isCanceled) {
                call.cancel()
            }
        }
    }.flowOn(Dispatchers.IO)

    private fun extractNarrativeDelta(fullContent: String, lastLen: Int): String {
        val key = "\"narrative\""
        val keyIndex = fullContent.indexOf(key)
        if (keyIndex == -1) return ""

        val valueStart = fullContent.indexOf('"', keyIndex + key.length)
        if (valueStart == -1) return ""

        var i = valueStart + 1
        var currentValue = StringBuilder()
        var inEscape = false

        while (i < fullContent.length) {
            val c = fullContent[i]
            if (inEscape) {
                currentValue.append(c)
                inEscape = false
            } else if (c == '\\') {
                inEscape = true
                currentValue.append(c)
            } else if (c == '"') {
                break
            } else {
                currentValue.append(c)
            }
            i++
        }

        val rawValue = currentValue.toString()
        if (rawValue.length <= lastLen) return ""
        return unescapeJsonString(rawValue.substring(lastLen))
    }

    private fun extractDialogueDelta(fullContent: String, lastLen: Int): String {
        val key = "\"dialogue\""
        val keyIndex = fullContent.indexOf(key)
        if (keyIndex == -1) return ""

        val valueStart = fullContent.indexOf('"', keyIndex + key.length)
        if (valueStart == -1) return ""

        var i = valueStart + 1
        var currentValue = StringBuilder()
        var inEscape = false

        while (i < fullContent.length) {
            val c = fullContent[i]
            if (inEscape) {
                currentValue.append(c)
                inEscape = false
            } else if (c == '\\') {
                inEscape = true
                currentValue.append(c)
            } else if (c == '"') {
                break
            } else {
                currentValue.append(c)
            }
            i++
        }

        val rawValue = currentValue.toString()
        if (rawValue.length <= lastLen) return ""
        return unescapeJsonString(rawValue.substring(lastLen))
    }

    private fun unescapeJsonString(s: String): String {
        return buildString {
            var i = 0
            while (i < s.length) {
                val c = s[i]
                if (c == '\\' && i + 1 < s.length) {
                    when (val next = s[i + 1]) {
                        'n' -> append('\n')
                        't' -> append('\t')
                        'r' -> append('\r')
                        '"' -> append('"')
                        '\\' -> append('\\')
                        '/' -> append('/')
                        else -> append(next)
                    }
                    i += 2
                } else {
                    append(c)
                    i++
                }
            }
        }
    }

    suspend fun generateSummary(
        worldSetting: WorldSetting,
        recentDialogues: List<String>,
        protagonist: Protagonist,
        npcs: List<NPC>,
        gameState: GameState,
        previousSummary: Summary? = null
    ): Summary {
        val prompt = buildSummaryPrompt(worldSetting, recentDialogues, protagonist, npcs, gameState, previousSummary)

        val messages = listOf(
            ChatMessage(role = "system", content = "你是一个游戏剧情总结助手，负责总结近期游戏进展。"),
            ChatMessage(role = "user", content = prompt)
        )

        val request = buildSummaryRequest(messages)

        val response = apiService.createChatCompletion(request)
        val content = response.choices.firstOrNull()?.message?.content ?: ""
        return parseSummaryResponse(content, gameState)
    }

    suspend fun generateWorldFromPrompt(userPrompt: String): GeneratedWorldResult {
        val systemPrompt = """
            你是一个文字冒险游戏的世界生成助手。用户用一句话描述想要的游戏世界，你需要生成完整的游戏设定。
            你必须以纯JSON格式回复，不要有任何额外的文字说明或markdown标记。

            JSON格式如下：
            {
              "gameName": "游戏名称",
              "protagonistName": "主角名字",
              "worldName": "世界名称",
              "worldType": "世界类型（如：奇幻/科幻/现代/末日/武侠/都市/历史等）",
              "worldDescription": "世界的详细描述，100-300字",
              "timeSetting": "时间设定",
              "locationSetting": "起始地点",
              "socialStructure": "社会结构简述",
              "specialRules": ["特殊规则1", "特殊规则2"],
              "lore": "世界观历史和传说",
              "protagonistBackground": "主角的详细背景故事",
              "worldHistory": "世界历史",
              "attributes": [
                {"name": "属性名称1", "type": "NUMERIC/BOOLEAN/STRING", "minValue": 最小值（数字类型必填）, "maxValue": 最大值（数字类型必填）, "defaultValue": 默认值, "description": "属性描述"},
                {"name": "属性名称2", "type": "NUMERIC/BOOLEAN/STRING", "minValue": 最小值（数字类型必填）, "maxValue": 最大值（数字类型必填）, "defaultValue": 默认值, "description": "属性描述"},
                {"name": "属性名称3", "type": "NUMERIC/BOOLEAN/STRING", "minValue": 最小值（数字类型必填）, "maxValue": 最大值（数字类型必填）, "defaultValue": 默认值, "description": "属性描述"}
              ],
              "npcs": [
                {
                  "name": "NPC名字",
                  "role": "身份/职业",
                  "personality": "性格特点",
                  "backstory": "背景故事",
                  "mood": "当前情绪",
                  "appearance": "外貌描述"
                }
              ]
              
            NPC的ID会自动分配为npc_001, npc_002等格式，你不需要在JSON中提供npcId字段
            }

            重要规则：
            1. 整个世界设定要完整、有创意、有故事性
            2. 主角背景要与世界设定紧密相关
            3. NPC要有鲜明的性格和与主角的关系
            4. 属性要符合世界类型（奇幻可以有魔力值，科幻可以有科技值等）
            5. 你的整个回复只能是JSON
        """.trimIndent()

        val messages = listOf(
            ChatMessage(role = "system", content = systemPrompt),
            ChatMessage(role = "user", content = "我想要一个这样的游戏世界：$userPrompt")
        )

        val request = buildDialogueRequest(messages, useJsonFormat = true, maxTokens = 8000)

        val response = apiService.createChatCompletion(request)
        val content = response.choices.firstOrNull()?.message?.content ?: ""
        if (content.isBlank()) {
            val fallbackRequest = buildDialogueRequest(messages, useJsonFormat = false, maxTokens = 8000)
            val fallbackResponse = apiService.createChatCompletion(fallbackRequest)
            return parseGeneratedWorld(fallbackResponse.choices.firstOrNull()?.message?.content ?: "")
        }
        return parseGeneratedWorld(content)
    }

    private fun buildSystemPrompt(
        worldSetting: WorldSetting,
        backgroundSetting: BackgroundSetting
    ): String = buildString {
        appendLine("【输出格式要求】")
        appendLine("你必须以纯JSON格式回复，不要有任何额外的文字说明或markdown代码块标记。你的整个回复内容必须是一个可以直接被解析的JSON对象。")
        appendLine()
        appendLine("JSON格式如下：")
        appendLine("{")
        appendLine("  \"dialogue\": \"当前说话NPC的对话内容（如果有多个NPC轮流说话，用换行分隔，格式如：角色名：台词）\",")
        appendLine("  \"narrative\": \"场景描述、旁白、动作描写等叙述性内容，这是核心字段，必须详细丰富\",")
        appendLine("  \"bgm\": \"背景音乐关键词（可选，从下方BGM列表中选择一个最匹配当前剧情氛围的关键词，如果不需要切换则不填该字段）\",")
        appendLine("  \"choices\": [")
        appendLine("    \"选项1的文字描述\",")
        appendLine("    \"选项2的文字描述\",")
        appendLine("    \"选项3的文字描述\"")
        appendLine("  ],")
        appendLine("  \"state_changes\": {")
        appendLine("    \"protagonist\": {")
        appendLine("      \"attributes\": {\"发生变化的属性名\": 新数值（只返回变化的属性）},")
        appendLine("      \"inventory_add\": [\"新增物品\"],")
        appendLine("      \"inventory_remove\": [\"移除物品\"],")
        appendLine("      \"location_change\": \"新位置\"")
        appendLine("    },")
        appendLine("    \"npc\": {")
        appendLine("      \"已存在NPC的ID（如npc_001）\": {")
        appendLine("        \"mood\": \"新情绪\",")
        appendLine("        \"awareness\": \"新的认知更新\",")
        appendLine("        \"briefing\": \"一句话简介更新\",")
        appendLine("        \"personality\": \"性格特点更新\",")
        appendLine("        \"backstory\": \"背景故事更新（记录过去的经历或当前经历的重大事件）\",")
        appendLine("        \"appearance\": \"外貌描述更新（添加新的细节变化）\",")
        appendLine("        \"attributes\": {\"发生变化的属性名\": 新数值（只返回变化的属性）}")
        appendLine("      },")
        appendLine("      \"要删除的NPC的ID（如npc_002）\": {")
        appendLine("        \"is_deleted\": true")
        appendLine("      },")
        appendLine("      \"新NPC的ID（新出现的角色，格式如npc_003）\": {")
        appendLine("        \"is_new\": true,")
        appendLine("        \"name\": \"NPC名称\",")
        appendLine("        \"role\": \"角色身份（如：旅店老板、神秘剑客等）\",")
        appendLine("        \"briefing\": \"一句话简介\",")
        appendLine("        \"appearance\": \"外貌详细描述\",")
        appendLine("        \"personality\": \"性格特点\",")
        appendLine("        \"backstory\": \"背景故事\",")
        appendLine("        \"mood\": \"当前情绪\",")
        appendLine("        \"awareness\": \"对主角的认知\",")
        appendLine("        \"attributes\": {\"属性名\": 初始数值}")
        appendLine("      }")
        appendLine("    },")
        appendLine("    \"game\": {")
        appendLine("      \"scene_change\": \"新场景名称\",")
        appendLine("      \"event_trigger\": \"触发的事件名\",")
        appendLine("      \"flag_set\": {\"标记名\": true/false},")
        appendLine("      \"world_rules\": [")
        appendLine("        {\"id\": \"worldrule_001\", \"content\": \"更新已有细则的完整新内容\"},")
        appendLine("        {\"id\": \"worldrule_003\", \"content\": \"新建细则的内容\"}")
        appendLine("      ]")
        appendLine("    }")
        appendLine("  },")
        appendLine("  \"summary_update\": false")
        appendLine("}")
        appendLine()
        appendLine("重要规则（必须严格遵守）：")
        appendLine("1. 你的整个回复只能是JSON，不能有任何其他文字、解释或markdown代码块标记")
        appendLine("2. 每次回复都必须有state_changes字段，即使没有变化也要包含该字段")
        appendLine("3. narrative字段是核心，必须包含完整的情节叙述，内容要充实饱满")
        appendLine("4. narrative中必须包含：当前场景的环境描写（光线、声音、气味等感官细节）、在场NPC的状态和动作描写、事件进展、主角的感受")
        appendLine("5. 如果是NPC第一次出场，必须在narrative中详细描写其外貌（面容、身材、穿着、气质）")
        appendLine("6. 对话要符合角色性格，自然流畅，配合动作和表情描写")
        appendLine("7. 用直白、有画面感的文字描写，让玩家有身临其境的感觉")
        appendLine("8. 每次回复都要推动剧情，提供足够的互动空间")
        appendLine("9. 适当加入突发事件或环境变化，让场景更生动真实")
        appendLine("10. choices字段必须包含3-4个玩家可以选择的行动选项，每个选项用简洁的文字描述玩家可以做什么")
        appendLine("11. 选项应该多样化，可以是对话选项、行动选项、调查选项等")
        appendLine("12. 无论玩家输入是什么，都必须输出state_changes来更新游戏状态")
        appendLine("13. 每个NPC都有一个唯一的ID（如npc_001、npc_002）。更新已有NPC时，必须使用其ID作为key；新出现的NPC也需要分配一个新ID（按照已有ID顺序递增），并且is_new必须设为true")
        appendLine("14. 已经在场的NPC不要重复设置is_new，只更新需要变化的字段，key用其ID")
        appendLine("15. name只是NPC的普通属性，不是身份标识，身份标识永远是npcId")
        appendLine("16. 【NPC删除 - 重要】当某个NPC确认死亡、永久离开剧情、或不可能再出现在后续剧情中时，可以将其删除以节省token。删除规则：")
        appendLine("    - 只有不重要的、跑龙套的NPC才能删除（如：路人甲、守卫、临时出现的小角色等）")
        appendLine("    - 重要NPC、主线角色、有后续剧情的NPC绝对不能删除，即使暂时离开也不能删")
        appendLine("    - 删除时，在npc对象中以该NPC的ID为key，设置\"is_deleted\": true即可，不需要其他字段")
        appendLine("    - 删除NPC前，请确保该NPC的所有重要信息已经被记录在世界观细则或总结中（如果重要的话）")
        appendLine("    - 如果不确定该不该删，就不要删，保留下来更安全")
        appendLine("17. NPC的所有字段都可以随着剧情发展更新：name（名称）、role（角色定位）、briefing（简介）、mood（当前情绪）、awareness（对主角的认知）、appearance（外貌）、personality（性格）、backstory（背景故事）。当剧情揭示了新的信息或状态发生变化时，必须及时更新对应的字段。特别注意：")
        appendLine("    - backstory（背景故事）：返回完整的、自包含的内容，包含所有历史信息+最新动态，不是只返回增量。原有内容中仍然重要的信息必须保留并整合，再加上新发生的重大事件，绝不能用新内容直接覆盖历史")
        appendLine("    - appearance（外貌描述）：返回完整的、自包含的外貌描述，包含基础形象（面容、身材、气质、常服等）+ 最新变化（临时服装、受伤、表情等）。基础形象必须保留，新变化是在基础上补充，绝不能用临时状态覆盖整体形象")
        appendLine("    - 当NPC经历了重要事件后，必须在对应的字段中体现出来")
        appendLine("18. attributes字段只需要返回发生变化的属性！引擎会保留所有原有属性不变。如果某属性没有变化，可以不返回该属性。")
        appendLine("19. 当剧情有重大进展（每10-20轮）时，将summary_update设为true来触发自动总结")
        appendLine("20. 禁止在attributes中创建新的属性名或修改属性类目！只能更新已存在的属性值。如果需要记录新的信息，请使用game.flag_set或其他方式。")
        appendLine("21. 引擎会忽略你返回的任何不在原有属性列表中的属性名。如果你尝试添加新属性，该属性将被丢弃。")
        appendLine("22. 【世界观细则维护 - 重要】你必须主动维护世界观细则（game.world_rules），这是游戏世界的知识库：")
        appendLine("    - 当剧情中揭示了新的世界设定、历史背景、魔法/力量体系规则、社会制度、文化习俗、地理信息、特殊规则等长期有效的信息时，必须记录为世界观细则")
        appendLine("    - 【优先更新】当新信息与已有细则属于同一主题时，必须更新已有细则的内容（合并、补充、修正），而不是新建条目。例如已有细则记录了某城市的描述，当剧情揭示了该城市的新信息时，应更新该条细则而非新建")
        appendLine("    - 只有当信息属于全新的、与现有细则无关联的主题时，才新建细则")
        appendLine("    - 禁止删除任何已有的世界观细则，即使你认为它不再重要")
        appendLine("    - 新增细则时，id由你指定，格式为 worldrule_XXX（XXX为数字编号，按顺序递增，确保与已有细则的ID不冲突）")
        appendLine("    - 修改已有细则时必须填写该细则的id，id从上方\"世界观细则\"列表中获取")
        appendLine("    - 每条细则用简洁的一句话概括核心内容，不要长篇大论")
        appendLine("    - 如果本轮没有新增或修改的细则，world_rules字段可以省略")
        appendLine()
        appendLine("【背景音乐BGM点播】")
        appendLine("你可以根据当前剧情氛围点播背景音乐。可用的BGM关键词如下：")
        val bgmKeywords = BgmTrack.availableKeywords()
        bgmKeywords.forEach { keyword ->
            appendLine("- $keyword")
        }
        appendLine()
        appendLine("BGM点播规则：")
        appendLine("1. 当剧情氛围发生明显变化时（如进入战斗、发现危险、获得胜利、场景切换等），选择最匹配的BGM关键词填入bgm字段")
        appendLine("2. 如果当前氛围没有明显变化，不需要切换BGM，就不要填bgm字段（省略该字段）")
        appendLine("3. 必须从上面的列表中选择关键词，不能自创关键词")
        appendLine("4. 季节类BGM（春夏、秋天、冬天）在场景切换到对应季节环境时使用")
        appendLine("5. 情绪类BGM（战斗、危险临近、胜利、爱情、未知的恐惧）在剧情氛围匹配时使用")
        appendLine()
        appendLine("你是一个文字冒险游戏的游戏主持人和NPC扮演助手。")
        appendLine("你的回复必须详细、丰富、生动，给玩家沉浸式的游戏体验。")
        appendLine()
        appendLine("【核心叙事原则】")
        appendLine("1. 每次回复必须包含完整的情节叙述，绝不能简短敷衍。叙述部分要充实饱满，有完整的场景、动作、对话和心理描写。")
        appendLine("2. 用生动、有画面感且直白的文字进行描写，让读者如临其境。")
        appendLine("3. 每次回复都要详细描述当前环境：包括光线、色彩、声音、气味、温度、触感等感官细节。")
        appendLine("4. 对于NPC：第一次出场时要详细描写其外貌特征（面容、身材、穿着、气质等）；后续每次出场也要描写其当前状态、表情、动作、语气变化。")
        appendLine("5. 注重动作描写：人物的举手投足、眼神变化、肢体语言都要具体呈现。")
        appendLine("6. 适时加入突发事件或环境变化，增加戏剧张力和真实感。")
        appendLine("7. 适当描写主角的心理活动、感官感受和生理反应，增强代入感。")
        appendLine("8. 对话要自然流畅，符合角色身份，配合动作和表情一起呈现。")
        appendLine("9. 推动剧情发展，每次回复都要有实质性的内容进展，给玩家提供充足的互动和选择空间。")
        appendLine("10. 叙述和描写要直白易懂，不用过于晦涩的辞藻，但要有画面感和感染力。")
        appendLine()
        appendLine("【世界观设定】")
        appendLine("世界名称：${worldSetting.name}")
        appendLine("世界类型：${worldSetting.worldType}")
        appendLine("描述：${worldSetting.description}")
        appendLine("时间设定：${worldSetting.timeSetting}")
        appendLine("地点设定：${worldSetting.locationSetting}")
        appendLine("社会结构：${worldSetting.socialStructure}")
        if (worldSetting.specialRules.isNotEmpty()) {
            appendLine("特殊规则：")
            worldSetting.specialRules.forEach { appendLine("- $it") }
        }
        if (worldSetting.lore.isNotEmpty()) {
            appendLine("世界观历史：${worldSetting.lore}")
        }
        appendLine()
        appendLine("【背景设定】")
        appendLine("主角背景：${backgroundSetting.protagonistBackground}")
        if (backgroundSetting.worldHistory.isNotEmpty()) {
            appendLine("世界历史：${backgroundSetting.worldHistory}")
        }
        appendLine()
        appendLine("请严格遵循以上设定进行游戏，保持角色性格和世界规则的一致性。")
        appendLine()
        appendLine("【本轮输出提醒】")
        appendLine("请以纯JSON格式回复，格式和规则已在上方详细说明。")
        appendLine("特别注意：")
        appendLine("- narrative必须详细丰富，包含完整场景、动作、对话和心理描写")
        appendLine("- 每次回复都要推动剧情发展")
        appendLine("- attributes只返回变化的属性，引擎会保留其他属性不变")
        appendLine("- 禁止创建新属性名或修改属性类目")
    }

    private fun buildWorldRulesPrompt(
        worldRules: List<com.textgame.domain.model.WorldRule>
    ): String = buildString {
        if (worldRules.isNotEmpty()) {
            appendLine("【世界观细则】")
            worldRules.forEach { rule ->
                appendLine("[${rule.id}] ${rule.content}")
            }
        } else {
            appendLine("【世界观细则】暂无")
        }
    }

    private fun buildDialogueHistoryPrompt(
        summary: Summary?,
        preSummaryDialogues: List<String>,
        postSummaryDialogues: List<String>
    ): String = buildString {
        val hasContent = preSummaryDialogues.isNotEmpty() ||
            (summary != null && summary.summaryText.isNotEmpty()) ||
            postSummaryDialogues.isNotEmpty()

        if (!hasContent) {
            appendLine("【对话历史】暂无")
            return@buildString
        }

        if (preSummaryDialogues.isNotEmpty()) {
            appendLine("【总结前的对话记录（参考）】")
            preSummaryDialogues.forEach { appendLine(it) }
            appendLine()
        }

        if (summary != null && summary.summaryText.isNotEmpty()) {
            appendLine("【近期进度总结】")
            appendLine(summary.summaryText)
            appendLine()
        }

        if (postSummaryDialogues.isNotEmpty()) {
            appendLine("【上次总结后的对话记录】")
            postSummaryDialogues.forEach { appendLine(it) }
            appendLine()
        }
    }

    private fun buildGameStatePrompt(
        protagonist: Protagonist,
        npcs: List<NPC>,
        gameState: GameState,
        attributeCategories: List<AttributeCategory> = emptyList(),
        majorPlotThreads: List<String> = emptyList()
    ): String = buildString {
        appendLine("【主角状态】")
        appendLine("姓名：${protagonist.name}")
        appendLine("位置：${protagonist.location}")
        if (protagonist.attributes.isNotEmpty()) {
            appendLine("属性：")
            protagonist.attributes.forEach { (key, value) ->
                val cat = attributeCategories.find { it.name == key }
                val meta = buildString {
                    if (cat != null) {
                        append("类型:${cat.type.name.lowercase()}")
                        if (cat.minValue != null) append(" 最小:${cat.minValue}")
                        if (cat.maxValue != null) append(" 最大:${cat.maxValue}")
                        if (cat.description.isNotBlank()) append(" - ${cat.description}")
                    }
                }
                if (meta.isNotEmpty()) {
                    appendLine("  $key: $value  [$meta]")
                } else {
                    appendLine("  $key: $value")
                }
            }
        }
        if (protagonist.inventory.isNotEmpty()) {
            appendLine("物品：${protagonist.inventory.joinToString("、")}")
        }
        appendLine()

        if (npcs.isNotEmpty()) {
            appendLine("【在场NPC】")
            npcs.forEach { npc ->
                val displayId = npc.npcId.ifBlank { "未分配" }
                appendLine("ID: ${displayId} | 名称: ${npc.name}（${npc.role}）")
                if (npc.briefing.isNotEmpty()) {
                    appendLine("  简介：${npc.briefing}")
                }
                appendLine("  情绪：${npc.mood}")
                if (npc.awareness.isNotEmpty()) {
                    appendLine("  认知：${npc.awareness}")
                }
                if (npc.personality.isNotEmpty()) {
                    appendLine("  性格：${npc.personality}")
                }
                if (npc.backstory.isNotEmpty()) {
                    appendLine("  背景：${npc.backstory}")
                }
                if (npc.appearance.isNotEmpty()) {
                    appendLine("  外貌：${npc.appearance}")
                }
                if (npc.attributes.isNotEmpty()) {
                    appendLine("  属性：")
                    npc.attributes.forEach { (key, value) ->
                        val cat = attributeCategories.find { it.name == key }
                        val meta = buildString {
                            if (cat != null) {
                                append("类型:${cat.type.name.lowercase()}")
                                if (cat.minValue != null) append(" 最小:${cat.minValue}")
                                if (cat.maxValue != null) append(" 最大:${cat.maxValue}")
                            }
                        }
                        if (meta.isNotEmpty()) {
                            appendLine("    $key: $value  [$meta]")
                        } else {
                            appendLine("    $key: $value")
                        }
                    }
                }
            }
            appendLine()
        }

        if (majorPlotThreads.isNotEmpty()) {
            appendLine("【主要剧情线】")
            majorPlotThreads.forEach { appendLine("- $it") }
            appendLine()
        }

        appendLine("【当前场景】${gameState.currentScene}")
        appendLine("【当前轮次】${gameState.turnCount}")
    }

    private fun buildUserPrompt(userInput: String): String = buildString {
        appendLine("【玩家输入】")
        appendLine(userInput)
    }

    private fun buildSummaryPrompt(
        worldSetting: WorldSetting,
        recentDialogues: List<String>,
        protagonist: Protagonist,
        npcs: List<NPC>,
        gameState: GameState,
        previousSummary: Summary? = null
    ): String = buildString {
        appendLine("【世界观】")
        appendLine("世界名称：${worldSetting.name}")
        appendLine("世界类型：${worldSetting.worldType}")
        appendLine()
        if (previousSummary != null && previousSummary.summaryText.isNotEmpty()) {
            appendLine("【上一次总结】")
            appendLine(previousSummary.summaryText)
            appendLine()
            appendLine("注意：你需要结合近期对话和上一次总结，生成一份新的、完整的总结。")
            appendLine("判断标准：")
            appendLine("- 上一次总结中仍然重要、与当前剧情相关的信息，保留并整合进来")
            appendLine("- 已经过时、不再重要、或被后续剧情覆盖的信息，舍弃掉")
            appendLine("- 近期对话中的新内容，有重要性的加入，不重要的不加入")
            appendLine("- 最终输出是一份完整的、自包含的总结，不是增量追加")
            appendLine()
        }
        appendLine("【近期完整对话记录】")
        recentDialogues.forEachIndexed { index, dialogue ->
            appendLine("${index + 1}. $dialogue")
        }
        appendLine()
        if (npcs.isNotEmpty()) {
            appendLine("【NPC当前状态】")
            npcs.forEach { npc ->
                val displayId = npc.npcId.ifBlank { "未分配" }
                appendLine("ID: ${displayId} | 名称: ${npc.name}（${npc.role}）")
                appendLine("  情绪: ${npc.mood}, 认知: ${npc.awareness}")
            }
            appendLine()
        }
        appendLine("【主角当前状态】")
        appendLine("姓名：${protagonist.name}，位置：${protagonist.location}")
        if (protagonist.attributes.isNotEmpty()) {
            appendLine("属性：${protagonist.attributes.entries.joinToString(", ") { "${it.key}=${it.value}" }}")
        }
        if (protagonist.inventory.isNotEmpty()) {
            appendLine("物品：${protagonist.inventory.joinToString("、")}")
        }
        appendLine()
        appendLine("【当前轮次】${gameState.turnCount}，当前场景：${gameState.currentScene}")
        appendLine()
        appendLine("【总结要求】")
        appendLine("请基于以上对话记录，提炼并总结关键剧情点和关键信息。注意：")
        appendLine("- 这是总结，不是原文复述。禁止大段粘贴原文，必须用自己的话提炼概括。")
        appendLine("- 要覆盖尽可能多的剧情细节，每个重要节点都要提到。")
        appendLine("- 只陈述客观事实，不要加入主观评价、心理分析或推测。")
        appendLine("- 语言简洁，不要啰嗦。")
        appendLine()
        appendLine("总结必须包含以下内容：")
        appendLine()
        appendLine("1. 【关键事件】按时间顺序列出所有重要事件。每个事件说明：发生了什么、涉及谁、在哪里、导致了什么结果。")
        appendLine()
        appendLine("2. 【场景变化】主角经过的场景/地点，以及每个场景中发生的关键事。")
        appendLine()
        appendLine("3. 【待处理事项】对话中明确提到的、主角尚未完成的事项或约定。只写已有信息，不做推测。")
        appendLine()
        appendLine("请用简洁的条目式撰写，每个条目一行，方便后续检索。")
    }

    private fun parseAIResponse(content: String): AIResponse {
        return try {
            val jsonStr = extractJson(content)
            gson.fromJson(jsonStr, AIResponse::class.java)
        } catch (e: Exception) {
            AIResponse(
                narrative = content,
                dialogue = ""
            )
        }
    }

    private fun parseSummaryResponse(content: String, gameState: GameState): Summary {
        return Summary(
            sessionId = gameState.sessionId,
            summaryText = content,
            keyEvents = emptyList(),
            involvedNPCs = emptyList(),
            sceneContext = gameState.currentScene,
            turnRangeStart = gameState.turnCount - 10,
            turnRangeEnd = gameState.turnCount,
            generatedAt = System.currentTimeMillis()
        )
    }

    private fun parseGeneratedWorld(content: String): GeneratedWorldResult {
        return try {
            val jsonStr = extractJson(content)
            val json = JsonParser.parseString(jsonStr).asJsonObject

            val attributes = mutableListOf<com.textgame.domain.model.AttributeCategory>()
            if (json.has("attributes")) {
                json.getAsJsonArray("attributes").forEach { elem ->
                    val obj = elem.asJsonObject
                    val typeStr = obj.get("type")?.asString ?: "NUMERIC"
                    val type = com.textgame.domain.model.AttributeType.valueOf(typeStr.uppercase())
                    val defaultVal = when (type) {
                        com.textgame.domain.model.AttributeType.NUMERIC ->
                            obj.get("defaultValue")?.asDouble ?: 0.0
                        com.textgame.domain.model.AttributeType.BOOLEAN ->
                            obj.get("defaultValue")?.asBoolean ?: false
                        else -> obj.get("defaultValue")?.asString ?: ""
                    }
                    attributes.add(
                        com.textgame.domain.model.AttributeCategory(
                            name = obj.get("name")?.asString ?: "",
                            type = type,
                            minValue = obj.get("minValue")?.asDouble,
                            maxValue = obj.get("maxValue")?.asDouble,
                            defaultValue = defaultVal,
                            description = obj.get("description")?.asString ?: ""
                        )
                    )
                }
            }

            var npcs = mutableListOf<NPC>()
            if (json.has("npcs")) {
                json.getAsJsonArray("npcs").forEach { elem ->
                    val obj = elem.asJsonObject
                    npcs.add(
                        NPC(
                            name = obj.get("name")?.asString ?: "",
                            role = obj.get("role")?.asString ?: "",
                            personality = obj.get("personality")?.asString ?: "",
                            backstory = obj.get("backstory")?.asString ?: "",
                            mood = obj.get("mood")?.asString ?: "neutral",
                            appearance = obj.get("appearance")?.asString ?: "",
                            sessionId = 0
                        )
                    )
                }
            }
            npcs = assignNpcIds(npcs).toMutableList()

            GeneratedWorldResult(
                gameName = json.get("gameName")?.asString ?: "",
                protagonistName = json.get("protagonistName")?.asString ?: "主角",
                worldName = json.get("worldName")?.asString ?: "",
                worldType = json.get("worldType")?.asString ?: "",
                worldDescription = json.get("worldDescription")?.asString ?: "",
                timeSetting = json.get("timeSetting")?.asString ?: "",
                locationSetting = json.get("locationSetting")?.asString ?: "",
                socialStructure = json.get("socialStructure")?.asString ?: "",
                specialRules = json.getAsJsonArray("specialRules")?.map { it.asString } ?: emptyList(),
                lore = json.get("lore")?.asString ?: "",
                protagonistBackground = json.get("protagonistBackground")?.asString ?: "",
                worldHistory = json.get("worldHistory")?.asString ?: "",
                attributes = attributes,
                npcs = npcs
            )
        } catch (e: Exception) {
            GeneratedWorldResult(error = e.message)
        }
    }

    private fun extractJson(content: String): String {
        val jsonStart = content.indexOf('{')
        val jsonEnd = content.lastIndexOf('}')
        return if (jsonStart != -1 && jsonEnd != -1 && jsonEnd > jsonStart) {
            content.substring(jsonStart, jsonEnd + 1)
        } else {
            content
        }
    }
}

data class GeneratedWorldResult(
    val gameName: String = "",
    val protagonistName: String = "",
    val worldName: String = "",
    val worldType: String = "",
    val worldDescription: String = "",
    val timeSetting: String = "",
    val locationSetting: String = "",
    val socialStructure: String = "",
    val specialRules: List<String> = emptyList(),
    val lore: String = "",
    val protagonistBackground: String = "",
    val worldHistory: String = "",
    val attributes: List<com.textgame.domain.model.AttributeCategory> = emptyList(),
    val npcs: List<NPC> = emptyList(),
    val error: String? = null
)
