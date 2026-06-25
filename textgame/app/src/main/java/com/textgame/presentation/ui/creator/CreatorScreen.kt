package com.textgame.presentation.ui.creator

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.textgame.domain.model.AttributeCategory
import com.textgame.domain.model.AttributeType
import com.textgame.domain.model.NPC
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

    val steps = listOf("基本信息", "世界观", "角色属性", "NPC设置", "完成")

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("创建新世界") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "返回")
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
                        Text("上一步")
                    }
                }
                if (currentStep < steps.size - 1) {
                    Button(
                        onClick = { currentStep++ },
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("下一步")
                    }
                } else {
                    Button(
                        onClick = { viewModel.createGame() },
                        modifier = Modifier.weight(1f),
                        enabled = !uiState.isCreating
                    ) {
                        Text(if (uiState.isCreating) "创建中..." else "创建游戏")
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
            title = { Text("创建成功") },
            text = { Text("游戏世界已创建完成！") },
            confirmButton = {
                TextButton(onClick = { onGameCreated(sessionId) }) {
                    Text("开始游戏")
                }
            }
        )
    }

    if (showAttributeDialog) {
        AddAttributeDialog(
            onDismiss = { showAttributeDialog = false },
            onAdd = { category ->
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
                text = "一句话生成世界",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "描述你想要的游戏世界，AI会自动生成所有设定",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = uiState.generationPrompt,
                onValueChange = viewModel::updateGenerationPrompt,
                label = { Text("例如：一个蒸汽朋克风格的侦探故事，主角是私家侦探") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )
            Spacer(modifier = Modifier.height(8.dp))
            Button(
                onClick = viewModel::generateWorldFromPrompt,
                modifier = Modifier.fillMaxWidth(),
                enabled = !uiState.isGenerating && uiState.generationPrompt.isNotBlank()
            ) {
                Text(if (uiState.isGenerating) "生成中..." else "AI生成")
            }
        }
    }

    Spacer(modifier = Modifier.height(24.dp))

    Text("或手动填写", style = MaterialTheme.typography.titleSmall)
    Spacer(modifier = Modifier.height(12.dp))

    OutlinedTextField(
        value = uiState.gameName,
        onValueChange = viewModel::updateGameName,
        label = { Text("游戏名称") },
        modifier = Modifier.fillMaxWidth(),
        singleLine = true
    )

    Spacer(modifier = Modifier.height(16.dp))

    OutlinedTextField(
        value = uiState.protagonistName,
        onValueChange = viewModel::updateProtagonistName,
        label = { Text("主角名称") },
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
        label = { Text("世界名称") },
        modifier = Modifier.fillMaxWidth(),
        singleLine = true
    )

    Spacer(modifier = Modifier.height(12.dp))

    WorldTypeDropdown(viewModel)

    Spacer(modifier = Modifier.height(12.dp))

    OutlinedTextField(
        value = uiState.worldDescription,
        onValueChange = viewModel::updateWorldDescription,
        label = { Text("世界描述") },
        modifier = Modifier.fillMaxWidth().height(120.dp),
        maxLines = 5
    )

    Spacer(modifier = Modifier.height(12.dp))

    OutlinedTextField(
        value = uiState.timeSetting,
        onValueChange = viewModel::updateTimeSetting,
        label = { Text("时间设定") },
        modifier = Modifier.fillMaxWidth(),
        singleLine = true
    )

    Spacer(modifier = Modifier.height(12.dp))

    OutlinedTextField(
        value = uiState.locationSetting,
        onValueChange = viewModel::updateLocationSetting,
        label = { Text("起始地点") },
        modifier = Modifier.fillMaxWidth(),
        singleLine = true
    )

    Spacer(modifier = Modifier.height(12.dp))

    OutlinedTextField(
        value = uiState.socialStructure,
        onValueChange = viewModel::updateSocialStructure,
        label = { Text("社会结构") },
        modifier = Modifier.fillMaxWidth(),
        singleLine = true
    )

    Spacer(modifier = Modifier.height(12.dp))

    SpecialRulesSection(viewModel)

    Spacer(modifier = Modifier.height(12.dp))

    OutlinedTextField(
        value = uiState.lore,
        onValueChange = viewModel::updateLore,
        label = { Text("世界观历史/Lore") },
        modifier = Modifier.fillMaxWidth().height(100.dp),
        maxLines = 4
    )

    Spacer(modifier = Modifier.height(12.dp))

    OutlinedTextField(
        value = uiState.protagonistBackground,
        onValueChange = viewModel::updateProtagonistBackground,
        label = { Text("主角背景故事") },
        modifier = Modifier.fillMaxWidth().height(100.dp),
        maxLines = 4
    )

    Spacer(modifier = Modifier.height(12.dp))

    OutlinedTextField(
        value = uiState.worldHistory,
        onValueChange = viewModel::updateWorldHistory,
        label = { Text("世界历史") },
        modifier = Modifier.fillMaxWidth().height(100.dp),
        maxLines = 4
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WorldTypeDropdown(viewModel: CreatorViewModel) {
    val uiState by viewModel.uiState.collectAsState(initial = com.textgame.presentation.viewmodel.CreatorUiState())
    var expanded by remember { mutableStateOf(false) }
    val worldTypes = listOf("奇幻", "科幻", "现代", "末日", "武侠", "都市", "历史", "其他")

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded }
    ) {
        OutlinedTextField(
            value = uiState.worldType,
            onValueChange = { },
            readOnly = true,
            label = { Text("世界类型") },
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

    Text("特殊规则", style = MaterialTheme.typography.titleSmall)

    Spacer(modifier = Modifier.height(8.dp))

    uiState.specialRules.forEachIndexed { index, rule ->
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("• $rule", modifier = Modifier.weight(1f))
            IconButton(onClick = { viewModel.removeSpecialRule(index) }) {
                Icon(Icons.Default.Delete, contentDescription = "删除")
            }
        }
    }

    Spacer(modifier = Modifier.height(8.dp))

    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        OutlinedTextField(
            value = ruleText,
            onValueChange = { ruleText = it },
            placeholder = { Text("添加规则") },
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
            Icon(Icons.Default.Add, contentDescription = "添加")
        }
    }
}

