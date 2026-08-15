package com.textgame.presentation.ui.creator

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.textgame.R
import com.textgame.domain.model.AttributeCategory
import com.textgame.domain.model.AttributeType
import com.textgame.domain.model.NPC
import com.textgame.domain.model.TableColumn
import com.textgame.presentation.viewmodel.CreatorViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreatorScreen(
    onBack: () -> Unit,
    onGameCreated: (Long) -> Unit
) {
    val viewModel: CreatorViewModel = viewModel()
    val uiState by viewModel.uiState.collectAsState(initial = com.textgame.presentation.viewmodel.CreatorUiState())

    var currentStep by remember { mutableStateOf(0) }
    var showAttributeDialog by remember { mutableStateOf(false) }
    var showNPCDialog by remember { mutableStateOf(false) }

    val steps = listOf(stringResource(R.string.creator_step_basic), stringResource(R.string.creator_step_world), stringResource(R.string.creator_step_attributes), stringResource(R.string.creator_step_npcs), stringResource(R.string.creator_step_done))

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.creator_title)) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = stringResource(R.string.back))
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
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                steps.forEachIndexed { index, step ->
                    Text(
                        text = step,
                        style = if (index == currentStep) {
                            MaterialTheme.typography.labelMedium
                        } else {
                            MaterialTheme.typography.labelMedium
                        },
                        color = if (index <= currentStep) {
                            MaterialTheme.colorScheme.primary
                        } else {
                            MaterialTheme.colorScheme.onSurfaceVariant
                        }
                    )
                }
            }

            Column(
                modifier = Modifier
                    .weight(1f)
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp)
            ) {
                when (currentStep) {
                    0 -> BasicInfoStep(viewModel)
                    1 -> WorldSettingStep(viewModel)
                    2 -> AttributeStep(viewModel, onAddAttribute = { showAttributeDialog = true })
                    3 -> NPCStep(viewModel, onAddNPC = { showNPCDialog = true })
                    4 -> FinalStep(viewModel)
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                if (currentStep > 0) {
                    Button(
                        onClick = { currentStep-- },
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(stringResource(R.string.creator_previous))
                    }
                }
                if (currentStep < steps.size - 1) {
                    Button(
                        onClick = { currentStep++ },
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(stringResource(R.string.creator_next))
                    }
                } else {
                    Button(
                        onClick = { viewModel.createGame() },
                        modifier = Modifier.weight(1f),
                        enabled = !uiState.isCreating
                    ) {
                        Text(stringResource(if (uiState.isCreating) R.string.creator_creating else R.string.creator_create))
                    }
                }
            }

            uiState.error?.let { error ->
                Text(
                    text = error,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
            }
        }
    }

    uiState.createdSessionId?.let { sessionId ->
        AlertDialog(
            onDismissRequest = { },
            title = { Text(stringResource(R.string.creator_created_title)) },
            text = { Text(stringResource(R.string.creator_created_message)) },
            confirmButton = {
                TextButton(onClick = { onGameCreated(sessionId) }) {
                    Text(stringResource(R.string.creator_start_game))
                }
            }
        )
    }

    if (showAttributeDialog) {
        AttributeDialog(
            initial = null,
            title = stringResource(R.string.creator_add_attribute_title),
            confirmText = stringResource(R.string.add),
            onDismiss = { showAttributeDialog = false },
            onConfirm = { category ->
                viewModel.addAttributeCategory(category)
                showAttributeDialog = false
            }
        )
    }

    if (showNPCDialog) {
        AddNPCDialog(
            onDismiss = { showNPCDialog = false },
            onAdd = { npc ->
                viewModel.addNPC(npc)
                showNPCDialog = false
            }
        )
    }
}

