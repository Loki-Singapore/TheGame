package com.textgame.domain.model

import com.google.gson.annotations.SerializedName

data class AIResponse(
    val dialogue: String = "",
    val narrative: String = "",
    @SerializedName("state_changes")
    val stateChanges: StateChanges? = null,
    val choices: List<String>? = null,
    @SerializedName("summary_update")
    val summaryUpdate: Boolean = false
)

data class StateChanges(
    val protagonist: ProtagonistChanges? = null,
    val npc: Map<String, NPCChanges>? = null,
    val game: GameChanges? = null
)

data class ProtagonistChanges(
    @SerializedName("attribute_changes")
    val attributeChanges: Map<String, Any>? = null,
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
    val name: String? = null,
    val role: String? = null,
    val mood: String? = null,
    val awareness: String? = null,
    val appearance: String? = null,
    val personality: String? = null,
    val backstory: String? = null,
    @SerializedName("attribute_changes")
    val attributeChanges: Map<String, Any>? = null
)

data class GameChanges(
    @SerializedName("scene_change")
    val sceneChange: String? = null,
    @SerializedName("event_trigger")
    val eventTrigger: String? = null,
    @SerializedName("flag_set")
    val flagSet: Map<String, Boolean>? = null
)
