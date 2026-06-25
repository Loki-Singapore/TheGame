package com.textgame.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.textgame.di.AppModule
import com.textgame.domain.model.AttributeCategory
import com.textgame.domain.model.NPC
import com.textgame.domain.model.WorldSetting
import com.textgame.domain.model.BackgroundSetting
import com.textgame.domain.model.Protagonist
import com.textgame.domain.usecase.CreateGameUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class CreatorUiState(
    val gameName: String = "",
    val protagonistName: String = "主角",
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
    val attributeCategories: List<AttributeCategory> = defaultAttributes(),
    val npcs: List<NPC> = emptyList(),
    val isCreating: Boolean = false,
    val createdSessionId: Long? = null,
    val error: String? = null,
    val isGenerating: Boolean = false,
    val generationPrompt: String = ""
) {
    companion object {
        fun defaultAttributes(): List<AttributeCategory> = listOf(
            AttributeCategory(
                name = "生命值",
                type = com.textgame.domain.model.AttributeType.NUMERIC,
                minValue = 0.0,
                maxValue = 100.0,
                defaultValue = 100.0,
                description = "角色的生命值"
            ),
            AttributeCategory(
                name = "金币",
                type = com.textgame.domain.model.AttributeType.NUMERIC,
                minValue = 0.0,
                maxValue = 999999.0,
                defaultValue = 100.0,
                description = "游戏货币"
            )
        )
    }
}

class CreatorViewModel : ViewModel() {
    private val createGameUseCase: CreateGameUseCase = AppModule.getCreateGameUseCase()
    private val aiService = AppModule.getAIService()

    private val _uiState = MutableStateFlow(CreatorUiState())
    val uiState: StateFlow<CreatorUiState> = _uiState.asStateFlow()

    fun updateGameName(name: String) {
        _uiState.value = _uiState.value.copy(gameName = name)
    }

    fun updateProtagonistName(name: String) {
        _uiState.value = _uiState.value.copy(protagonistName = name)
    }

    fun updateWorldName(name: String) {
        _uiState.value = _uiState.value.copy(worldName = name)
    }

    fun updateWorldType(type: String) {
        _uiState.value = _uiState.value.copy(worldType = type)
    }

    fun updateWorldDescription(desc: String) {
        _uiState.value = _uiState.value.copy(worldDescription = desc)
    }

    fun updateTimeSetting(setting: String) {
        _uiState.value = _uiState.value.copy(timeSetting = setting)
    }

    fun updateLocationSetting(setting: String) {
        _uiState.value = _uiState.value.copy(locationSetting = setting)
    }

    fun updateSocialStructure(structure: String) {
        _uiState.value = _uiState.value.copy(socialStructure = structure)
    }

    fun addSpecialRule(rule: String) {
        if (rule.isNotBlank()) {
            _uiState.value = _uiState.value.copy(
                specialRules = _uiState.value.specialRules + rule
            )
        }
    }

    fun removeSpecialRule(index: Int) {
        val rules = _uiState.value.specialRules.toMutableList()
        if (index in rules.indices) {
            rules.removeAt(index)
            _uiState.value = _uiState.value.copy(specialRules = rules)
        }
    }

    fun updateLore(lore: String) {
        _uiState.value = _uiState.value.copy(lore = lore)
    }

    fun updateProtagonistBackground(bg: String) {
        _uiState.value = _uiState.value.copy(protagonistBackground = bg)
    }

    fun updateWorldHistory(history: String) {
        _uiState.value = _uiState.value.copy(worldHistory = history)
    }

    fun addNPC(npc: NPC) {
        _uiState.value = _uiState.value.copy(npcs = _uiState.value.npcs + npc)
    }

    fun removeNPC(index: Int) {
        val npcs = _uiState.value.npcs.toMutableList()
        if (index in npcs.indices) {
            npcs.removeAt(index)
            _uiState.value = _uiState.value.copy(npcs = npcs)
        }
    }

    fun updateNPC(index: Int, npc: NPC) {
        val npcs = _uiState.value.npcs.toMutableList()
        if (index in npcs.indices) {
            npcs[index] = npc
            _uiState.value = _uiState.value.copy(npcs = npcs)
        }
    }

