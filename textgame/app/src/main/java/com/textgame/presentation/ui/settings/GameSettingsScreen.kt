package com.textgame.presentation.ui.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.textgame.R
import com.textgame.di.AppModule
import com.textgame.domain.model.BackgroundSetting
import com.textgame.domain.model.NPC
import com.textgame.domain.model.Protagonist
import com.textgame.domain.model.WorldRule
import com.textgame.domain.model.WorldSetting
import com.textgame.domain.repository.GameRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class GameSettingsUiState(
    val worldSetting: WorldSetting? = null,
    val backgroundSetting: BackgroundSetting? = null,
    val protagonist: Protagonist? = null,
    val npcs: List<NPC> = emptyList(),
    val isLoading: Boolean = false,
    val saved: Boolean = false,
    val error: String? = null
)

class GameSettingsViewModel(private val sessionId: Long) : ViewModel() {
    private val gameRepository: GameRepository = AppModule.getGameRepository()

    private val _uiState = MutableStateFlow(GameSettingsUiState())
    val uiState: StateFlow<GameSettingsUiState> = _uiState.asStateFlow()

    init {
        loadSettings()
    }

    private fun loadSettings() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            try {
                val world = gameRepository.getWorldSetting(sessionId)
                val background = gameRepository.getBackgroundSetting(sessionId)
                val protagonist = gameRepository.getProtagonist(sessionId)
                val npcs = gameRepository.getNPCList(sessionId)
                _uiState.value = GameSettingsUiState(
                    worldSetting = world,
                    backgroundSetting = background,
                    protagonist = protagonist,
                    npcs = npcs,
                    isLoading = false
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(error = e.message, isLoading = false)
            }
        }
    }

    fun updateWorldName(name: String) {
        _uiState.value.worldSetting?.let {
            _uiState.value = _uiState.value.copy(worldSetting = it.copy(name = name))
        }
    }

    fun updateWorldDescription(desc: String) {
        _uiState.value.worldSetting?.let {
            _uiState.value = _uiState.value.copy(worldSetting = it.copy(description = desc))
        }
    }

    fun updateWorldType(type: String) {
        _uiState.value.worldSetting?.let {
            _uiState.value = _uiState.value.copy(worldSetting = it.copy(worldType = type))
        }
    }

    fun updateBackgroundProtagonist(background: String) {
        _uiState.value.backgroundSetting?.let {
            _uiState.value = _uiState.value.copy(backgroundSetting = it.copy(protagonistBackground = background))
        }
    }

    fun updateProtagonistName(name: String) {
        _uiState.value.protagonist?.let {
            _uiState.value = _uiState.value.copy(protagonist = it.copy(name = name))
        }
    }

    fun updateProtagonistLocation(location: String) {
        _uiState.value.protagonist?.let {
            _uiState.value = _uiState.value.copy(protagonist = it.copy(location = location))
        }
    }

    fun updateProtagonistAttribute(key: String, value: String) {
        _uiState.value.protagonist?.let { protag ->
            val attrs = protag.attributes.toMutableMap()
            attrs[key] = value.toDoubleOrNull() ?: value
            _uiState.value = _uiState.value.copy(protagonist = protag.copy(attributes = attrs))
        }
    }

    fun updateNPCName(index: Int, name: String) {
        val npcs = _uiState.value.npcs.toMutableList()
        if (index < npcs.size) {
            npcs[index] = npcs[index].copy(name = name)
            _uiState.value = _uiState.value.copy(npcs = npcs)
        }
    }

    fun updateNPCRole(index: Int, role: String) {
        val npcs = _uiState.value.npcs.toMutableList()
        if (index < npcs.size) {
            npcs[index] = npcs[index].copy(role = role)
            _uiState.value = _uiState.value.copy(npcs = npcs)
        }
    }

    fun updateNPCMood(index: Int, mood: String) {
        val npcs = _uiState.value.npcs.toMutableList()
        if (index < npcs.size) {
            npcs[index] = npcs[index].copy(mood = mood)
            _uiState.value = _uiState.value.copy(npcs = npcs)
        }
    }

    fun updateNPCBriefing(index: Int, briefing: String) {
        val npcs = _uiState.value.npcs.toMutableList()
        if (index < npcs.size) {
            npcs[index] = npcs[index].copy(briefing = briefing)
            _uiState.value = _uiState.value.copy(npcs = npcs)
        }
    }

    fun updateNPCBackstory(index: Int, backstory: String) {
        val npcs = _uiState.value.npcs.toMutableList()
        if (index < npcs.size) {
            npcs[index] = npcs[index].copy(backstory = backstory)
            _uiState.value = _uiState.value.copy(npcs = npcs)
        }
    }

    fun updateNPCPersonality(index: Int, personality: String) {
        val npcs = _uiState.value.npcs.toMutableList()
        if (index < npcs.size) {
            npcs[index] = npcs[index].copy(personality = personality)
            _uiState.value = _uiState.value.copy(npcs = npcs)
        }
    }

    fun updateNPCAppearance(index: Int, appearance: String) {
        val npcs = _uiState.value.npcs.toMutableList()
        if (index < npcs.size) {
            npcs[index] = npcs[index].copy(appearance = appearance)
            _uiState.value = _uiState.value.copy(npcs = npcs)
        }
    }

    fun updateNPCAwareness(index: Int, awareness: String) {
        val npcs = _uiState.value.npcs.toMutableList()
        if (index < npcs.size) {
            npcs[index] = npcs[index].copy(awareness = awareness)
            _uiState.value = _uiState.value.copy(npcs = npcs)
        }
    }

    fun deleteNPC(index: Int) {
        val npcs = _uiState.value.npcs.toMutableList()
        if (index < npcs.size) {
            viewModelScope.launch {
                npcs[index].id.let { npcId ->
                    if (npcId > 0) {
                        gameRepository.deleteNPC(npcId)
                    }
                }
            }
            npcs.removeAt(index)
            _uiState.value = _uiState.value.copy(npcs = npcs)
        }
    }

    fun addWorldRule(content: String) {
        _uiState.value.worldSetting?.let { world ->
            val nextNum = world.worldRules.size + 1
            val newId = "worldrule_${nextNum.toString().padStart(3, '0')}"
            val newRule = WorldRule(id = newId, content = content)
            _uiState.value = _uiState.value.copy(worldSetting = world.copy(worldRules = world.worldRules + newRule))
        }
    }

    fun updateWorldRule(id: String, content: String) {
        _uiState.value.worldSetting?.let { world ->
            val updatedRules = world.worldRules.map { rule ->
                if (rule.id == id) rule.copy(content = content) else rule
            }
            _uiState.value = _uiState.value.copy(worldSetting = world.copy(worldRules = updatedRules))
        }
    }

    fun deleteWorldRule(id: String) {
        _uiState.value.worldSetting?.let { world ->
            _uiState.value = _uiState.value.copy(worldSetting = world.copy(worldRules = world.worldRules.filter { it.id != id }))
        }
    }

    fun saveSettings() {
        viewModelScope.launch {
            try {
                _uiState.value.worldSetting?.let { gameRepository.updateWorldSetting(it) }
                _uiState.value.backgroundSetting?.let { gameRepository.updateBackgroundSetting(it) }
                _uiState.value.protagonist?.let { gameRepository.saveProtagonist(it) }
                _uiState.value.npcs.forEach { npc ->
                    if (npc.id > 0) {
                        gameRepository.updateNPC(npc)
                    } else {
                        gameRepository.saveNPC(npc.copy(sessionId = sessionId))
                    }
                }
                _uiState.value = _uiState.value.copy(saved = true)
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(error = e.message)
            }
        }
    }
}