@Composable
fun AttributeStep(viewModel: CreatorViewModel, onAddAttribute: () -> Unit) {
    val uiState by viewModel.uiState.collectAsState(initial = com.textgame.presentation.viewmodel.CreatorUiState())

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text("角色属性类目", style = MaterialTheme.typography.titleMedium)
        TextButton(onClick = onAddAttribute) {
            Icon(Icons.Default.Add, contentDescription = null)
            Spacer(modifier = Modifier.height(4.dp))
            Text("添加属性")
        }
    }

    Spacer(modifier = Modifier.height(8.dp))

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
                    Text(
                        text = "类型: ${category.type.name} | 默认值: ${category.defaultValue}",
                        style = MaterialTheme.typography.bodySmall
                    )
                }
                IconButton(onClick = { viewModel.removeAttributeCategory(index) }) {
                    Icon(Icons.Default.Delete, contentDescription = "删除")
                }
            }
        }
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
        Text("初始NPC", style = MaterialTheme.typography.titleMedium)
        TextButton(onClick = onAddNPC) {
            Icon(Icons.Default.Add, contentDescription = null)
            Spacer(modifier = Modifier.height(4.dp))
            Text("添加NPC")
        }
    }

    Spacer(modifier = Modifier.height(8.dp))

    if (uiState.npcs.isEmpty()) {
        Text(
            text = "暂无NPC，点击上方按钮添加",
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
                            text = "性格: ${npc.personality}",
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }
                IconButton(onClick = {
                    editingNpcIndex = index
                    editingNpc = npc
                }) {
                    Icon(Icons.Default.Edit, contentDescription = "编辑", tint = MaterialTheme.colorScheme.primary)
                }
                IconButton(onClick = { viewModel.removeNPC(index) }) {
                    Icon(Icons.Default.Delete, contentDescription = "删除", tint = MaterialTheme.colorScheme.error)
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

    Text("游戏设置概览", style = MaterialTheme.typography.titleLarge)

    Spacer(modifier = Modifier.height(16.dp))

    Card(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("游戏名称: ${uiState.gameName}")
            Text("主角: ${uiState.protagonistName}")
            Text("世界: ${uiState.worldName}")
            Text("类型: ${uiState.worldType}")
            Text("属性数量: ${uiState.attributeCategories.size}")
            Text("NPC数量: ${uiState.npcs.size}")
        }
    }

    Spacer(modifier = Modifier.height(16.dp))

    Text(
        text = "点击「创建游戏」开始你的冒险！",
        style = MaterialTheme.typography.bodyMedium,
        color = MaterialTheme.colorScheme.onSurfaceVariant
    )
}

