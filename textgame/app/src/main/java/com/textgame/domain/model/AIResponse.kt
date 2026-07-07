package com.textgame.domain.model

import com.google.gson.annotations.SerializedName

data class AIResponse(
    val dialogue: String = "",
    val narrative: String = "",
    @SerializedName("state_changes")
    val stateChanges: StateChanges? = null,
    val choices: List<String>? = null,
    @SerializedName("summary_update")
    val summaryUpdate: Boolean = false,
    val bgm: String? = null,
    val tokenUsage: TokenUsage? = null
)

sealed class StreamingChunk {
    data class NarrativeDelta(val delta: String) : StreamingChunk()
    data class DialogueDelta(val delta: String) : StreamingChunk()
    data class Complete(val response: AIResponse) : StreamingChunk()
    data class Error(val message: String) : StreamingChunk()
}

data class TokenUsage(
    val promptTokens: Int = 0,
    val completionTokens: Int = 0,
    val totalTokens: Int = 0
)

data class StateChanges(
    val protagonist: ProtagonistChanges? = null,
    val npc: Map<String, NPCChanges>? = null,
    val game: GameChanges? = null
)

data class ProtagonistChanges(
    // AI返回主角的完整属性状态，引擎直接替换，不做加减
    val attributes: Map<String, Any>? = null,
    @SerializedName("inventory_add")
    val inventoryAdd: List<String>? = null,
    @SerializedName("inventory_remove")
    val inventoryRemove: List<String>? = null,
    @SerializedName("location_change")
    val locationChange: String? = null
)

data class NPCChanges(
    @SerializedName("is_new")
    val isNew: Boolean = false,
    @SerializedName("is_deleted")
    val isDeleted: Boolean = false,
    val name: String? = null,
    val briefing: String? = null,
    val role: String? = null,
    val mood: String? = null,
    val awareness: String? = null,
    val appearance: String? = null,
    val personality: String? = null,
    val backstory: String? = null,
    // ponytail: 隐藏动机更新。AI可写入，玩家不可见。返回完整的新内容而非增量。
    @SerializedName("hidden_agenda")
    val hiddenAgenda: String? = null,
    // AI返回NPC的完整属性状态，引擎直接替换，不做加减
    val attributes: Map<String, Any>? = null
)

data class GameChanges(
    @SerializedName("scene_change")
    val sceneChange: String? = null,
    @SerializedName("event_trigger")
    val eventTrigger: String? = null,
    @SerializedName("flag_set")
    val flagSet: Map<String, Boolean>? = null,
    @SerializedName("world_rules")
    val worldRules: List<WorldRuleChange>? = null,
    @SerializedName("attribute_categories")
    val attributeCategories: List<AttributeCategoryChange>? = null
)

data class WorldRuleChange(
    val id: String? = null,
    val content: String
)

/**
 * AI 返回的属性类目变更指令。按 name 匹配已有类目：
 * - is_deleted=true：删除该类目，并从主角/NPC的attributes中移除该键
 * - name 已存在：按提供的字段部分更新（未提供的字段保持不变）
 * - name 不存在且 is_deleted=false：新增类目（type 必填），引擎会用 defaultValue 初始化主角的对应属性
 */
data class AttributeCategoryChange(
    val name: String,
    val type: String? = null,
    @SerializedName("minValue")
    val minValue: Double? = null,
    @SerializedName("maxValue")
    val maxValue: Double? = null,
    @SerializedName("defaultValue")
    val defaultValue: Any? = null,
    @SerializedName("enumOptions")
    val enumOptions: List<String>? = null,
    val description: String? = null,
    @SerializedName("is_deleted")
    val isDeleted: Boolean = false
)