class GameSettingsViewModelFactory(private val sessionId: Long) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(GameSettingsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return GameSettingsViewModel(sessionId) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GameSettingsScreen(sessionId: Long, onBack: () -> Unit) {
    val viewModel: GameSettingsViewModel = viewModel(
        factory = GameSettingsViewModelFactory(sessionId)
    )
    val uiState by viewModel.uiState.collectAsState(initial = GameSettingsUiState())
    var selectedTab by remember { mutableIntStateOf(0) }
    val tabs = listOf(
        stringResource(R.string.gs_tab_world),
        stringResource(R.string.gs_tab_background),
        stringResource(R.string.gs_tab_protagonist),
        stringResource(R.string.gs_tab_npc)
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.gs_title)) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = stringResource(R.string.back))
                    }
                },
                actions = {
                    TextButton(onClick = {
                        viewModel.saveSettings()
                        onBack()
                    }) {
                        Text(stringResource(R.string.save))
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            TabRow(selectedTabIndex = selectedTab) {
                tabs.forEachIndexed { index, title ->
                    Tab(
                        selected = selectedTab == index,
                        onClick = { selectedTab = index },
                        text = { Text(title) }
                    )
                }
            }

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                when (selectedTab) {
                    0 -> {
                        item {
                            WorldSettingSection(
                                worldSetting = uiState.worldSetting,
                                onNameChange = viewModel::updateWorldName,
                                onDescriptionChange = viewModel::updateWorldDescription,
                                onTypeChange = viewModel::updateWorldType,
                                onAddWorldRule = viewModel::addWorldRule,
                                onUpdateWorldRule = viewModel::updateWorldRule,
                                onDeleteWorldRule = viewModel::deleteWorldRule
                            )
                        }
                    }
                    1 -> {
                        item {
                            BackgroundSettingSection(
                                backgroundSetting = uiState.backgroundSetting,
                                onBackgroundChange = viewModel::updateBackgroundProtagonist
                            )
                        }
                    }
                    2 -> {
                        item {
                            ProtagonistSection(
                                protagonist = uiState.protagonist,
                                onNameChange = viewModel::updateProtagonistName,
                                onLocationChange = viewModel::updateProtagonistLocation,
                                onAttributeChange = viewModel::updateProtagonistAttribute
                            )
                        }
                    }
                    3 -> {
                        itemsIndexed(uiState.npcs) { index, npc ->
                            NPCSection(
                                npc = npc,
                                onNameChange = { viewModel.updateNPCName(index, it) },
                                onRoleChange = { viewModel.updateNPCRole(index, it) },
                                onMoodChange = { viewModel.updateNPCMood(index, it) },
                                onBriefingChange = { viewModel.updateNPCBriefing(index, it) },
                                onBackstoryChange = { viewModel.updateNPCBackstory(index, it) },
                                onPersonalityChange = { viewModel.updateNPCPersonality(index, it) },
                                onAppearanceChange = { viewModel.updateNPCAppearance(index, it) },
                                onAwarenessChange = { viewModel.updateNPCAwareness(index, it) },
                                onDelete = { viewModel.deleteNPC(index) }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun WorldSettingSection(
    worldSetting: WorldSetting?,
    onNameChange: (String) -> Unit,
    onDescriptionChange: (String) -> Unit,
    onTypeChange: (String) -> Unit,
    onAddWorldRule: (String) -> Unit,
    onUpdateWorldRule: (String, String) -> Unit,
    onDeleteWorldRule: (String) -> Unit
) {
    var showAddRuleDialog by remember { mutableStateOf(false) }
    var editingRuleId by remember { mutableStateOf<String?>(null) }
    var editingRuleContent by remember { mutableStateOf("") }

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(stringResource(R.string.gs_world_section), style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = worldSetting?.name ?: "",
                onValueChange = onNameChange,
                label = { Text(stringResource(R.string.gs_world_name)) },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = worldSetting?.worldType ?: "",
                onValueChange = onTypeChange,
                label = { Text(stringResource(R.string.gs_world_type)) },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = worldSetting?.description ?: "",
                onValueChange = onDescriptionChange,
                label = { Text(stringResource(R.string.gs_world_description)) },
                modifier = Modifier.fillMaxWidth(),
                minLines = 3,
                maxLines = 5
            )

            Spacer(modifier = Modifier.height(16.dp))
            Divider()
            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(stringResource(R.string.gs_world_rules), style = MaterialTheme.typography.titleMedium)
                OutlinedButton(onClick = { showAddRuleDialog = true }) {
                    Text(stringResource(R.string.gs_add))
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            worldSetting?.worldRules?.forEach { rule ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = rule.content,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                    IconButton(onClick = {
                        editingRuleId = rule.id
                        editingRuleContent = rule.content
                    }) {
                        Icon(Icons.Default.Edit, contentDescription = stringResource(R.string.edit), tint = MaterialTheme.colorScheme.primary)
                    }
                    IconButton(onClick = { onDeleteWorldRule(rule.id) }) {
                        Icon(Icons.Default.Delete, contentDescription = stringResource(R.string.delete), tint = MaterialTheme.colorScheme.error)
                    }
                }
                Spacer(modifier = Modifier.height(4.dp))
            }

            if (worldSetting?.worldRules.isNullOrEmpty()) {
                Text(
                    text = stringResource(R.string.gs_no_rules),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }

    if (showAddRuleDialog) {
        AddWorldRuleDialog(
            onDismiss = { showAddRuleDialog = false },
            onConfirm = { content ->
                onAddWorldRule(content)
                showAddRuleDialog = false
            }
        )
    }

    editingRuleId?.let { id ->
        EditWorldRuleDialog(
            initialContent = editingRuleContent,
            onDismiss = {
                editingRuleId = null
                editingRuleContent = ""
            },
            onConfirm = { content ->
                onUpdateWorldRule(id, content)
                editingRuleId = null
                editingRuleContent = ""
            }
        )
    }
}

@Composable
fun AddWorldRuleDialog(
    onDismiss: () -> Unit,
    onConfirm: (String) -> Unit
) {
    var content by remember { mutableStateOf("") }
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(stringResource(R.string.gs_add_rule_title)) },
        text = {
            OutlinedTextField(
                value = content,
                onValueChange = { content = it },
                label = { Text(stringResource(R.string.gs_rule_content)) },
                modifier = Modifier.fillMaxWidth(),
                minLines = 2
            )
        },
        confirmButton = {
            TextButton(onClick = { onConfirm(content) }, enabled = content.isNotBlank()) {
                Text(stringResource(R.string.gs_add))
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(stringResource(R.string.cancel))
            }
        }
    )
}

@Composable
fun EditWorldRuleDialog(
    initialContent: String,
    onDismiss: () -> Unit,
    onConfirm: (String) -> Unit
) {
    var content by remember { mutableStateOf(initialContent) }
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(stringResource(R.string.gs_edit_rule_title)) },
        text = {
            OutlinedTextField(
                value = content,
                onValueChange = { content = it },
                label = { Text(stringResource(R.string.gs_rule_content)) },
                modifier = Modifier.fillMaxWidth(),
                minLines = 2
            )
        },
        confirmButton = {
            TextButton(onClick = { onConfirm(content) }, enabled = content.isNotBlank()) {
                Text(stringResource(R.string.save))
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(stringResource(R.string.cancel))
            }
        }
    )
}

@Composable
fun BackgroundSettingSection(
    backgroundSetting: BackgroundSetting?,
    onBackgroundChange: (String) -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(stringResource(R.string.gs_background_section), style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = backgroundSetting?.protagonistBackground ?: "",
                onValueChange = onBackgroundChange,
                label = { Text(stringResource(R.string.gs_protagonist_background)) },
                modifier = Modifier.fillMaxWidth(),
                minLines = 4,
                maxLines = 8
            )
        }
    }
}

@Composable
fun ProtagonistSection(
    protagonist: Protagonist?,
    onNameChange: (String) -> Unit,
    onLocationChange: (String) -> Unit,
    onAttributeChange: (String, String) -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(stringResource(R.string.gs_protagonist_section), style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = protagonist?.name ?: "",
                onValueChange = onNameChange,
                label = { Text(stringResource(R.string.gs_protagonist_name)) },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = protagonist?.location ?: "",
                onValueChange = onLocationChange,
                label = { Text(stringResource(R.string.gs_current_location)) },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))
            Divider()
            Spacer(modifier = Modifier.height(8.dp))

            Text(stringResource(R.string.gs_attributes), style = MaterialTheme.typography.titleSmall)
            Spacer(modifier = Modifier.height(8.dp))

            protagonist?.attributes?.forEach { (key, value) ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(key, modifier = Modifier.weight(1f))
                    OutlinedTextField(
                        value = value.toString(),
                        onValueChange = { onAttributeChange(key, it) },
                        modifier = Modifier.weight(1f),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                    )
                }
                Spacer(modifier = Modifier.height(4.dp))
            }
        }
    }
}