@Composable
fun BasicInfoStep(viewModel: CreatorViewModel) {
    val uiState by viewModel.uiState.collectAsState(initial = com.textgame.presentation.viewmodel.CreatorUiState())

    // 一句话AI生成
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = androidx.compose.material3.CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        )
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = stringResource(R.string.creator_one_sentence_title),
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = stringResource(R.string.creator_one_sentence_desc),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = uiState.generationPrompt,
                onValueChange = viewModel::updateGenerationPrompt,
                label = { Text(stringResource(R.string.creator_generation_placeholder)) },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )
            Spacer(modifier = Modifier.height(8.dp))
            Button(
                onClick = viewModel::generateWorldFromPrompt,
                modifier = Modifier.fillMaxWidth(),
                enabled = !uiState.isGenerating && uiState.generationPrompt.isNotBlank()
            ) {
                Text(stringResource(if (uiState.isGenerating) R.string.creator_generating else R.string.creator_ai_generate))
            }
        }
    }

    Spacer(modifier = Modifier.height(24.dp))

    Text(stringResource(R.string.creator_or_manual), style = MaterialTheme.typography.titleSmall)
    Spacer(modifier = Modifier.height(12.dp))

    OutlinedTextField(
        value = uiState.gameName,
        onValueChange = viewModel::updateGameName,
        label = { Text(stringResource(R.string.creator_game_name)) },
        modifier = Modifier.fillMaxWidth(),
        singleLine = true
    )

    Spacer(modifier = Modifier.height(16.dp))

    OutlinedTextField(
        value = uiState.protagonistName,
        onValueChange = viewModel::updateProtagonistName,
        label = { Text(stringResource(R.string.creator_protagonist_name)) },
        modifier = Modifier.fillMaxWidth(),
        singleLine = true
    )
}

@Composable
fun WorldSettingStep(viewModel: CreatorViewModel) {
    val uiState by viewModel.uiState.collectAsState(initial = com.textgame.presentation.viewmodel.CreatorUiState())

    OutlinedTextField(
        value = uiState.worldName,
        onValueChange = viewModel::updateWorldName,
        label = { Text(stringResource(R.string.creator_world_name)) },
        modifier = Modifier.fillMaxWidth(),
        singleLine = true
    )

    Spacer(modifier = Modifier.height(12.dp))

    WorldTypeDropdown(viewModel)

    Spacer(modifier = Modifier.height(12.dp))

    OutlinedTextField(
        value = uiState.worldDescription,
        onValueChange = viewModel::updateWorldDescription,
        label = { Text(stringResource(R.string.creator_world_description)) },
        modifier = Modifier.fillMaxWidth().height(120.dp),
        maxLines = 5
    )

    Spacer(modifier = Modifier.height(12.dp))

    OutlinedTextField(
        value = uiState.timeSetting,
        onValueChange = viewModel::updateTimeSetting,
        label = { Text(stringResource(R.string.creator_time_setting)) },
        modifier = Modifier.fillMaxWidth(),
        singleLine = true
    )

    Spacer(modifier = Modifier.height(12.dp))

    OutlinedTextField(
        value = uiState.locationSetting,
        onValueChange = viewModel::updateLocationSetting,
        label = { Text(stringResource(R.string.creator_start_location)) },
        modifier = Modifier.fillMaxWidth(),
        singleLine = true
    )

    Spacer(modifier = Modifier.height(12.dp))

    OutlinedTextField(
        value = uiState.socialStructure,
        onValueChange = viewModel::updateSocialStructure,
        label = { Text(stringResource(R.string.creator_social_structure)) },
        modifier = Modifier.fillMaxWidth(),
        singleLine = true
    )

    Spacer(modifier = Modifier.height(12.dp))

    SpecialRulesSection(viewModel)

    Spacer(modifier = Modifier.height(12.dp))

    OutlinedTextField(
        value = uiState.lore,
        onValueChange = viewModel::updateLore,
        label = { Text(stringResource(R.string.creator_lore)) },
        modifier = Modifier.fillMaxWidth().height(100.dp),
        maxLines = 4
    )

    Spacer(modifier = Modifier.height(12.dp))

    OutlinedTextField(
        value = uiState.protagonistBackground,
        onValueChange = viewModel::updateProtagonistBackground,
        label = { Text(stringResource(R.string.creator_protagonist_background)) },
        modifier = Modifier.fillMaxWidth().height(100.dp),
        maxLines = 4
    )

    Spacer(modifier = Modifier.height(12.dp))

    OutlinedTextField(
        value = uiState.worldHistory,
        onValueChange = viewModel::updateWorldHistory,
        label = { Text(stringResource(R.string.creator_world_history)) },
        modifier = Modifier.fillMaxWidth().height(100.dp),
        maxLines = 4
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WorldTypeDropdown(viewModel: CreatorViewModel) {
    val uiState by viewModel.uiState.collectAsState(initial = com.textgame.presentation.viewmodel.CreatorUiState())
    var expanded by remember { mutableStateOf(false) }
    val worldTypes = stringArrayResource(R.array.creator_world_types).toList()

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded }
    ) {
        OutlinedTextField(
            value = uiState.worldType,
            onValueChange = { },
            readOnly = true,
            label = { Text(stringResource(R.string.creator_world_type)) },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            modifier = Modifier.fillMaxWidth().menuAnchor()
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            worldTypes.forEach { type ->
                DropdownMenuItem(
                    text = { Text(type) },
                    onClick = {
                        viewModel.updateWorldType(type)
                        expanded = false
                    }
                )
            }
        }
    }
}