    fun addAttributeCategory(category: AttributeCategory) {
        _uiState.value = _uiState.value.copy(
            attributeCategories = _uiState.value.attributeCategories + category
        )
    }

    fun removeAttributeCategory(index: Int) {
        val categories = _uiState.value.attributeCategories.toMutableList()
        if (index in categories.indices) {
            categories.removeAt(index)
            _uiState.value = _uiState.value.copy(attributeCategories = categories)
        }
    }

    fun updateGenerationPrompt(prompt: String) {
        _uiState.value = _uiState.value.copy(generationPrompt = prompt)
    }

    fun generateWorldFromPrompt() {
        val prompt = _uiState.value.generationPrompt
        if (prompt.isBlank()) {
            _uiState.value = _uiState.value.copy(error = "请输入一句话描述")
            return
        }

        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isGenerating = true, error = null)
            try {
                val result = aiService.generateWorldFromPrompt(prompt)
                if (result.error != null) {
                    _uiState.value = _uiState.value.copy(
                        isGenerating = false,
                        error = "生成失败: ${result.error}"
                    )
                    return@launch
                }

                _uiState.value = _uiState.value.copy(
                    gameName = result.gameName.ifBlank { _uiState.value.gameName },
                    protagonistName = result.protagonistName.ifBlank { _uiState.value.protagonistName },
                    worldName = result.worldName.ifBlank { _uiState.value.worldName },
                    worldType = result.worldType.ifBlank { _uiState.value.worldType },
                    worldDescription = result.worldDescription.ifBlank { _uiState.value.worldDescription },
                    timeSetting = result.timeSetting.ifBlank { _uiState.value.timeSetting },
                    locationSetting = result.locationSetting.ifBlank { _uiState.value.locationSetting },
                    socialStructure = result.socialStructure.ifBlank { _uiState.value.socialStructure },
                    specialRules = result.specialRules.ifEmpty { _uiState.value.specialRules },
                    lore = result.lore.ifBlank { _uiState.value.lore },
                    protagonistBackground = result.protagonistBackground.ifBlank { _uiState.value.protagonistBackground },
                    worldHistory = result.worldHistory.ifBlank { _uiState.value.worldHistory },
                    attributeCategories = result.attributes.ifEmpty { _uiState.value.attributeCategories },
                    npcs = result.npcs.ifEmpty { _uiState.value.npcs },
                    isGenerating = false
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isGenerating = false,
                    error = "生成失败: ${e.message}"
                )
            }
        }
    }

    fun createGame() {
        if (_uiState.value.gameName.isBlank()) {
            _uiState.value = _uiState.value.copy(error = "请输入游戏名称")
            return
        }

        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isCreating = true, error = null)

            try {
                val state = _uiState.value

                val initialAttributes = state.attributeCategories.associate { category ->
                    category.name to (category.defaultValue ?: "")
                }

                val worldSetting = WorldSetting(
                    name = state.worldName.ifBlank { state.gameName },
                    description = state.worldDescription,
                    worldType = state.worldType,
                    timeSetting = state.timeSetting,
                    locationSetting = state.locationSetting,
                    socialStructure = state.socialStructure,
                    specialRules = state.specialRules,
                    lore = state.lore,
                    locations = if (state.locationSetting.isNotBlank()) listOf(state.locationSetting) else emptyList()
                )

                val backgroundSetting = BackgroundSetting(
                    protagonistBackground = state.protagonistBackground,
                    worldHistory = state.worldHistory
                )

                val protagonist = Protagonist(
                    name = state.protagonistName,
                    attributes = initialAttributes,
                    location = state.locationSetting
                )

                val sessionId = createGameUseCase.execute(
                    gameName = state.gameName,
                    protagonistName = state.protagonistName,
                    worldSetting = worldSetting,
                    backgroundSetting = backgroundSetting,
                    initialNPCs = state.npcs,
                    initialProtagonist = protagonist
                )

                _uiState.value = _uiState.value.copy(
                    isCreating = false,
                    createdSessionId = sessionId
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isCreating = false,
                    error = "创建失败: ${e.message}"
                )
            }
        }
    }
}