@Composable
fun AddAttributeDialog(onDismiss: () -> Unit, onAdd: (AttributeCategory) -> Unit) {
    var name by remember { mutableStateOf("") }
    var type by remember { mutableStateOf(AttributeType.NUMERIC) }
    var defaultValue by remember { mutableStateOf("") }
    var minValue by remember { mutableStateOf("") }
    var maxValue by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("添加属性") },
        text = {
            Column {
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("属性名称") },
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(8.dp))

                AttributeTypeDropdown(selected = type, onSelected = { type = it })

                Spacer(modifier = Modifier.height(8.dp))

                if (type == AttributeType.NUMERIC) {
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        OutlinedTextField(
                            value = minValue,
                            onValueChange = { minValue = it },
                            label = { Text("最小值") },
                            modifier = Modifier.weight(1f),
                            singleLine = true
                        )
                        OutlinedTextField(
                            value = maxValue,
                            onValueChange = { maxValue = it },
                            label = { Text("最大值") },
                            modifier = Modifier.weight(1f),
                            singleLine = true
                        )
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = defaultValue,
                    onValueChange = { defaultValue = it },
                    label = { Text("默认值") },
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = description,
                    onValueChange = { description = it },
                    label = { Text("描述") },
                    singleLine = true
                )
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    val defaultVal = when (type) {
                        AttributeType.NUMERIC -> defaultValue.toDoubleOrNull() ?: 0.0
                        AttributeType.BOOLEAN -> defaultValue.toBoolean()
                        else -> defaultValue
                    }
                    onAdd(
                        AttributeCategory(
                            name = name,
                            type = type,
                            minValue = minValue.toDoubleOrNull(),
                            maxValue = maxValue.toDoubleOrNull(),
                            defaultValue = defaultVal,
                            description = description
                        )
                    )
                },
                enabled = name.isNotBlank()
            ) {
                Text("添加")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("取消")
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
            value = selected.name,
            onValueChange = { },
            readOnly = true,
            label = { Text("属性类型") },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            modifier = Modifier.fillMaxWidth().menuAnchor()
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            AttributeType.values().forEach { type ->
                DropdownMenuItem(
                    text = { Text(type.name) },
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
fun AddNPCDialog(onDismiss: () -> Unit, onAdd: (NPC) -> Unit) {
    var name by remember { mutableStateOf("") }
    var role by remember { mutableStateOf("") }
    var briefing by remember { mutableStateOf("") }
    var personality by remember { mutableStateOf("") }
    var backstory by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("添加NPC") },
        text = {
            Column {
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("NPC名称") },
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = role,
                    onValueChange = { role = it },
                    label = { Text("身份/职业") },
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = briefing,
                    onValueChange = { briefing = it },
                    label = { Text("简介（一句话描述）") },
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = personality,
                    onValueChange = { personality = it },
                    label = { Text("性格特点") },
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = backstory,
                    onValueChange = { backstory = it },
                    label = { Text("背景故事") },
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
                Text("添加")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("取消")
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
        title = { Text("编辑NPC") },
        text = {
            Column {
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("NPC名称") },
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = role,
                    onValueChange = { role = it },
                    label = { Text("身份/职业") },
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = briefing,
                    onValueChange = { briefing = it },
                    label = { Text("简介（一句话描述）") },
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = personality,
                    onValueChange = { personality = it },
                    label = { Text("性格特点") },
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = backstory,
                    onValueChange = { backstory = it },
                    label = { Text("背景故事") },
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
                Text("保存")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("取消")
            }
        }
    )
}
