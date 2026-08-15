package com.textgame.presentation.ui.settings

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
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Restore
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Divider
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.textgame.R
import com.textgame.data.local.SettingsPreferences
import com.textgame.presentation.viewmodel.SettingsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    onBack: () -> Unit
) {
    val viewModel: SettingsViewModel = viewModel()
    val uiState by viewModel.uiState.collectAsState(initial = com.textgame.presentation.viewmodel.SettingsUiState())

    var showSavedDialog by remember { mutableStateOf(false) }
    var showModelPicker by remember { mutableStateOf(false) }
    var showImageModelPicker by remember { mutableStateOf(false) }

    LaunchedEffect(uiState.saved) {
        if (uiState.saved) {
            showSavedDialog = true
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.settings_title)) },
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
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {
            // === API 连接设置 ===
            Text(stringResource(R.string.settings_api_connection), style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = uiState.apiKey,
                onValueChange = viewModel::updateApiKey,
                label = { Text("API Key") },
                placeholder = { Text("sk-...") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = uiState.baseUrl,
                onValueChange = viewModel::updateBaseUrl,
                label = { Text(stringResource(R.string.settings_api_address)) },
                placeholder = { Text("https://api.deepseek.com/") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = uiState.model,
                onValueChange = viewModel::updateModel,
                label = { Text(stringResource(R.string.settings_model_name)) },
                placeholder = { Text("deepseek-v4-flash") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                readOnly = false,
                trailingIcon = {
                    TextButton(onClick = { showModelPicker = true }) {
                        Text(stringResource(R.string.settings_choose))
                    }
                }
            )

            Divider(modifier = Modifier.padding(vertical = 16.dp))

            // === 对话参数 ===
            Text(stringResource(R.string.settings_dialogue_params), style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(8.dp))

            // 思考模式开关（仅 v4 模型支持）
            val supportsThinking = uiState.model.contains("v4")
            if (supportsThinking) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(stringResource(R.string.settings_thinking_mode), style = MaterialTheme.typography.bodyMedium)
                        Text(
                            stringResource(R.string.settings_thinking_mode_desc),
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                    Switch(
                        checked = uiState.thinkingEnabled,
                        onCheckedChange = viewModel::updateThinkingEnabled
                    )
                }
                Spacer(modifier = Modifier.height(12.dp))
            }

            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant
                )
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    if (uiState.thinkingEnabled && supportsThinking) {
                        // 思考模式：显示思考强度选择
                        Text(
                            stringResource(R.string.settings_thinking_effort, uiState.reasoningEffort),
                            style = MaterialTheme.typography.bodyMedium
                        )
                        Text(
                            stringResource(R.string.settings_thinking_effort_desc),
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            listOf("high", "max").forEach { effort ->
                                FilterChip(
                                    selected = uiState.reasoningEffort == effort,
                                    onClick = { viewModel.updateReasoningEffort(effort) },
                                    label = { Text(effort) },
                                    modifier = Modifier.weight(1f)
                                )
                            }
                        }
                    } else {
                        // 普通模式：Temperature 滑块
                        Text(
                            "Temperature: ${"%.1f".format(uiState.dialogueTemperature)}",
                            style = MaterialTheme.typography.bodyMedium
                        )
                        Text(
                            stringResource(R.string.settings_temperature_desc),
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Slider(
                            value = uiState.dialogueTemperature,
                            onValueChange = viewModel::updateDialogueTemperature,
                            valueRange = 0f..2f,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        "Max Tokens: ${uiState.dialogueMaxTokens}",
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Text(
                        stringResource(R.string.settings_max_tokens_desc),
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Slider(
                        value = uiState.dialogueMaxTokens.toFloat(),
                        onValueChange = { viewModel.updateDialogueMaxTokens(it.toInt()) },
                        valueRange = 256f..uiState.dialogueMaxTokensLimit.toFloat(),
                        modifier = Modifier.fillMaxWidth()
                    )
                    Text(
                        text = stringResource(R.string.settings_limit, uiState.dialogueMaxTokensLimit),
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            Divider(modifier = Modifier.padding(vertical = 16.dp))

            // === 总结参数 ===
            Text(stringResource(R.string.settings_summary_params), style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(8.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant
                )
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    if (uiState.thinkingEnabled && supportsThinking) {
                        // 思考模式：显示思考强度选择
                        Text(
                            stringResource(R.string.settings_thinking_effort, uiState.reasoningEffort),
                            style = MaterialTheme.typography.bodyMedium
                        )
                        Text(
                            stringResource(R.string.settings_summary_thinking_desc),
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            listOf("high", "max").forEach { effort ->
                                FilterChip(
                                    selected = uiState.reasoningEffort == effort,
                                    onClick = { viewModel.updateReasoningEffort(effort) },
                                    label = { Text(effort) },
                                    modifier = Modifier.weight(1f)
                                )
                            }
                        }
                    } else {
                        // 普通模式：Temperature 滑块
                        Text(
                            "Temperature: ${"%.1f".format(uiState.summaryTemperature)}",
                            style = MaterialTheme.typography.bodyMedium
                        )
                        Text(
                            stringResource(R.string.settings_summary_temperature_desc),
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Slider(
                            value = uiState.summaryTemperature,
                            onValueChange = viewModel::updateSummaryTemperature,
                            valueRange = 0f..2f,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        "Max Tokens: ${uiState.summaryMaxTokens}",
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Text(
                        stringResource(R.string.settings_summary_max_tokens_desc),
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Slider(
                        value = uiState.summaryMaxTokens.toFloat(),
                        onValueChange = { viewModel.updateSummaryMaxTokens(it.toInt()) },
                        valueRange = 256f..uiState.summaryMaxTokensLimit.toFloat(),
                        modifier = Modifier.fillMaxWidth()
                    )
                    Text(
                        text = stringResource(R.string.settings_limit, uiState.summaryMaxTokensLimit),
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // === 生图设置 ===
            Text(stringResource(R.string.settings_image_section), style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                stringResource(R.string.settings_image_section_desc),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = uiState.imageApiKey,
                onValueChange = viewModel::updateImageApiKey,
                label = { Text(stringResource(R.string.settings_image_api_key)) },
                placeholder = { Text(stringResource(R.string.settings_image_api_key_placeholder)) },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = uiState.imageBaseUrl,
                onValueChange = viewModel::updateImageBaseUrl,
                label = { Text(stringResource(R.string.settings_image_domain)) },
                placeholder = { Text("ark.ap-southeast.bytepluses.com") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = uiState.imageModel,
                onValueChange = viewModel::updateImageModel,
                label = { Text(stringResource(R.string.settings_image_model)) },
                placeholder = { Text("doubao-seedream-4-0-250828") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                trailingIcon = {
                    TextButton(onClick = { showImageModelPicker = true }) {
                        Text(stringResource(R.string.settings_choose))
                    }
                }
            )

            Divider(modifier = Modifier.padding(vertical = 16.dp))

            // === 操作按钮 ===
            Button(
                onClick = { viewModel.saveSettings() },
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(Icons.Default.Save, contentDescription = null)
                Spacer(modifier = Modifier.height(4.dp))
                Text(stringResource(R.string.settings_save))
            }

            Spacer(modifier = Modifier.height(8.dp))

            TextButton(
                onClick = { viewModel.resetToDefaults() },
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(Icons.Default.Restore, contentDescription = null)
                Spacer(modifier = Modifier.height(4.dp))
                Text(stringResource(R.string.settings_restore_defaults))
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }

    if (showSavedDialog) {
        AlertDialog(
            onDismissRequest = {
                showSavedDialog = false
                onBack()
            },
            title = { Text(stringResource(R.string.settings_saved_title)) },
            text = { Text(stringResource(R.string.settings_saved_message)) },
            confirmButton = {
                TextButton(onClick = {
                    showSavedDialog = false
                    onBack()
                }) {
                    Text(stringResource(R.string.ok))
                }
            }
        )
    }

    if (showModelPicker) {
        AlertDialog(
            onDismissRequest = { showModelPicker = false },
            title = { Text(stringResource(R.string.settings_choose_model)) },
            text = {
                Column {
                    SettingsPreferences.PRESET_MODELS.forEach { model ->
                        TextButton(
                            onClick = {
                                viewModel.updateModel(model)
                                showModelPicker = false
                            },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(model)
                        }
                    }
                }
            },
            confirmButton = {
                TextButton(onClick = { showModelPicker = false }) {
                    Text(stringResource(R.string.cancel))
                }
            }
        )
    }

    if (showImageModelPicker) {
        AlertDialog(
            onDismissRequest = { showImageModelPicker = false },
            title = { Text(stringResource(R.string.settings_choose_image_model)) },
            text = {
                Column {
                    SettingsPreferences.IMAGE_PRESET_MODELS.forEach { model ->
                        TextButton(
                            onClick = {
                                viewModel.updateImageModel(model)
                                showImageModelPicker = false
                            },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(model)
                        }
                    }
                }
            },
            confirmButton = {
                TextButton(onClick = { showImageModelPicker = false }) {
                    Text(stringResource(R.string.cancel))
                }
            }
        )
    }
}
