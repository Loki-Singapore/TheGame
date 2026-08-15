package com.textgame.data.remote.ai

import com.google.gson.Gson
import com.google.gson.JsonParser
import com.textgame.data.audio.BgmTrack
import com.textgame.domain.model.AIResponse
import com.textgame.domain.model.AttributeCategory
import com.textgame.domain.model.AttributeType
import com.textgame.domain.model.BackgroundSetting
import com.textgame.domain.model.GameState
import com.textgame.domain.model.NPC
import com.textgame.domain.model.Protagonist
import com.textgame.domain.model.StreamingChunk
import com.textgame.domain.model.Summary
import com.textgame.domain.model.WorldSetting
import com.textgame.domain.model.TokenUsage
import com.textgame.i18n.Lang
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.io.BufferedReader
import java.io.InputStreamReader

class AIService(
    private val apiService: DeepSeekApiService,
    private val streamingApiService: DeepSeekApiService,
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
     * 统一组装普通对话与流式对话的 messages，保证两条路径的 prompt 字节级一致。
     *
     * 缓存命中思路（DeepSeek 上下文缓存按"输入前缀"匹配）：
     * 把越稳定的内容放得越靠前，每轮必变的内容放到最后。
     * 1. system：静态规则 + 世界观 + 背景设定 + 输出协议（整局不变）
     * 2. dialogueHistory：对话历史（纯追加，旧前缀保持不变，命中率最高）
     * 3. worldRules：世界观细则（偶尔追加或原地修订，不如对话历史稳定）
     * 4. gameState：状态块内部同样按"稳定在前、变化在后"排列
     * 5. directorDirective：导演指令槽位永远存在（空指令用固定占位文案），
     *    避免"有时有 system、有时没有"导致消息结构漂移
     * 6. userInput：玩家输入 + 轮次（每轮必变，放在最后）
     */
    private fun buildDialogueMessages(
        worldSetting: WorldSetting,
        backgroundSetting: BackgroundSetting,
        summary: Summary?,
        postSummaryDialogues: List<String>,
        protagonist: Protagonist,
        npcs: List<NPC>,
        gameState: GameState,
        userInput: String,
        directorDirective: String?
    ): List<ChatMessage> {
        val systemPrompt = buildSystemPrompt(worldSetting, backgroundSetting)
        val worldRulesPrompt = buildWorldRulesPrompt(worldSetting.worldRules)
        val dialogueHistoryPrompt = buildDialogueHistoryPrompt(summary, postSummaryDialogues)
        val gameStatePrompt = buildGameStatePrompt(
            protagonist, npcs, gameState,
            worldSetting.attributeCategories, backgroundSetting.majorPlotThreads
        )
        val userPrompt = buildUserPrompt(userInput, gameState.turnCount)
        // 空指令也保留一个内容恒定的 system 槽位，让相邻轮次的消息数量与角色序列不变。
        val directivePrompt = directorDirective?.takeIf { it.isNotBlank() }
            ?: Lang.text("【Director Directive】None", "【导演指令】无")

        return buildList {
            add(ChatMessage(role = "system", content = systemPrompt))
            add(ChatMessage(role = "user", content = dialogueHistoryPrompt))
            add(ChatMessage(role = "user", content = worldRulesPrompt))
            add(ChatMessage(role = "user", content = gameStatePrompt))
            add(ChatMessage(role = "system", content = directivePrompt))
            add(ChatMessage(role = "user", content = userPrompt))
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
        postSummaryDialogues: List<String>,
        protagonist: Protagonist,
        npcs: List<NPC>,
        gameState: GameState,
        userInput: String,
        directorDirective: String? = null
    ): AIResponse {
        val messages = buildDialogueMessages(
            worldSetting = worldSetting,
            backgroundSetting = backgroundSetting,
            summary = summary,
            postSummaryDialogues = postSummaryDialogues,
            protagonist = protagonist,
            npcs = npcs,
            gameState = gameState,
            userInput = userInput,
            directorDirective = directorDirective
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
                        totalTokens = it.totalTokens,
                        promptCacheHitTokens = it.promptCacheHitTokens,
                        promptCacheMissTokens = it.promptCacheMissTokens
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
                    totalTokens = it.totalTokens,
                    promptCacheHitTokens = it.promptCacheHitTokens,
                    promptCacheMissTokens = it.promptCacheMissTokens
                )
            }
        )
    }

    fun streamDialogueResponse(
        worldSetting: WorldSetting,
        backgroundSetting: BackgroundSetting,
        summary: Summary?,
        postSummaryDialogues: List<String>,
        protagonist: Protagonist,
        npcs: List<NPC>,
        gameState: GameState,
        userInput: String,
        directorDirective: String? = null
    ): Flow<StreamingChunk> = flow {
        val messages = buildDialogueMessages(
            worldSetting = worldSetting,
            backgroundSetting = backgroundSetting,
            summary = summary,
            postSummaryDialogues = postSummaryDialogues,
            protagonist = protagonist,
            npcs = npcs,
            gameState = gameState,
            userInput = userInput,
            directorDirective = directorDirective
        )

        val request = buildDialogueRequest(messages, useJsonFormat = false).copy(
            stream = true,
            streamOptions = StreamOptions(includeUsage = true)
        )
        val call = streamingApiService.createChatCompletionStream(request)

        try {
            val response = call.execute()
            if (!response.isSuccessful) {
                emit(StreamingChunk.Error("HTTP ${response.code()}: ${response.message()}"))
                return@flow
            }

            val body = response.body() ?: run {
                emit(StreamingChunk.Error("Empty response body"))
                return@flow
            }

            var fullContent = StringBuilder()
            var lastNarrativeLen = 0
            var lastDialogueLen = 0
            var capturedUsage: TokenUsage? = null

            val jsonParser = JsonStreamingParser()

            val reader = BufferedReader(InputStreamReader(body.byteStream(), Charsets.UTF_8))
            var line: String?

            try {
                while (true) {
                    line = reader.readLine()
                    if (line == null) break

                    val trimmed = line.trim()
                    if (trimmed.isEmpty()) continue

                    val data = if (trimmed.startsWith("data:")) {
                        trimmed.removePrefix("data:").trim()
                    } else {
                        continue
                    }

                    if (data == "[DONE]") break
                    if (data.isEmpty()) continue

                    try {
                        val streamResponse = gson.fromJson(data, ChatCompletionStreamResponse::class.java)
                        val delta = streamResponse.choices.firstOrNull()?.delta?.content ?: ""

                        // 流式响应中 usage 通常出现在最后一个 chunk（choices 为空数组时），
                        // 仅当开启了 stream_options.include_usage 时才会下发
                        streamResponse.usage?.let { usage ->
                            capturedUsage = TokenUsage(
                                promptTokens = usage.promptTokens,
                                completionTokens = usage.completionTokens,
                                totalTokens = usage.totalTokens,
                                promptCacheHitTokens = usage.promptCacheHitTokens,
                                promptCacheMissTokens = usage.promptCacheMissTokens
                            )
                        }

                        if (delta.isNotEmpty()) {
                            fullContent.append(delta)

                            for (c in delta) {
                                jsonParser.processChar(c)
                            }

                            val narrativeResult = jsonParser.getFieldValue("narrative")
                            val narrativeCurrentLen = narrativeResult.length
                            if (narrativeCurrentLen > lastNarrativeLen) {
                                val deltaStr = narrativeResult.substring(lastNarrativeLen)
                                lastNarrativeLen = narrativeCurrentLen
                                emit(StreamingChunk.NarrativeDelta(deltaStr))
                            }

                            val dialogueResult = jsonParser.getFieldValue("dialogue")
                            val dialogueCurrentLen = dialogueResult.length
                            if (dialogueCurrentLen > lastDialogueLen) {
                                val deltaStr = dialogueResult.substring(lastDialogueLen)
                                lastDialogueLen = dialogueCurrentLen
                                emit(StreamingChunk.DialogueDelta(deltaStr))
                            }
                        }

                        if (streamResponse.choices.firstOrNull()?.finishReason != null) {
                            break
                        }
                    } catch (_: Exception) {
                    }
                }

                val finalContent = fullContent.toString()
                val aiResponse = parseAIResponse(finalContent).copy(tokenUsage = capturedUsage)
                emit(StreamingChunk.Complete(aiResponse))
            } finally {
                try {
                    reader.close()
                } catch (_: Exception) {}
            }
        } catch (e: Exception) {
            emit(StreamingChunk.Error(e.message ?: "Stream error"))
        } finally {
            if (!call.isCanceled) {
                call.cancel()
            }
        }
    }.flowOn(Dispatchers.IO)

    private class JsonStreamingParser {
        private var state = State.INITIAL
        private var currentField = StringBuilder()
        private val fieldValues = mutableMapOf<String, StringBuilder>()
        private var inEscape = false
        private var targetField: String? = null
        private var nestingDepth = 0
        private var skipInString = false
        private var skipInEscape = false

        private enum class State {
            INITIAL,
            AFTER_BRACE,
            IN_FIELD_NAME,
            AFTER_FIELD_NAME,
            AFTER_COLON,
            IN_STRING_VALUE,
            AFTER_STRING_VALUE,
            SKIPPING_VALUE
        }

        fun processChar(c: Char) {
            when (state) {
                State.INITIAL -> {
                    if (c == '{') {
                        state = State.AFTER_BRACE
                    }
                }
                State.AFTER_BRACE -> {
                    if (c == '"') {
                        state = State.IN_FIELD_NAME
                        currentField.clear()
                    }
                }
                State.IN_FIELD_NAME -> {
                    if (c == '"') {
                        state = State.AFTER_FIELD_NAME
                        val fieldName = currentField.toString()
                        if (fieldName == "narrative" || fieldName == "dialogue") {
                            targetField = fieldName
                            if (!fieldValues.containsKey(fieldName)) {
                                fieldValues[fieldName] = StringBuilder()
                            }
                        } else {
                            targetField = null
                        }
                    } else {
                        currentField.append(c)
                    }
                }
                State.AFTER_FIELD_NAME -> {
                    if (c == ':') {
                        state = State.AFTER_COLON
                    }
                }
                State.AFTER_COLON -> {
                    when {
                        c == '"' -> {
                            state = State.IN_STRING_VALUE
                            inEscape = false
                        }
                        c == '{' || c == '[' -> {
                            state = State.SKIPPING_VALUE
                            nestingDepth = 1
                            skipInString = false
                            skipInEscape = false
                        }
                        c.isDigit() || c == '-' || c == 't' || c == 'f' || c == 'n' -> {
                            state = State.SKIPPING_VALUE
                            nestingDepth = 0
                            skipInString = false
                        }
                    }
                }
                State.IN_STRING_VALUE -> {
                    if (targetField != null) {
                        val valueBuilder = fieldValues[targetField]
                        if (valueBuilder != null) {
                            if (inEscape) {
                                when (c) {
                                    'n' -> valueBuilder.append('\n')
                                    't' -> valueBuilder.append('\t')
                                    'r' -> valueBuilder.append('\r')
                                    '"' -> valueBuilder.append('"')
                                    '\\' -> valueBuilder.append('\\')
                                    '/' -> valueBuilder.append('/')
                                    else -> valueBuilder.append(c)
                                }
                                inEscape = false
                            } else if (c == '\\') {
                                inEscape = true
                            } else if (c == '"') {
                                state = State.AFTER_STRING_VALUE
                                targetField = null
                            } else {
                                valueBuilder.append(c)
                            }
                        }
                    } else {
                        if (inEscape) {
                            inEscape = false
                        } else if (c == '\\') {
                            inEscape = true
                        } else if (c == '"') {
                            state = State.AFTER_STRING_VALUE
                        }
                    }
                }
                State.AFTER_STRING_VALUE -> {
                    if (c == ',') {
                        state = State.AFTER_BRACE
                    } else if (c == '}') {
                        state = State.INITIAL
                    }
                }
                State.SKIPPING_VALUE -> {
                    if (skipInString) {
                        if (skipInEscape) {
                            skipInEscape = false
                        } else if (c == '\\') {
                            skipInEscape = true
                        } else if (c == '"') {
                            skipInString = false
                        }
                    } else {
                        when (c) {
                            '"' -> skipInString = true
                            '{', '[' -> nestingDepth++
                            '}', ']' -> {
                                nestingDepth--
                                if (nestingDepth <= 0) {
                                    state = State.AFTER_STRING_VALUE
                                }
                            }
                            ',', '}' -> {
                                if (nestingDepth == 0) {
                                    state = if (c == ',') State.AFTER_BRACE else State.INITIAL
                                }
                            }
                        }
                    }
                }
            }
        }

        fun getFieldValue(fieldName: String): String {
            return fieldValues[fieldName]?.toString() ?: ""
        }
    }

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
            ChatMessage(
                role = "system",
                content = Lang.text(
                    "You are a game story summarizer. Summarize the recent game progress in English.",
                    "你是一个游戏剧情总结助手，负责总结近期游戏进展。"
                )
            ),
            ChatMessage(role = "user", content = prompt)
        )

        val request = buildSummaryRequest(messages)

        val response = apiService.createChatCompletion(request)
        val content = response.choices.firstOrNull()?.message?.content ?: ""
        return parseSummaryResponse(content, gameState)
    }

    /**
     * 根据当前游戏场景生成 AI 图片提示词。
     * @param style 风格：REALISTIC（高真实感）或 ANIME（动漫化）
     */
    suspend fun generateImagePrompt(
        worldSetting: WorldSetting?,
        protagonist: Protagonist?,
        npcs: List<NPC>,
        gameState: GameState?,
        sceneNarrative: String,
        style: ImagePromptStyle
    ): String {
        val systemPrompt = buildImagePromptSystemPrompt(style)
        val userPrompt = buildImagePromptUserPrompt(
            worldSetting, protagonist, npcs, gameState, sceneNarrative
        )

        val messages = listOf(
            ChatMessage(role = "system", content = systemPrompt),
            ChatMessage(role = "user", content = userPrompt)
        )

        val request = buildDialogueRequest(messages, useJsonFormat = false)
        val response = apiService.createChatCompletion(request)
        val content = response.choices.firstOrNull()?.message?.content ?: ""
        // 去除可能的代码块标记和首尾引号
        return content
            .removePrefix("```").removePrefix("json")
            .removeSuffix("```")
            .trim()
            .removeSurrounding("\"")
            .trim()
    }

    private fun buildImagePromptSystemPrompt(style: ImagePromptStyle): String = buildString {
        appendLine("你是一位资深 AI 绘画提示词工程师，专精于把文字冒险游戏的场景叙述转化为工程化、结构化的文生图提示词。")
        appendLine("你输出的不是文学描写，而是给文生图模型执行的指令——简洁、具体、可执行。")
        if (Lang.isEnglish()) {
            appendLine()
            appendLine("【LANGUAGE REQUIREMENT - MUST FOLLOW】")
            appendLine("The player is using the English version of the app. Output the entire image prompt in English, including every block tag and every phrase.")
        }
        appendLine()
        appendLine("【工作流程 - 仅内部思考，绝不输出】")
        appendLine("1. 场景解读：主体是谁/什么？情绪基调（紧张/温馨/肃杀/怅惘/壮阔/诡异）？戏剧高潮？画面焦点？")
        appendLine("2. 视觉转译：把文学性描写翻译成可视化具体元素——人物姿态、表情、服装细节、环境物体、空间关系、材质特征。")
        appendLine("3. 参数推演：根据场景内容【推导】镜头、光影、色彩参数，而不是套用万能模板（夜景与日景光源不同；室内烛光与室外阳光色温不同；战斗与对话景别不同；雨林与沙漠空气质感不同）。")
        appendLine("4. 风格收敛：在指定风格下挑选最贴合情绪的具体亚风格与技法，贯穿始终。")
        appendLine()
        appendLine("【输出格式 - 必须严格遵守】")
        appendLine(Lang.text(
            "Output the image prompt in English, structured in the blocks below. Start each block with a [tag] and use noun phrases and short clauses separated by commas; never write prose paragraphs:",
            "输出中文提示词，按以下分块结构输出，每块以 [标签] 开头，块内用名词短语和短句，逗号或顿号分隔，禁止整段散文叙事："
        ))
        appendLine()
        appendLine("[主体] 人物/物体的外貌、姿态、表情、穿着、材质")
        appendLine("[环境] 场景空间、物体陈设、空间纵深")
        appendLine("[构图] 景别、机位、视角、画面分割、视觉引导")
        appendLine("[光影] 光源类型、方向、强度、色温、阴影特征")
        appendLine("[色彩] 主色调、对比关系、饱和度倾向")
        appendLine("[材质细节] 关键质感（皮肤/织物/金属/木材/玻璃等）")
        appendLine("[风格] 具体亚风格名称、线条/上色/光影技法")
        appendLine("[负面] 文字、水印、签名、低质量、变形、多余肢体")
        appendLine()
        appendLine("要求：")
        appendLine("- 每个标签块都必须输出，无内容的填\"无\"。")
        appendLine("- 块内用名词短语和短句，禁止\"美丽\"\"壮观\"等空泛形容词，禁止抒情叙事、禁止连词成段的小说式描写。")
        appendLine("- 每个描述都要具体到可被执行，宁可详尽也不要笼统。")
        appendLine("- 不要输出思考过程、解释、前后缀、markdown 标记或引号，只输出分块提示词本身。")
        appendLine("- 不要在画面中出现任何文字、水印、签名。")
        appendLine()
        when (style) {
            ImagePromptStyle.REALISTIC -> {
                appendLine("【当前风格：真实感（Photorealistic）】")
                appendLine("真实感 ≠ 高清、≠ 8K、≠ 锐利。真实感 = 像一张真实存在的照片，让人无法分辨真伪。")
                appendLine("在 [光影]、[材质细节]、[风格] 块中，根据场景智能选取最契合的几项（不要全堆上）：")
                appendLine("- 镜头参数：具体焦段（35mm/50mm/85mm 定焦）、光圈景深、快门速度（运动模糊或凝固）")
                appendLine("- 相机特征：白平衡偏移、轻微曝光偏差、镜头眩光、边缘色散、暗角")
                appendLine("- 光线：可识别光源（太阳/月亮/烛火/灯泡/霓虹/窗漫射光）、方向明确、软硬阴影并存、环境反射光、次表面散射（皮肤/玉石/树叶）")
                appendLine("- 材质：皮肤毛孔与绒毛、织物纹理褶皱、金属氧化划痕、木材纹路裂痕、玻璃指纹反光、墙面斑驳")
                appendLine("- 瑕疵：姿态不完美对称、表情瞬时捕捉、环境杂乱细节（灰尘、杂物）、轻微噪点/胶片颗粒")
                appendLine("- 瞬间感：抓拍而非摆拍，有\"此刻正在发生\"的临场感")
                appendLine()
                appendLine("【禁用词汇】8K、超高清、CG 渲染、3D 渲染、数码艺术、壁纸级、超写实（hyperrealistic）、极致细节——这些词会让画面失去照片感，显得像电脑合成。")
            }
            ImagePromptStyle.ANIME -> {
                appendLine("【当前风格：动漫化（Anime）】")
                appendLine("动漫化 ≠ 堆叠\"动漫风格\"\"赛璐珞\"\"色彩鲜艳\"。动漫化 = 用动画/插画语言重新组织场景，核心在构图设计与风格化处理。")
                appendLine()
                appendLine("在 [构图] 块主动设计（不要默认平铺）：")
                appendLine("- 景别：特写/近景/中景/全景/远景（依情绪选取）")
                appendLine("- 机位：低角度仰拍/高角度俯拍/平视/倾斜构图（荷兰角）")
                appendLine("- 视觉引导：引导线、框架、明暗对比、色彩对比")
                appendLine("- 画面分割：黄金分割、三分法、对角线、对称/非对称平衡、留白")
                appendLine("- 前中后景层次：前景遮挡、中景主体、背景氛围")
                appendLine()
                appendLine("在 [风格] 块择一亚风格贯穿始终（举例参考，可自行推导更契合的）：")
                appendLine("- 新海诚式：极致光影、天空云层细腻、逆光剪影、色彩饱和通透")
                appendLine("- 京阿尼式：柔和高光过渡、细腻神态、温暖光晕、生活质感")
                appendLine("- 吉卜力式：手绘质感、饱和沉稳色块、自然厚重、怀旧色调")
                appendLine("- 物哀/水墨式：低饱和、留白、笔触感、东方意境")
                appendLine("- 赛博朋克动画式：高对比霓虹、夜雨反射、补色冲突、机械细节")
                appendLine("- 少女漫式：柔和粉调、装饰性光斑、线条细腻、情绪化特写")
                appendLine()
                appendLine("在 [风格] 块必须明确：线条风格（粗/细/有无线条/线条颜色）、上色方式（赛璐珞平涂/渐变/水彩/厚涂）、光影处理（二分光影/柔和漫射/逆光剪影）。")
                appendLine("在 [色彩] 块设计情绪指向的色彩方案：主色、辅色、点缀色、冷暖对比策略，说明为什么用这套色传达什么情绪。")
            }
        }
    }

    private fun buildImagePromptUserPrompt(
        worldSetting: WorldSetting?,
        protagonist: Protagonist?,
        npcs: List<NPC>,
        gameState: GameState?,
        sceneNarrative: String
    ): String = buildString {
        if (worldSetting != null) {
            appendLine("【世界设定】")
            appendLine("世界名称：${worldSetting.name}")
            appendLine("世界类型：${worldSetting.worldType}")
            if (worldSetting.description.isNotBlank()) {
                appendLine("描述：${worldSetting.description}")
            }
            if (worldSetting.timeSetting.isNotBlank()) {
                appendLine("时间设定：${worldSetting.timeSetting}")
            }
            appendLine()
        }
        if (gameState != null) {
            appendLine("【当前场景】${gameState.currentScene}")
            appendLine()
        }
        if (protagonist != null) {
            appendLine("【主角】")
            appendLine("姓名：${protagonist.name}")
            if (protagonist.location.isNotBlank()) appendLine("位置：${protagonist.location}")
            appendLine()
        }
        if (npcs.isNotEmpty()) {
            appendLine("【在场角色】")
            npcs.forEach { npc ->
                appendLine("- ${npc.name}（${npc.role}）")
                if (npc.appearance.isNotBlank()) appendLine("  外貌：${npc.appearance}")
                if (npc.mood.isNotBlank()) appendLine("  当前情绪：${npc.mood}")
            }
            appendLine()
        }
        appendLine("【本回合场景叙述】")
        appendLine(sceneNarrative.ifBlank { gameState?.currentScene ?: "无" })
        appendLine()
        appendLine(Lang.text(
            "Based on the information above, think through the workflow in the system prompt first, then output the engineering-grade image prompt in English using the eight blocks [Subject][Environment][Composition][Lighting][Color][Material Details][Style][Negative]. Use noun phrases and short clauses inside each block; no prose narration. Derive the effect parameters from the specific content of this scene; do not reuse a fixed set of modifiers.",
            "请基于以上信息，先按系统提示词的工作流程内部思考，然后按 [主体][环境][构图][光影][色彩][材质细节][风格][负面] 八个分块输出工程化中文生图提示词。块内用名词短语和短句，禁止散文叙事。效果参数必须从本场景具体内容推导，不要套用固定修饰词。"
        ))
    }

    suspend fun generateWorldFromPrompt(userPrompt: String): GeneratedWorldResult {
        val systemPrompt = """
            你是一个文字冒险游戏的世界生成助手。用户用一句话描述想要的游戏世界，你需要生成完整的游戏设定。
            ${if (Lang.isEnglish()) "【LANGUAGE REQUIREMENT - MUST FOLLOW】\nThe player is using the English version of the app. ALL generated content in the JSON — gameName, protagonistName, worldName, worldType, worldDescription, timeSetting, locationSetting, socialStructure, specialRules, lore, protagonistBackground, worldHistory, attribute names/descriptions/enumOptions/column names, and NPC names/roles/personality/backstory/mood/appearance — MUST be written in English. Keep the JSON field names exactly as shown below." else ""}
            你必须以纯JSON格式回复，不要有任何额外的文字说明或markdown标记。

            JSON格式如下：
            {
              "gameName": "游戏名称",
              "protagonistName": "主角名字",
              "worldName": "世界名称",
              "worldType": "世界类型（如：奇幻/科幻/现代/末日/武侠/都市/历史等）",
              "worldDescription": "世界的详细描述，100-300字",
              "timeSetting": "时间设定，必须紧扣世界题材并有题材特色。要包含纪年/年代+季节或更细的时间单位，便于后续剧情中精确推进时间。示例：奇幻世界用'第三纪元 1247 年，深秋'；科幻世界用'星历 3042 年，地球历 7 月'；武侠世界用'大明永乐三年，春末'；末日世界用'灾变后第 187 天'；克苏鲁世界用'1923 年 10 月，波士顿'；都市世界用'2024 年 6 月，初夏'；历史世界用'公元前 221 年，秦统一六国之年'。不要使用笼统的'古代'或'未来'等模糊表述。",
              "locationSetting": "起始地点",
              "socialStructure": "社会结构简述",
              "specialRules": ["特殊规则1", "特殊规则2"],
              "lore": "世界观历史和传说",
              "protagonistBackground": "主角的详细背景故事",
              "worldHistory": "世界历史",
              "attributes": [
                {"name": "生命值", "type": "NUMERIC", "minValue": 0, "maxValue": 100, "defaultValue": 80, "description": "生命值归零即死亡，受伤时减少，休息或治疗时恢复"},
                {"name": "理智值", "type": "NUMERIC", "minValue": 0, "maxValue": 100, "defaultValue": 75, "description": "目睹恐怖事物或经历创伤会下降，归零则陷入疯狂，无法做出理性判断"},
                {"name": "是否被通缉", "type": "BOOLEAN", "defaultValue": false, "description": "若为true，进入城镇时可能遭遇守卫追捕，需乔装或潜行"},
                {"name": "阵营", "type": "ENUM", "enumOptions": ["守序善良", "中立善良", "混乱善良", "守序中立", "绝对中立", "混乱中立", "守序邪恶", "中立邪恶", "混乱邪恶"], "defaultValue": "中立善良", "description": "影响NPC对主角的态度及部分剧情走向的选择"},
                {"name": "身份", "type": "TEXT", "defaultValue": "落魄贵族", "description": "主角的社会身份，会影响可对话人群和剧情分支"},
                {"name": "技能表", "type": "TABLE", "columns": [{"name": "技能名", "type": "TEXT"}, {"name": "等级", "type": "NUMERIC"}, {"name": "类型", "type": "ENUM", "enumOptions": ["主动", "被动", "buff"]}], "defaultValue": [{"技能名": "基础剑术", "等级": 1, "类型": "主动"}], "description": "主角已掌握的技能清单，随修炼/学习增删行，每行的等级、类型可随剧情变化"}
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
            4. 属性必须紧扣世界题材，富有设计感和可玩性。属性是游戏机制的核心，每一个属性都应该能在剧情中触发实际效果。具体要求：
               - 不要只生成"生命值/金币"这类通用属性，必须根据题材设计有题材特色的属性（如：克苏鲁题材生成"理智值""禁忌知识"；赛博朋克生成"义体改造度""企业声望"；武侠生成"内力修为""门派声望"；末日生存生成"辐射剂量""幸存者信任度"等）
               - 每个属性的description必须明确说明该属性如何影响游戏：何时增减、归零或极值时的后果、是否解锁特殊剧情或对话选项
               - 属性之间应该互相关联或互相制约，形成策略选择（如高"声望"能开启某些任务但会引来仇家；高"禁忌知识"提升能力但消耗"理智值"）
               - 至少包含一个会随剧情持续变化、可触发分支的属性（如声望、信任度、堕落值等）
               - 至少包含一个能体现角色身份/立场的ENUM或TEXT属性
            5. 属性类型必须多样化，不要全部使用NUMERIC：数值类用NUMERIC（可量化数据）；是否类状态用BOOLEAN（如是否中毒、是否被通缉等）；有固定取值范围的用ENUM并必须提供enumOptions（如阵营、稀有度、阶级等）；自由文本类用TEXT（如职业、称号、身份等）；结构化清单类用TABLE（如技能列表、装备栏、任务清单、人际关系网络等，每行是一条记录，由columns定义字段）
            6. 每种类型的字段要求：NUMERIC必须包含minValue和maxValue且defaultValue在范围内；BOOLEAN的defaultValue必须是true/false；ENUM必须提供enumOptions数组且defaultValue必须是其中之一；TEXT的defaultValue为字符串；TABLE必须提供columns数组（每列含name和type，列type只能是NUMERIC/BOOLEAN/ENUM/TEXT，ENUM列需提供enumOptions），defaultValue为一组行对象数组（如[{"技能名":"基础剑术","等级":1}]），每行的字段名需与columns的name对应
            7. 至少生成5-8个属性，且至少包含三种不同的类型
            8. 你的整个回复只能是JSON
        """.trimIndent()

        val messages = listOf(
            ChatMessage(role = "system", content = systemPrompt),
            ChatMessage(
                role = "user",
                content = Lang.text(
                    "I want a game world like this: $userPrompt",
                    "我想要一个这样的游戏世界：$userPrompt"
                )
            )
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
        if (Lang.isEnglish()) {
            appendLine("【LANGUAGE REQUIREMENT - MUST FOLLOW】")
            appendLine("The player is using the English version of the app. ALL player-facing creative content in your response — narrative, dialogue, choices, new NPC names/roles/briefing/personality/backstory/mood/appearance, new attribute names/descriptions/enumOptions/column names, world rule content, and the bgm keyword — MUST be written in English. Keep all JSON field names exactly as shown below.")
            appendLine()
        }
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
        appendLine("        \"hidden_agenda\": \"该NPC玩家不可见的隐藏动机更新（仅当动机有变化或首次赋予时返回，返回完整的新内容而非增量）\",")
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
        appendLine("        \"hidden_agenda\": \"该NPC玩家不可见的隐藏动机（重要NPC必须有；跑龙套的可不填）\",")
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
        appendLine("      ],")
        appendLine("      \"attribute_categories\": [")
        appendLine("        {\"name\": \"新属性名\", \"type\": \"NUMERIC\", \"minValue\": 0, \"maxValue\": 100, \"defaultValue\": 50, \"description\": \"该属性如何影响游戏\"},")
        appendLine("        {\"name\": \"新表格属性名\", \"type\": \"TABLE\", \"columns\": [{\"name\": \"列名1\", \"type\": \"TEXT\"}, {\"name\": \"列名2\", \"type\": \"NUMERIC\"}], \"defaultValue\": [{\"列名1\": \"示例\", \"列名2\": 1}], \"description\": \"该清单如何影响游戏\"},")
        appendLine("        {\"name\": \"已有属性名\", \"maxValue\": 200, \"description\": \"只写需要修改的字段，未写出的字段保持不变\"},")
        appendLine("        {\"name\": \"要删除的属性名\", \"is_deleted\": true}")
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
        appendLine("19. 进度总结由引擎按约每30轮一次的间隔自动触发。summary_update字段保留用于协议兼容：当本轮剧情出现重大进展时仍可设为true，但最终是否生成总结由引擎决定。")
        appendLine("20. 【属性类目维护 - 高级操作，谨慎使用】你可以通过 state_changes.game.attribute_categories 增删改属性类目，让属性体系随剧情演进：")
        appendLine("    - 新增：当剧情引入了全新的、需要长期量化追踪的机制时（如主角解锁'腐化度'系统、获得'灵力'修炼体系、被加入'通缉等级'，或获得一个需要逐条记录的'技能表'/'装备栏'/'任务清单'），新增类目。必须提供：name、type、defaultValue，以及对应类型所需的约束（NUMERIC需要minValue/maxValue；ENUM需要enumOptions；TABLE需要columns数组，每列含name和type，列type只能是NUMERIC/BOOLEAN/ENUM/TEXT，ENUM列需提供enumOptions，TABLE的defaultValue是一组行对象数组）。description必须明确说明该属性如何影响游戏。引擎会自动把defaultValue应用到主角身上。")
        appendLine("    - 修改：当某属性的取值范围或语义需要随剧情调整时（如主角升级导致生命值上限提高、剧情揭示了新的枚举选项、描述需要补充，或为TABLE属性增删/调整列定义），按name匹配已有类目，只写需要修改的字段，未写出的字段保持不变。type不可更改（如需更换类型应先删除再新增）。修改TABLE列时，columns按列name匹配：已存在的列按提供的字段部分更新，新列名追加，未提及的列保留。")
        appendLine("    - 删除：当某属性永久失去意义时（如临时机制结束、属性被剧情事件彻底移除），按name匹配并设置\"is_deleted\": true。重要NPC属性、长期追踪的属性、有剧情意义的属性绝不能删除。删除时引擎会同步从主角和所有NPC的attributes中移除该键。")
        appendLine("    - 谨慎原则：每次修改属性类目都需要充分的剧情理由，不要为了方便而频繁增删，这会破坏游戏稳定性。如果只是想记录一次性事件，使用game.flag_set；如果只是想记录长期背景信息，使用game.world_rules。")
        appendLine("    - 【TABLE属性更新】TABLE类属性的值是一组行（List<Map>）。在 protagonist.attributes 或 npc.attributes 中更新TABLE属性时，必须返回完整的新表（所有行），引擎会整体替换，不做增量。增删行或修改某行字段都要返回完整的新表。")
        appendLine("21. protagonist.attributes 和 npc.attributes 只接受当前属性类目列表中存在的属性名（包括本轮新增的）。引擎会忽略任何不在类目列表中的属性名。")
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
        appendLine("【隐藏动机与导演指令 - 重要，决定游戏是否好玩】")
        appendLine("23. 每个NPC都有一个玩家不可见的 hidden_agenda（隐藏动机）。这是该NPC背着主角想要达成的事、保守的秘密、或对主角的真实态度。你作为GM可见，玩家不可见。")
        appendLine("24. 重要NPC（主线角色、长期互动角色）必须有 hidden_agenda。新NPC首次出场时，在 state_changes.npc.<id>.hidden_agenda 中赋予其一个具体、可执行的动机（不要写'暂无'）。跑龙套的可不填。")
        appendLine("25. hidden_agenda 是NPC自主行动的内在驱动。每轮根据其性格和动机，让NPC做出符合动机的言行——玩家会感觉这个人有自己的生活和算计，不是只为主角而存在。")
        appendLine("26. 绝不能在 narrative 或 dialogue 中直接告诉玩家NPC的 hidden_agenda。只能通过NPC的言行、表情、反常举动让玩家自己察觉。揭穿时机由你掌握，要克制。")
        appendLine("27. 仅当某NPC的动机在本轮发生了实质性变化时，才在 state_changes.npc.<id>.hidden_agenda 中返回完整的新动机内容（完整替换，不是增量；例如从'复仇'变成'动摇'变成'和解'）。动机无变化时必须省略该字段——省略即保留原值，不要为填充而重复返回当前动机。")
        appendLine("28. 【导演指令】本轮你可能会在玩家输入之前收到一条 system 消息，内容是导演给你的强制戏剧指令（例如'让某NPC撒个谎''引入时间压力''埋一个伏笔'）。你必须把该指令编织进本轮 narrative 中，但绝不能在回复中提及该指令的存在、引用其原文、或暴露'有指令'这件事。")
        appendLine("29. 导演指令的优先级高于'顺从玩家'。玩家要求A，但导演指令要求B时，让世界以B的方式回应A，而不是忽略B满足A。这就是游戏感的来源——玩家不能完全掌控剧情。")
        appendLine("30. 若本轮导演指令为无指令占位（即没有强制戏剧指令），正常推进剧情即可，但仍要让NPC的 hidden_agenda 在其言行中有所体现。")
        appendLine()
        appendLine("【背景音乐BGM点播】")
        appendLine("你可以根据当前剧情氛围点播背景音乐。可用的BGM关键词如下：")
        val bgmKeywords = BgmTrack.availableKeywords(Lang.isEnglish())
        bgmKeywords.forEach { keyword ->
            appendLine("- $keyword")
        }
        appendLine()
        appendLine("BGM点播规则：")
        appendLine("1. 当剧情氛围发生明显变化时（如进入战斗、发现危险、获得胜利、场景切换等），选择最匹配的BGM关键词填入bgm字段")
        appendLine("2. 如果当前氛围没有明显变化，不需要切换BGM，就不要填bgm字段（省略该字段）")
        appendLine("3. 必须从上面的列表中选择关键词，不能自创关键词")
        appendLine("4. 季节类BGM（春夏、秋天、冬天）在场景切换到对应季节环境时使用")
        appendLine("5. 情绪类BGM（战斗、危险临近、胜利、爱情、恐惧）在剧情氛围匹配时使用")
        appendLine()
        appendLine("【时间推进规则】")
        appendLine("1. 当剧情中时间发生明显变化时（如：场景切换伴随时间流逝、时间跳跃、昼夜变化、季节更替、跨越数日/数月/数年、长途旅行后等），必须在 narrative 中明确写出当前的具体时间，让玩家清晰感知到时间流逝到了何时")
        appendLine("2. 时间表述必须与世界设定中的时间设定风格保持一致，体现世界观的特色（如奇幻世界用'第三纪元1247年深秋，黄昏时分'；科幻世界用'星历3042年7月15日14:00'；武侠世界用'大明永乐三年春末，午时三刻'；末日世界用'灾变后第189天，黎明'；克苏鲁世界用'1923年10月，波士顿，雨夜'等）")
        appendLine("3. 时间应当比上一次出现的时间有所推进，体现时间的流逝感，不要原地踏步")
        appendLine("4. 时间应自然地融入 narrative 的场景描写中，可以放在叙述开头点明时间，或在环境描写中体现（如'晨光透过窗棂'、'子夜的钟声敲响'、'三日后，船队抵达...'等）")
        appendLine("5. 如果只是普通对话或短暂动作（如几句话的交谈、单次行动、一场战斗等），时间没有明显流逝，不需要在 narrative 中强调具体时间")
        appendLine("6. 首轮回复时，必须在 narrative 中设定故事的起始具体时间，作为时间推进的基点")
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
        appendLine("- 如需调整属性体系（增删改类目），使用 game.attribute_categories，但需谨慎并有充分剧情理由")
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
        postSummaryDialogues: List<String>
    ): String = buildString {
        val hasContent = (summary != null && summary.summaryText.isNotEmpty()) ||
            postSummaryDialogues.isNotEmpty()

        if (!hasContent) {
            appendLine("【对话历史】暂无")
            return@buildString
        }

        // 不再携带"总结前对话"：总结本身由最近对话完整提炼、自包含。
        // 历史块退化为"总结 + 总结后追加对话"，总结之间是纯追加前缀，缓存更稳定。
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

    /**
     * 格式化单条属性供 AI 阅读。
     * - 标量属性：单行展示 "  名称: 值  [类型:... 最小:... 最大:... - 描述]"
     * - TABLE 属性：先展示列定义与元信息，再逐行展示表格内容，便于 AI 理解结构。
     */
    private fun formatAttributeLine(
        indent: String,
        key: String,
        value: Any?,
        cat: AttributeCategory?
    ): String = buildString {
        if (cat?.type == AttributeType.TABLE) {
            appendLine("${indent}$key: [TABLE] 列=[${cat.columns.joinToString(", ") { it.name + ":" + it.type.name.lowercase() }}]" +
                if (cat.description.isNotBlank()) " - ${cat.description}" else "")
            val rows = extractTableRows(value)
            if (rows.isEmpty()) {
                append("${indent}  (空表)")
            } else {
                rows.forEachIndexed { i, row ->
                    val cells = cat.columns.joinToString(", ") { col ->
                        "${col.name}=${row[col.name] ?: ""}"
                    }
                    append("${indent}  #${i + 1}  $cells")
                    if (i < rows.size - 1) appendLine()
                }
            }
        } else {
            val meta = buildString {
                if (cat != null) {
                    append("类型:${cat.type.name.lowercase()}")
                    if (cat.minValue != null) append(" 最小:${cat.minValue}")
                    if (cat.maxValue != null) append(" 最大:${cat.maxValue}")
                    if (cat.description.isNotBlank()) append(" - ${cat.description}")
                }
            }
            if (meta.isNotEmpty()) {
                // 变量值放在行尾：键名和类型/范围/描述是稳定前缀，值才是每轮可能变化的尾部，
                // 这样属性值变化时只破坏本行末尾，不会把行内稳定的元信息一起拖成缓存未命中。
                append("$indent$key  [$meta]: $value")
            } else {
                append("$indent$key: $value")
            }
        }
    }

    /**
     * 把 TABLE 属性值（可能来自 Gson 反序列化为 List<Map<String, Any>>，
     * 也可能来自旧数据为 List<LinkedTreeMap>）统一成 List<Map<String, Any>>。
     */
    private fun extractTableRows(value: Any?): List<Map<String, Any>> {
        if (value == null) return emptyList()
        return when (value) {
            is List<*> -> value.mapNotNull { row ->
                when (row) {
                    is Map<*, *> -> row.entries.associate { (k, v) -> k.toString() to (v ?: "") }
                    else -> null
                }
            }
            is Map<*, *> -> listOf(value.entries.associate { (k, v) -> k.toString() to (v ?: "") })
            else -> emptyList()
        }
    }

    private fun buildGameStatePrompt(
        protagonist: Protagonist,
        npcs: List<NPC>,
        gameState: GameState,
        attributeCategories: List<AttributeCategory> = emptyList(),
        majorPlotThreads: List<String> = emptyList()
    ): String {
        // 确定性排序：NPC 按 npcId（未分配的排最后、同ID按数据库id），属性按 key，
        // 避免数据库返回顺序或 Map 迭代顺序漂移导致 prompt 出现无意义的文本差异。
        val orderedNpcs = npcs.sortedWith(
            compareBy<NPC> { it.npcId.ifBlank { "zzz" } }.thenBy { it.id }
        )

        return buildString {
            // 状态块内部同样按"稳定在前、变化在后"排列：
            // 主角身份/NPC基础设定/主要剧情线很少变，实时数值和场景每轮可能变。
            appendLine("【主角身份】")
            appendLine("姓名：${protagonist.name}")
            appendLine()

            if (majorPlotThreads.isNotEmpty()) {
                appendLine("【主要剧情线】")
                majorPlotThreads.forEach { appendLine("- $it") }
                appendLine()
            }

            if (orderedNpcs.isNotEmpty()) {
                appendLine("【在场NPC-基础设定】")
                orderedNpcs.forEach { npc ->
                    val displayId = npc.npcId.ifBlank { "未分配" }
                    appendLine("ID: ${displayId} | 名称: ${npc.name}（${npc.role}）")
                    if (npc.personality.isNotEmpty()) {
                        appendLine("  性格：${npc.personality}")
                    }
                    if (npc.appearance.isNotEmpty()) {
                        appendLine("  外貌：${npc.appearance}")
                    }
                    if (npc.backstory.isNotEmpty()) {
                        appendLine("  背景：${npc.backstory}")
                    }
                    // ponytail: 隐藏动机——AI作为GM可见，玩家不可见。空表示该NPC暂无隐藏动机，
                    // AI可在本轮通过 state_changes.npc.<id>.hidden_agenda 赋予。
                    if (npc.hiddenAgenda.isNotEmpty()) {
                        appendLine("  【玩家不可见】隐藏动机：${npc.hiddenAgenda}")
                    } else {
                        appendLine("  【玩家不可见】隐藏动机：（暂无，可在适当时机赋予该NPC一个玩家不知情的动机）")
                    }
                }
                appendLine()
            }

            appendLine("【当前实时状态】")
            appendLine("主角：")
            appendLine("  位置：${protagonist.location}")
            if (protagonist.attributes.isNotEmpty()) {
                appendLine("  属性：")
                protagonist.attributes.entries.sortedBy { it.key }.forEach { (key, value) ->
                    val cat = attributeCategories.find { it.name == key }
                    appendLine(formatAttributeLine("    ", key, value, cat))
                }
            }
            if (protagonist.inventory.isNotEmpty()) {
                appendLine("  物品：${protagonist.inventory.joinToString("、")}")
            }

            if (orderedNpcs.isNotEmpty()) {
                appendLine("NPC：")
                orderedNpcs.forEach { npc ->
                    val displayId = npc.npcId.ifBlank { "未分配" }
                    appendLine("  ID: ${displayId} | 名称: ${npc.name}")
                    if (npc.briefing.isNotEmpty()) {
                        appendLine("    简介：${npc.briefing}")
                    }
                    if (npc.awareness.isNotEmpty()) {
                        appendLine("    认知：${npc.awareness}")
                    }
                    appendLine("    情绪：${npc.mood}")
                    if (npc.attributes.isNotEmpty()) {
                        appendLine("    属性：")
                        npc.attributes.entries.sortedBy { it.key }.forEach { (key, value) ->
                            val cat = attributeCategories.find { it.name == key }
                            appendLine(formatAttributeLine("      ", key, value, cat))
                        }
                    }
                }
            }
            appendLine()

            appendLine("【当前场景】${gameState.currentScene}")
            // ponytail: turnCount 移到 userPrompt——每轮必然+1，留在 gameStatePrompt 会把整块拖下水。
        }
    }

    private fun buildUserPrompt(userInput: String, turnCount: Int): String = buildString {
        appendLine("【玩家输入】（轮次 $turnCount）")
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
                    try {
                        val obj = elem.asJsonObject
                        val typeStr = obj.get("type")?.asString ?: "NUMERIC"
                        // 兼容历史 prompt 中可能出现的 STRING，映射到 TEXT
                        val normalized = when (typeStr.uppercase()) {
                            "STRING" -> "TEXT"
                            else -> typeStr.uppercase()
                        }
                        val type = com.textgame.domain.model.AttributeType.values()
                            .firstOrNull { it.name == normalized }
                            ?: com.textgame.domain.model.AttributeType.TEXT
                        val defaultVal: Any = when (type) {
                            com.textgame.domain.model.AttributeType.NUMERIC ->
                                obj.get("defaultValue")?.asDouble ?: 0.0
                            com.textgame.domain.model.AttributeType.BOOLEAN ->
                                obj.get("defaultValue")?.asBoolean ?: false
                            com.textgame.domain.model.AttributeType.ENUM ->
                                obj.get("defaultValue")?.asString ?: ""
                            com.textgame.domain.model.AttributeType.TEXT ->
                                obj.get("defaultValue")?.asString ?: ""
                            com.textgame.domain.model.AttributeType.TABLE -> {
                                // defaultValue 为一组行对象数组：List<Map<String, Any>>
                                val arr = obj.getAsJsonArray("defaultValue")
                                if (arr != null) {
                                    arr.map { rowElem ->
                                        rowElem.asJsonObject.entrySet().associate { (k, v) ->
                                            k to parseScalarJsonValue(v)
                                        }
                                    }
                                } else {
                                    emptyList<Map<String, Any>>()
                                }
                            }
                        }
                        val enumOptions = if (type == com.textgame.domain.model.AttributeType.ENUM) {
                            obj.getAsJsonArray("enumOptions")?.map { it.asString } ?: emptyList()
                        } else {
                            emptyList()
                        }
                        val columns = if (type == com.textgame.domain.model.AttributeType.TABLE) {
                            obj.getAsJsonArray("columns")?.mapNotNull { colElem ->
                                try {
                                    val colObj = colElem.asJsonObject
                                    val colTypeStr = colObj.get("type")?.asString ?: "TEXT"
                                    val colNormalized = when (colTypeStr.uppercase()) {
                                        "STRING" -> "TEXT"
                                        else -> colTypeStr.uppercase()
                                    }
                                    val colType = com.textgame.domain.model.AttributeType.values()
                                        .firstOrNull { it.name == colNormalized && it != com.textgame.domain.model.AttributeType.TABLE }
                                        ?: com.textgame.domain.model.AttributeType.TEXT
                                    com.textgame.domain.model.TableColumn(
                                        name = colObj.get("name")?.asString ?: "",
                                        type = colType,
                                        enumOptions = colObj.getAsJsonArray("enumOptions")?.map { it.asString } ?: emptyList(),
                                        description = colObj.get("description")?.asString ?: ""
                                    )
                                } catch (_: Exception) {
                                    null
                                }
                            } ?: emptyList()
                        } else {
                            emptyList()
                        }
                        attributes.add(
                            com.textgame.domain.model.AttributeCategory(
                                name = obj.get("name")?.asString ?: "",
                                type = type,
                                minValue = obj.get("minValue")?.asDouble,
                                maxValue = obj.get("maxValue")?.asDouble,
                                defaultValue = defaultVal,
                                enumOptions = enumOptions,
                                description = obj.get("description")?.asString ?: "",
                                columns = columns
                            )
                        )
                    } catch (e: Exception) {
                        // 单条属性解析失败不影响其他属性
                    }
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
                protagonistName = json.get("protagonistName")?.asString
                    ?: Lang.text("Hero", "主角"),
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

    /**
     * 解析 TABLE 单元格的标量值。Gson 的 JsonElement 已经携带类型信息，
     * 这里转成 Kotlin 友好的 Any（Double / Boolean / String）。
     */
    private fun parseScalarJsonValue(v: com.google.gson.JsonElement): Any {
        return when {
            v.isJsonPrimitive -> {
                val prim = v.asJsonPrimitive
                when {
                    prim.isBoolean -> prim.asBoolean
                    prim.isNumber -> prim.asDouble
                    else -> prim.asString
                }
            }
            v.isJsonNull -> ""
            else -> v.toString()
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

/**
 * 生图提示词风格。
 */
enum class ImagePromptStyle(val label: String) {
    REALISTIC("高真实感"),
    ANIME("动漫化")
}