@Composable
fun SpecialRulesSection(viewModel: CreatorViewModel) {
    val uiState by viewModel.uiState.collectAsState(initial = com.textgame.presentation.viewmodel.CreatorUiState())
    var ruleText by remember { mutableStateOf("") }

    Text(stringResource(R.string.creator_special_rules), style = MaterialTheme.typography.titleSmall)

    Spacer(modifier = Modifier.height(8.dp))

    uiState.specialRules.forEachIndexed { index, rule ->
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("• $rule", modifier = Modifier.weight(1f))
            IconButton(onClick = { viewModel.removeSpecialRule(index) }) {
                Icon(Icons.Default.Delete, contentDescription = stringResource(R.string.delete))
            }
        }
    }

    Spacer(modifier = Modifier.height(8.dp))

    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        OutlinedTextField(
            value = ruleText,
            onValueChange = { ruleText = it },
            placeholder = { Text(stringResource(R.string.creator_add_rule)) },
            modifier = Modifier.weight(1f),
            singleLine = true
        )
        Button(
            onClick = {
                viewModel.addSpecialRule(ruleText)
                ruleText = ""
            },
            enabled = ruleText.isNotBlank()
        ) {
            Icon(Icons.Default.Add, contentDescription = stringResource(R.string.add))
        }
    }
}

