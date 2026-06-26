package com.textgame.data.remote.ai

import com.google.gson.Gson
import com.google.gson.JsonParser
import com.textgame.domain.model.AIResponse
import com.textgame.domain.model.BackgroundSetting
import com.textgame.domain.model.GameState
import com.textgame.domain.model.NPC
import com.textgame.domain.model.Protagonist
import com.textgame.domain.model.Summary
import com.textgame.domain.model.WorldSetting

class AIService(
    private val apiService: DeepSeekApiService,
    private val apiKey: String,
    private val model: String = "deepseek-chat",
    private val dialogueTemperature: Float = 0.8f,
    private val dialogueMaxTokens: Int = 2000,
    private val summaryTemperature: Float = 0.5f,
    private val summaryMaxTokens: Int = 1000
) {
    private val gson = Gson()

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
        protagonist: Protagonist,
        npcs: List<NPC>,
        gameState: GameState,
        recentDialogues: List<String>,
        userInput: String
    ): AIResponse {
        val systemPrompt = buildSystemPrompt(worldSetting, backgroundSetting)
        val contextPrompt = buildContextPrompt(summary, protagonist, npcs, gameState, recentDialogues)
        val userPrompt = buildUserPrompt(userInput)
        val outputInstruction = buildOutputInstruction()

        val messages = listOf(
            ChatMessage(role = "system", content = systemPrompt),
            ChatMessage(role = "user", content = "$contextPrompt\n\n$userPrompt\n\n$outputInstruction")
        )

        val request = ChatCompletionRequest(
            model = model,
            messages = messages,
            temperature = dialogueTemperature,
            maxTokens = dialogueMaxTokens
        )

        val response = apiService.createChatCompletion(request)
        val content = response.choices.firstOrNull()?.message?.content ?: ""
        return parseAIResponse(content)
    }

    suspend fun generateSummary(
        worldSetting: WorldSetting,
        recentDialogues: List<String>,
        protagonist: Protagonist,
        npcs: List<NPC>,
        gameState: GameState
    ): Summary {
        val prompt = buildSummaryPrompt(worldSetting, recentDialogues, protagonist, npcs, gameState)

        val messages = listOf(
            ChatMessage(role = "system", content = "你是一个游戏剧情总结助手，负责总结近期游戏进展。"),
            ChatMessage(role = "user", content = prompt)
        )

        val request = ChatCompletionRequest(
            model = model,
            messages = messages,
            temperature = summaryTemperature,
            maxTokens = summaryMaxTokens
        )

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
                {"name": "生命值", "type": "NUMERIC", "minValue": 0, "maxValue": 100, "defaultValue": 100, "description": "角色生命值"},
                {"name": "金币", "type": "NUMERIC", "minValue": 0, "maxValue": 999999, "defaultValue": 100, "description": "游戏货币"}
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

        val request = ChatCompletionRequest(
            model = model,
            messages = messages,
            temperature = 1.0f,
            maxTokens = 8000
        )

        val response = apiService.createChatCompletion(request)
        val content = response.choices.firstOrNull()?.message?.content ?: ""
        return parseGeneratedWorld(content)
    }

    private fun buildSystemPrompt(
        worldSetting: WorldSetting,
        backgroundSetting: BackgroundSetting
    ): String = buildString {
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
        if (backgroundSetting.majorPlotThreads.isNotEmpty()) {
            appendLine("主要剧情线：")
            backgroundSetting.majorPlotThreads.forEach { appendLine("- $it") }
        }
        appendLine()
        appendLine("请严格遵循以上设定进行游戏，保持角色性格和世界规则的一致性。")
    }

    private fun buildContextPrompt(
        summary: Summary?,
        protagonist: Protagonist,
        npcs: List<NPC>,
        gameState: GameState,
        recentDialogues: List<String>
    ): String = buildString {
        if (summary != null && summary.summaryText.isNotEmpty()) {
            appendLine("【近期进度总结】")
            appendLine(summary.summaryText)
            appendLine()
        }
        appendLine("【主角状态】")
        appendLine("姓名：${protagonist.name}")
        appendLine("位置：${protagonist.location}")
        if (protagonist.attributes.isNotEmpty()) {
            appendLine("属性：")
            protagonist.attributes.forEach { (key, value) ->
                appendLine("  $key: $value")
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
                if (npc.backstory.isNotEmpty()) {
                    appendLine("  背景：${npc.backstory}")
                }
                if (npc.appearance.isNotEmpty()) {
                    appendLine("  外貌：${npc.appearance}")
                }
            }
            appendLine()
        }
        if (recentDialogues.isNotEmpty()) {
            appendLine("【近期对话记录】")
            recentDialogues.forEach { dialogue ->
                appendLine(dialogue)
            }
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
        gameState: GameState
    ): String = buildString {
        appendLine("【世界观】")
        appendLine("世界名称：${worldSetting.name}")
        appendLine("世界类型：${worldSetting.worldType}")
        appendLine()
        appendLine("【近期对话】")
        recentDialogues.forEachIndexed { index, dialogue ->
            appendLine("${index + 1}. $dialogue")
        }
        appendLine()
        if (npcs.isNotEmpty()) {
            appendLine("【NPC状态】")
            npcs.forEach { npc ->
                val displayId = npc.npcId.ifBlank { "未分配" }
                appendLine("ID: ${displayId} | 名称: ${npc.name}（${npc.role}）- 情绪: ${npc.mood}")
            }
            appendLine()
        }
        appendLine("【主角状态】")
        appendLine("姓名：${protagonist.name}")
        appendLine("位置：${protagonist.location}")
        appendLine()
        appendLine("【当前轮次】${gameState.turnCount}")
        appendLine()
        appendLine("请总结以上游戏进展，包括：")
        appendLine("1. 关键事件")
        appendLine("2. 涉及的NPC")
        appendLine("3. 场景和剧情进展")
        appendLine("4. 主角状态变化")
    }

    private fun buildOutputInstruction(): String = """
        【输出要求】
        你必须以纯JSON格式回复，不要有任何额外的文字说明或markdown代码块标记。你的整个回复内容必须是一个可以直接被解析的JSON对象。

        JSON格式如下：
        {
          "dialogue": "当前说话NPC的对话内容（如果有多个NPC轮流说话，用换行分隔，格式如：角色名：台词）",
          "narrative": "场景描述、旁白、动作描写等叙述性内容，这是核心字段，必须详细丰富",
          "choices": [
            "选项1的文字描述",
            "选项2的文字描述",
            "选项3的文字描述"
          ],
          "state_changes": {
            "protagonist": {
              "attributes": {"发生变化的属性名": 新数值（只返回变化的属性）},
              "inventory_add": ["新增物品"],
              "inventory_remove": ["移除物品"],
              "location_change": "新位置"
            },
            "npc": {
              "已存在NPC的ID（如npc_001）": {
                "mood": "新情绪",
                "awareness": "新的认知更新",
                "briefing": "一句话简介更新",
                "attributes": {"发生变化的属性名": 新数值（只返回变化的属性）}
              },
              "新NPC的ID（新出现的角色，格式如npc_003）": {
                "is_new": true,
                "name": "NPC名称",
                "role": "角色身份（如：旅店老板、神秘剑客等）",
                "briefing": "一句话简介",
                "appearance": "外貌详细描述",
                "personality": "性格特点",
                "backstory": "背景故事",
                "mood": "当前情绪",
                "awareness": "对主角的认知",
                "attributes": {"属性名": 初始数值}
              }
            },
            "game": {
              "scene_change": "新场景名称",
              "event_trigger": "触发的事件名",
              "flag_set": {"标记名": true/false}
            }
          },
          "summary_update": false
        }

        重要规则（必须严格遵守）：
        1. 你的整个回复只能是JSON，不能有任何其他文字、解释或markdown代码块标记
        2. 每次回复都必须有state_changes字段，即使没有变化也要包含该字段
        3. narrative字段是核心，必须包含完整的情节叙述，内容要充实饱满
        4. narrative中必须包含：当前场景的环境描写（光线、声音、气味等感官细节）、在场NPC的状态和动作描写、事件进展、主角的感受
        5. 如果是NPC第一次出场，必须在narrative中详细描写其外貌（面容、身材、穿着、气质）
        6. 对话要符合角色性格，自然流畅，配合动作和表情描写
        7. 用直白、有画面感的文字描写，让玩家有身临其境的感觉
        8. 每次回复都要推动剧情，提供足够的互动空间
        9. 适当加入突发事件或环境变化，让场景更生动真实
        10. choices字段必须包含3-4个玩家可以选择的行动选项，每个选项用简洁的文字描述玩家可以做什么
        11. 选项应该多样化，可以是对话选项、行动选项、调查选项等
        12. 无论玩家输入是什么，都必须输出state_changes来更新游戏状态
        13. 【重要】每个NPC都有一个唯一的ID（如npc_001、npc_002）。更新已有NPC时，必须使用其ID作为key；新出现的NPC也需要分配一个新ID（按照已有ID顺序递增），并且is_new必须设为true
        14. 已经在场的NPC不要重复设置is_new，只更新需要变化的字段，key用其ID
        15. name只是NPC的普通属性，不是身份标识，身份标识永远是npcId
        16. 【属性系统重要】attributes字段只需要返回发生变化的属性！引擎会保留所有原有属性不变。例如主角有生命值和金币两个属性，如果只有生命值从100变成90，你应该返回{"生命值": 90}，引擎会保留金币原值不变。不需要返回所有属性。
        17. 当剧情有重大进展（每10-20轮）时，将summary_update设为true来触发自动总结
        18. 【严格禁止】禁止在attributes中创建新的属性名或修改属性类目！只能更新已存在的属性值。如果需要记录新的信息，请使用game.flag_set或其他方式。
        19. 【属性保护】引擎会忽略你返回的任何不在原有属性列表中的属性名。如果你尝试添加新属性，该属性将被丢弃。
    """.trimIndent()

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