@Composable
fun NPCSection(
    npc: NPC,
    onNameChange: (String) -> Unit,
    onRoleChange: (String) -> Unit,
    onMoodChange: (String) -> Unit,
    onBriefingChange: (String) -> Unit,
    onBackstoryChange: (String) -> Unit,
    onPersonalityChange: (String) -> Unit,
    onAppearanceChange: (String) -> Unit,
    onAwarenessChange: (String) -> Unit,
    onDelete: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(stringResource(R.string.gs_npc_title, npc.name), style = MaterialTheme.typography.titleMedium)
                IconButton(onClick = onDelete) {
                    Icon(Icons.Default.Delete, contentDescription = stringResource(R.string.delete))
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = npc.name,
                onValueChange = onNameChange,
                label = { Text(stringResource(R.string.gs_name)) },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = npc.role,
                onValueChange = onRoleChange,
                label = { Text(stringResource(R.string.gs_role)) },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = npc.briefing,
                onValueChange = onBriefingChange,
                label = { Text(stringResource(R.string.gs_brief)) },
                modifier = Modifier.fillMaxWidth(),
                minLines = 2,
                maxLines = 3
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = npc.mood,
                onValueChange = onMoodChange,
                label = { Text(stringResource(R.string.gs_mood)) },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = npc.awareness,
                onValueChange = onAwarenessChange,
                label = { Text(stringResource(R.string.gs_awareness)) },
                modifier = Modifier.fillMaxWidth(),
                minLines = 2,
                maxLines = 3
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = npc.appearance,
                onValueChange = onAppearanceChange,
                label = { Text(stringResource(R.string.gs_appearance)) },
                modifier = Modifier.fillMaxWidth(),
                minLines = 2,
                maxLines = 4
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = npc.personality,
                onValueChange = onPersonalityChange,
                label = { Text(stringResource(R.string.gs_personality)) },
                modifier = Modifier.fillMaxWidth(),
                minLines = 2,
                maxLines = 3
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = npc.backstory,
                onValueChange = onBackstoryChange,
                label = { Text(stringResource(R.string.gs_backstory)) },
                modifier = Modifier.fillMaxWidth(),
                minLines = 3,
                maxLines = 6
            )
        }
    }
}
