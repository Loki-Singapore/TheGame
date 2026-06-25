package com.textgame.presentation.ui.settings

import androidx.compose.foundation.layout.Column
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
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
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

    LaunchedEffect(uiState.saved) {
        if (uiState.saved) {
            showSavedDialog = true
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("AI 设置") },
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
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {
            // === API 连接设置 ===
            Text("API 连接", style = MaterialTheme.typography.titleMedium)
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
                label = { Text("API 地址") },
                placeholder = { Text("https://api.deepseek.com/") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = uiState.model,
                onValueChange = viewModel::updateModel,
                label = { Text("模型名称") },
                placeholder = { Text("deepseek-v4-flash") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                readOnly = false,
                trailingIcon = {
                    TextButton(onClick = { showModelPicker = true }) {
                        Text("选择")
                    }
                }
            )

            Divider(modifier = Modifier.padding(vertical = 16.dp))

            // === 对话参数 ===
            Text("对话参数", style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(8.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant
                )
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        "Temperature: ${"%.1f".format(uiState.dialogueTemperature)}",
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Text(
                        "控制回复的随机性，值越高越有创意",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Slider(
                        value = uiState.dialogueTemperature,
                        onValueChange = viewModel::updateDialogueTemperature,
                        valueRange = 0f..2f,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        "Max Tokens: ${uiState.dialogueMaxTokens}",
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Text(
                        "单次回复的最大长度",
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
                        text = "上限: ${uiState.dialogueMaxTokensLimit}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            Divider(modifier = Modifier.padding(vertical = 16.dp))

            // === 总结参数 ===
            Text("总结参数", style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(8.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant
                )
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        "Temperature: ${"%.1f".format(uiState.summaryTemperature)}",
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Text(
                        "总结生成时建议使用较低值以保持准确性",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Slider(
                        value = uiState.summaryTemperature,
                        onValueChange = viewModel::updateSummaryTemperature,
                        valueRange = 0f..2f,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        "Max Tokens: ${uiState.summaryMaxTokens}",
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Text(
                        "总结内容的最大长度",
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
                        text = "上限: ${uiState.summaryMaxTokensLimit}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // === 操作按钮 ===
            Button(
                onClick = { viewModel.saveSettings() },
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(Icons.Default.Save, contentDescription = null)
                Spacer(modifier = Modifier.height(4.dp))
                Text("保存设置")
            }

            Spacer(modifier = Modifier.height(8.dp))

            TextButton(
                onClick = { viewModel.resetToDefaults() },
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(Icons.Default.Restore, contentDescription = null)
                Spacer(modifier = Modifier.height(4.dp))
                Text("恢复默认值")
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
            title = { Text("保存成功") },
            text = { Text("AI 设置已保存并生效。") },
            confirmButton = {
                TextButton(onClick = {
                    showSavedDialog = false
                    onBack()
                }) {
                    Text("确定")
                }
            }
        )
    }

    if (showModelPicker) {
        AlertDialog(
            onDismissRequest = { showModelPicker = false },
            title = { Text("选择模型") },
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
                    Text("取消")
                }
            }
        )
    }
}