@Composable
fun AttributeStep(viewModel: CreatorViewModel, onAddAttribute: () -> Unit) {
    val uiState by viewModel.uiState.collectAsState(initial = com.textgame.presentation.viewmodel.CreatorUiState())
    var editingAttrIndex by remember { mutableStateOf<Int?>(null) }
    var editingAttr by remember { mutableStateOf<AttributeCategory?>(null) }

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(stringResource(R.string.creator_attribute_categories), style = MaterialTheme.typography.titleMedium)
        TextButton(onClick = onAddAttribute) {
            Icon(Icons.Default.Add, contentDescription = null)
            Spacer(modifier = Modifier.height(4.dp))
            Text(stringResource(R.string.creator_add_attribute))
        }
    }

    Spacer(modifier = Modifier.height(8.dp))

    if (uiState.attributeCategories.isEmpty()) {
        Text(
            text = stringResource(R.string.creator_no_attributes),
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }

    uiState.attributeCategories.forEachIndexed { index, category ->
        Card(
            modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth().padding(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(category.name, style = MaterialTheme.typography.titleSmall)
                    if (category.type == AttributeType.TABLE) {
                        Text(
                            text = stringResource(R.string.creator_attr_type_table, category.columns.joinToString(", ") { it.name }),
                            style = MaterialTheme.typography.bodySmall
                        )
                    } else {
                        Text(
                            text = stringResource(R.string.creator_attr_type_default, category.type.name, category.defaultValue.toString()),
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                    if (category.description.isNotBlank()) {
                        Text(
                            text = category.description,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
                IconButton(onClick = {
                    editingAttrIndex = index
                    editingAttr = category
                }) {
                    Icon(Icons.Default.Edit, contentDescription = stringResource(R.string.edit), tint = MaterialTheme.colorScheme.primary)
                }
                IconButton(onClick = { viewModel.removeAttributeCategory(index) }) {
                    Icon(Icons.Default.Delete, contentDescription = stringResource(R.string.delete), tint = MaterialTheme.colorScheme.error)
                }
            }
        }
    }

    editingAttr?.let { category ->
        AttributeDialog(
            initial = category,
            title = stringResource(R.string.creator_edit_attribute_title),
            confirmText = stringResource(R.string.save),
            onDismiss = {
                editingAttr = null
                editingAttrIndex = null
            },
            onConfirm = { updated ->
                editingAttrIndex?.let { index ->
                    viewModel.updateAttributeCategory(index, updated)
                }
                editingAttr = null
                editingAttrIndex = null
            }
        )
    }
}

@Composable
fun NPCStep(viewModel: CreatorViewModel, onAddNPC: () -> Unit) {
    val uiState by viewModel.uiState.collectAsState(initial = com.textgame.presentation.viewmodel.CreatorUiState())
    var editingNpcIndex by remember { mutableStateOf<Int?>(null) }
    var editingNpc by remember { mutableStateOf<NPC?>(null) }

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(stringResource(R.string.creator_initial_npcs), style = MaterialTheme.typography.titleMedium)
        TextButton(onClick = onAddNPC) {
            Icon(Icons.Default.Add, contentDescription = null)
            Spacer(modifier = Modifier.height(4.dp))
            Text(stringResource(R.string.creator_add_npc))
        }
    }

    Spacer(modifier = Modifier.height(8.dp))

    if (uiState.npcs.isEmpty()) {
        Text(
            text = stringResource(R.string.creator_no_npcs),
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }

    uiState.npcs.forEachIndexed { index, npc ->
        Card(
            modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth().padding(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(npc.name, style = MaterialTheme.typography.titleSmall)
                    Text(
                        text = npc.role,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    if (npc.personality.isNotEmpty()) {
                        Text(
                            text = stringResource(R.string.creator_npc_personality, npc.personality),
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }
                IconButton(onClick = {
                    editingNpcIndex = index
                    editingNpc = npc
                }) {
                    Icon(Icons.Default.Edit, contentDescription = stringResource(R.string.edit), tint = MaterialTheme.colorScheme.primary)
                }
                IconButton(onClick = { viewModel.removeNPC(index) }) {
                    Icon(Icons.Default.Delete, contentDescription = stringResource(R.string.delete), tint = MaterialTheme.colorScheme.error)
                }
            }
        }
    }

    editingNpc?.let { npc ->
        EditNPCDialog(
            npc = npc,
            onDismiss = {
                editingNpc = null
                editingNpcIndex = null
            },
            onSave = { updatedNpc ->
                editingNpcIndex?.let { index ->
                    viewModel.updateNPC(index, updatedNpc)
                }
                editingNpc = null
                editingNpcIndex = null
            }
        )
    }
}

@Composable
fun FinalStep(viewModel: CreatorViewModel) {
    val uiState by viewModel.uiState.collectAsState(initial = com.textgame.presentation.viewmodel.CreatorUiState())

    Text(stringResource(R.string.creator_overview_title), style = MaterialTheme.typography.titleLarge)

    Spacer(modifier = Modifier.height(16.dp))

    Card(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(stringResource(R.string.creator_overview_game_name, uiState.gameName))
            Text(stringResource(R.string.creator_overview_protagonist, uiState.protagonistName))
            Text(stringResource(R.string.creator_overview_world, uiState.worldName))
            Text(stringResource(R.string.creator_overview_type, uiState.worldType))
            Text(stringResource(R.string.creator_overview_attr_count, uiState.attributeCategories.size))
            Text(stringResource(R.string.creator_overview_npc_count, uiState.npcs.size))
        }
    }

    Spacer(modifier = Modifier.height(16.dp))

    Text(
        text = stringResource(R.string.creator_overview_cta),
        style = MaterialTheme.typography.bodyMedium,
        color = MaterialTheme.colorScheme.onSurfaceVariant
    )
}

@Composable
fun AttributeDialog(
    initial: AttributeCategory?,
    title: String,
    confirmText: String,
    onDismiss: () -> Unit,
    onConfirm: (AttributeCategory) -> Unit
) {
    var name by remember { mutableStateOf(initial?.name ?: "") }
    var type by remember { mutableStateOf(initial?.type ?: AttributeType.NUMERIC) }
    var defaultValue by remember {
        mutableStateOf(when (initial?.type) {
            AttributeType.NUMERIC -> (initial.defaultValue as? Double)?.toString() ?: ""
            AttributeType.BOOLEAN -> (initial.defaultValue as? Boolean)?.toString() ?: "false"
            else -> initial?.defaultValue?.toString() ?: ""
        })
    }
    var minValue by remember { mutableStateOf(initial?.minValue?.toString() ?: "") }
    var maxValue by remember { mutableStateOf(initial?.maxValue?.toString() ?: "") }
    var enumOptionsText by remember { mutableStateOf(initial?.enumOptions?.joinToString(",") ?: "") }
    var description by remember { mutableStateOf(initial?.description ?: "") }
    // TABLE 列编辑器状态：初始为已有类目的列定义，或空表
    var columns by remember {
        mutableStateOf(initial?.columns?.map { it.copy() } ?: emptyList<TableColumn>())
    }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(title) },
        text = {
            Column {
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text(stringResource(R.string.creator_attr_name)) },
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(8.dp))

                AttributeTypeDropdown(selected = type, onSelected = { type = it })

                Spacer(modifier = Modifier.height(8.dp))

                when (type) {
                    AttributeType.NUMERIC -> {
                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            OutlinedTextField(
                                value = minValue,
                                onValueChange = { minValue = it },
                                label = { Text(stringResource(R.string.creator_attr_min_value)) },
                                modifier = Modifier.weight(1f),
                                singleLine = true
                            )
                            OutlinedTextField(
                                value = maxValue,
                                onValueChange = { maxValue = it },
                                label = { Text(stringResource(R.string.creator_attr_max_value)) },
                                modifier = Modifier.weight(1f),
                                singleLine = true
                            )
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        OutlinedTextField(
                            value = defaultValue,
                            onValueChange = { defaultValue = it },
                            label = { Text(stringResource(R.string.creator_attr_default_numeric)) },
                            singleLine = true
                        )
                    }
                    AttributeType.BOOLEAN -> {
                        val checked = defaultValue.toBoolean()
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            androidx.compose.material3.Switch(
                                checked = checked,
                                onCheckedChange = { defaultValue = it.toString() }
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(if (checked) "true" else "false")
                        }
                    }
                    AttributeType.ENUM -> {
                        OutlinedTextField(
                            value = enumOptionsText,
                            onValueChange = { enumOptionsText = it },
                            label = { Text(stringResource(R.string.creator_enum_options_comma)) },
                            singleLine = true
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        val options = enumOptionsText.split(",").map { it.trim() }.filter { it.isNotEmpty() }
                        if (options.isNotEmpty()) {
                            Text(
                                text = stringResource(R.string.creator_enum_default_must_be_option, options.joinToString(" / ")),
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                        }
                        OutlinedTextField(
                            value = defaultValue,
                            onValueChange = { defaultValue = it },
                            label = { Text(stringResource(R.string.creator_enum_default_from_options)) },
                            singleLine = true
                        )
                    }
                    AttributeType.TEXT -> {
                        OutlinedTextField(
                            value = defaultValue,
                            onValueChange = { defaultValue = it },
                            label = { Text(stringResource(R.string.creator_attr_default_value)) },
                            singleLine = true
                        )
                    }
                    AttributeType.TABLE -> {
                        TableColumnsEditor(
                            columns = columns,
                            onColumnsChange = { columns = it }
                        )
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = description,
                    onValueChange = { description = it },
                    label = { Text(stringResource(R.string.creator_attr_description)) },
                    modifier = Modifier.height(80.dp),
                    maxLines = 3
                )
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    val defaultVal: Any = when (type) {
                        AttributeType.NUMERIC -> defaultValue.toDoubleOrNull() ?: 0.0
                        AttributeType.BOOLEAN -> defaultValue.toBoolean()
                        AttributeType.ENUM -> {
                            val options = enumOptionsText.split(",").map { it.trim() }.filter { it.isNotEmpty() }
                            if (options.isNotEmpty() && options.contains(defaultValue.trim())) {
                                defaultValue.trim()
                            } else {
                                options.firstOrNull() ?: ""
                            }
                        }
                        AttributeType.TEXT -> defaultValue
                        AttributeType.TABLE -> emptyList<Map<String, Any>>()
                    }
                    val enumOptions = if (type == AttributeType.ENUM) {
                        enumOptionsText.split(",").map { it.trim() }.filter { it.isNotEmpty() }
                    } else {
                        emptyList()
                    }
                    val finalColumns = if (type == AttributeType.TABLE) columns else emptyList()
                    onConfirm(
                        AttributeCategory(
                            name = name,
                            type = type,
                            minValue = if (type == AttributeType.NUMERIC) minValue.toDoubleOrNull() else null,
                            maxValue = if (type == AttributeType.NUMERIC) maxValue.toDoubleOrNull() else null,
                            defaultValue = defaultVal,
                            enumOptions = enumOptions,
                            description = description,
                            columns = finalColumns
                        )
                    )
                },
                enabled = name.isNotBlank() && (type != AttributeType.TABLE || columns.isNotEmpty())
            ) {
                Text(confirmText)
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(stringResource(R.string.cancel))
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AttributeTypeDropdown(selected: AttributeType, onSelected: (AttributeType) -> Unit) {
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded }
    ) {
        OutlinedTextField(
            value = attributeTypeLabel(selected),
            onValueChange = { },
            readOnly = true,
            label = { Text(stringResource(R.string.creator_attr_type)) },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            modifier = Modifier.fillMaxWidth().menuAnchor()
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            AttributeType.values().forEach { type ->
                DropdownMenuItem(
                    text = { Text(attributeTypeLabel(type)) },
                    onClick = {
                        onSelected(type)
                        expanded = false
                    }
                )
            }
        }
    }
}

@Composable
private fun attributeTypeLabel(type: AttributeType): String = when (type) {
    AttributeType.NUMERIC -> stringResource(R.string.creator_attr_type_numeric)
    AttributeType.BOOLEAN -> stringResource(R.string.creator_attr_type_boolean)
    AttributeType.ENUM -> stringResource(R.string.creator_attr_type_enum)
    AttributeType.TEXT -> stringResource(R.string.creator_attr_type_text)
    AttributeType.TABLE -> stringResource(R.string.creator_attr_type_label_table)
}

/**
 * TABLE 属性的列定义编辑器。每列含名称、类型（不可为 TABLE）、ENUM 列的可选项。
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TableColumnsEditor(
    columns: List<TableColumn>,
    onColumnsChange: (List<TableColumn>) -> Unit
) {
    Text(stringResource(R.string.creator_table_title), style = MaterialTheme.typography.titleSmall)
    Spacer(modifier = Modifier.height(4.dp))
    Text(
        text = stringResource(R.string.creator_table_hint),
        style = MaterialTheme.typography.bodySmall,
        color = MaterialTheme.colorScheme.onSurfaceVariant
    )
    Spacer(modifier = Modifier.height(8.dp))

    if (columns.isEmpty()) {
        Text(
            text = stringResource(R.string.creator_table_no_columns),
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }

    columns.forEachIndexed { index, col ->
        Card(
            modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)
        ) {
            Column(modifier = Modifier.padding(8.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    OutlinedTextField(
                        value = col.name,
                        onValueChange = { newName ->
                            onColumnsChange(columns.toMutableList().also {
                                it[index] = col.copy(name = newName)
                            })
                        },
                        label = { Text(stringResource(R.string.creator_column_name)) },
                        modifier = Modifier.weight(1f),
                        singleLine = true
                    )
                    IconButton(onClick = {
                        onColumnsChange(columns.toMutableList().also { it.removeAt(index) })
                    }) {
                        Icon(Icons.Default.Delete, contentDescription = stringResource(R.string.creator_delete_column), tint = MaterialTheme.colorScheme.error)
                    }
                }
                Spacer(modifier = Modifier.height(4.dp))
                // 列类型选择：枚举除 TABLE 外的四种标量类型
                val scalarTypes = AttributeType.values().filter { it != AttributeType.TABLE }
                var colTypeExpanded by remember { mutableStateOf(false) }
                ExposedDropdownMenuBox(
                    expanded = colTypeExpanded,
                    onExpandedChange = { colTypeExpanded = !colTypeExpanded }
                ) {
                    OutlinedTextField(
                        value = col.type.name,
                        onValueChange = { },
                        readOnly = true,
                        label = { Text(stringResource(R.string.creator_column_type)) },
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = colTypeExpanded) },
                        modifier = Modifier.fillMaxWidth().menuAnchor()
                    )
                    ExposedDropdownMenu(
                        expanded = colTypeExpanded,
                        onDismissRequest = { colTypeExpanded = false }
                    ) {
                        scalarTypes.forEach { t ->
                            DropdownMenuItem(
                                text = { Text(t.name) },
                                onClick = {
                                    onColumnsChange(columns.toMutableList().also {
                                        it[index] = col.copy(type = t, enumOptions = if (t == AttributeType.ENUM) col.enumOptions else emptyList())
                                    })
                                    colTypeExpanded = false
                                }
                            )
                        }
                    }
                }
                if (col.type == AttributeType.ENUM) {
                    Spacer(modifier = Modifier.height(4.dp))
                    var enumText by remember(col.name, col.enumOptions) {
                        mutableStateOf(col.enumOptions.joinToString(","))
                    }
                    OutlinedTextField(
                        value = enumText,
                        onValueChange = {
                            enumText = it
                            val opts = it.split(",").map { s -> s.trim() }.filter { it.isNotEmpty() }
                            onColumnsChange(columns.toMutableList().also { list ->
                                list[index] = col.copy(enumOptions = opts)
                            })
                        },
                        label = { Text(stringResource(R.string.creator_enum_options_comma)) },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }
    }

    Spacer(modifier = Modifier.height(4.dp))
    val newColumnName = stringResource(R.string.creator_default_column_name, columns.size + 1)
    Button(
        onClick = {
            onColumnsChange(columns + TableColumn(name = newColumnName, type = AttributeType.TEXT))
        },
        modifier = Modifier.fillMaxWidth()
    ) {
        Icon(Icons.Default.Add, contentDescription = null)
        Spacer(modifier = Modifier.width(4.dp))
        Text(stringResource(R.string.creator_add_column))
    }
}

@Composable
fun AddNPCDialog(onDismiss: () -> Unit, onAdd: (NPC) -> Unit) {
    var name by remember { mutableStateOf("") }
    var role by remember { mutableStateOf("") }
    var briefing by remember { mutableStateOf("") }
    var personality by remember { mutableStateOf("") }
    var backstory by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(stringResource(R.string.creator_add_npc_title)) },
        text = {
            Column {
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text(stringResource(R.string.creator_npc_name)) },
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = role,
                    onValueChange = { role = it },
                    label = { Text(stringResource(R.string.creator_npc_role)) },
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = briefing,
                    onValueChange = { briefing = it },
                    label = { Text(stringResource(R.string.creator_npc_brief)) },
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = personality,
                    onValueChange = { personality = it },
                    label = { Text(stringResource(R.string.creator_npc_personality_field)) },
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = backstory,
                    onValueChange = { backstory = it },
                    label = { Text(stringResource(R.string.creator_npc_backstory)) },
                    modifier = Modifier.height(100.dp),
                    maxLines = 4
                )
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onAdd(
                        NPC(
                            name = name,
                            role = role,
                            briefing = briefing,
                            personality = personality,
                            backstory = backstory,
                            sessionId = 0
                        )
                    )
                },
                enabled = name.isNotBlank() && role.isNotBlank()
            ) {
                Text(stringResource(R.string.add))
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
fun EditNPCDialog(npc: NPC, onDismiss: () -> Unit, onSave: (NPC) -> Unit) {
    var name by remember { mutableStateOf(npc.name) }
    var role by remember { mutableStateOf(npc.role) }
    var briefing by remember { mutableStateOf(npc.briefing) }
    var personality by remember { mutableStateOf(npc.personality) }
    var backstory by remember { mutableStateOf(npc.backstory) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(stringResource(R.string.creator_edit_npc_title)) },
        text = {
            Column {
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text(stringResource(R.string.creator_npc_name)) },
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = role,
                    onValueChange = { role = it },
                    label = { Text(stringResource(R.string.creator_npc_role)) },
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = briefing,
                    onValueChange = { briefing = it },
                    label = { Text(stringResource(R.string.creator_npc_brief)) },
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = personality,
                    onValueChange = { personality = it },
                    label = { Text(stringResource(R.string.creator_npc_personality_field)) },
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = backstory,
                    onValueChange = { backstory = it },
                    label = { Text(stringResource(R.string.creator_npc_backstory)) },
                    modifier = Modifier.height(100.dp),
                    maxLines = 4
                )
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onSave(
                        npc.copy(
                            name = name,
                            role = role,
                            briefing = briefing,
                            personality = personality,
                            backstory = backstory
                        )
                    )
                },
                enabled = name.isNotBlank() && role.isNotBlank()
            ) {
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
