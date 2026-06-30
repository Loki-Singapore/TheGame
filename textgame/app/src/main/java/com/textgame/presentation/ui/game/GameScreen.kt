package com.textgame.presentation.ui.game

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.halilibo.richtext.markdown.Markdown
import com.halilibo.richtext.ui.material3.RichText
import com.textgame.presentation.viewmodel.DialogueDisplay
import com.textgame.presentation.viewmodel.GameViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GameScreen(
    sessionId: Long,
    onBack: () -> Unit,
    onOpenSettings: () -> Unit
) {
    val context = LocalContext.current
    val viewModel: GameViewModel = viewModel(
        factory = GameViewModelFactory(sessionId, context)
    )
    val uiState by viewModel.uiState.collectAsState(initial = com.textgame.presentation.viewmodel.GameUiState())

    var inputText by remember { mutableStateOf("") }
    val listState = rememberLazyListState()
    var showStatusPanel by remember { mutableStateOf(false) }

    LaunchedEffect(uiState.pendingRegeneratePrompt) {
        uiState.pendingRegeneratePrompt?.let { prompt ->
            inputText = prompt
            viewModel.consumePendingRegeneratePrompt()
        }
    }

    LaunchedEffect(uiState.dialogues.size) {
        if (uiState.dialogues.isNotEmpty()) {
            listState.animateScrollToItem(uiState.dialogues.size - 1)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(uiState.gameState?.currentScene ?: "游戏中")
                        Text(
                            text = "第 ${uiState.gameState?.turnCount ?: 0} 轮",
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "返回")
                    }
                },
                actions = {
                    TextButton(onClick = { showStatusPanel = true }) {
                        Text("状态")
                    }
                    TextButton(onClick = onOpenSettings) {
                        Text("设定")
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
            LazyColumn(
                state = listState,
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                itemsIndexed(uiState.dialogues) { _, dialogue ->
                    DialogueItem(
                        dialogue = dialogue,
                        onRegenerate = { turn ->
                            viewModel.regenerateFromTurn(turn)
                        }
                    )
                }

                if (uiState.isLoading) {
                    item {
                        Box(
                            modifier = Modifier.fillMaxWidth(),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator()
                        }
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

            if (uiState.choices.isNotEmpty() && !uiState.isLoading) {
                var choicesExpanded by remember { mutableStateOf(true) }
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surfaceVariant
                    ),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "你可以选择 (${uiState.choices.size})",
                                style = MaterialTheme.typography.labelMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            TextButton(onClick = { choicesExpanded = !choicesExpanded }) {
                                Text(if (choicesExpanded) "收起" else "展开")
                            }
                        }
                        if (choicesExpanded) {
                            Spacer(modifier = Modifier.height(8.dp))
                            uiState.choices.forEach { choice ->
                                Button(
                                    onClick = {
                                        viewModel.sendMessage(choice)
                                        inputText = ""
                                    },
                                    modifier = Modifier.fillMaxWidth(),
                                    shape = RoundedCornerShape(12.dp)
                                ) {
                                    Text(text = choice)
                                }
                                Spacer(modifier = Modifier.height(6.dp))
                            }
                            TextButton(
                                onClick = { },
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text("...或者自由输入你的行动")
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    value = inputText,
                    onValueChange = { inputText = it },
                    placeholder = { Text("输入你的行动或对话...") },
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(24.dp)
                )
                Button(
                    onClick = {
                        viewModel.sendMessage(inputText)
                        inputText = ""
                    },
                    enabled = inputText.isNotBlank() && !uiState.isLoading
                ) {
                    Text("发送")
                }
            }
        }
    }

    if (showStatusPanel) {
        StatusPanelDialog(
            viewModel = viewModel,
            onDismiss = { showStatusPanel = false }
        )
    }
}

@Composable
fun DialogueItem(
    dialogue: com.textgame.presentation.viewmodel.DialogueDisplay,
    onRegenerate: (Int) -> Unit
) {
    var showMenu by remember { mutableStateOf(false) }

    when {
        dialogue.isNarrative -> {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { showMenu = true },
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant
                )
            ) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Text(
                        text = dialogue.content,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    dialogue.tokenUsage?.let { usage ->
                        Spacer(modifier = Modifier.height(4.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.End
                        ) {
                            Text(
                                text = "${usage.totalTokens} tokens",
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f)
                            )
                        }
                    }
                    Box {
                        DropdownMenu(
                            expanded = showMenu,
                            onDismissRequest = { showMenu = false }
                        ) {
                            DropdownMenuItem(
                                text = { Text("从此轮重新生成") },
                                onClick = {
                                    showMenu = false
                                    onRegenerate(dialogue.turnNumber)
                                }
                            )
                        }
                    }
                }
            }
        }
        dialogue.isPlayer -> {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                Box {
                    Box(
                        modifier = Modifier
                            .widthIn(max = 280.dp)
                            .background(
                                color = MaterialTheme.colorScheme.primary,
                                shape = RoundedCornerShape(16.dp)
                            )
                            .padding(12.dp)
                            .then(
                                if (showMenu) Modifier else Modifier.clickable { showMenu = true }
                            )
                    ) {
                        Text(
                            text = dialogue.content,
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                    DropdownMenu(
                        expanded = showMenu,
                        onDismissRequest = { showMenu = false }
                    ) {
                        DropdownMenuItem(
                            text = { Text("从此轮重新生成") },
                            onClick = {
                                showMenu = false
                                onRegenerate(dialogue.turnNumber)
                            }
                        )
                    }
                }
            }
        }
        else -> {
            Column(modifier = Modifier.fillMaxWidth()) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = dialogue.speaker,
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.padding(start = 4.dp, bottom = 4.dp)
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Box {
                        TextButton(onClick = { showMenu = true }) {
                            Text("...", style = MaterialTheme.typography.titleMedium)
                        }
                        DropdownMenu(
                            expanded = showMenu,
                            onDismissRequest = { showMenu = false }
                        ) {
                            DropdownMenuItem(
                                text = { Text("从此轮重新生成") },
                                onClick = {
                                    showMenu = false
                                    onRegenerate(dialogue.turnNumber)
                                }
                            )
                        }
                    }
                }
                Box(
                    modifier = Modifier
                        .widthIn(max = 280.dp)
                        .background(
                            color = MaterialTheme.colorScheme.surfaceVariant,
                            shape = RoundedCornerShape(16.dp)
                        )
                        .padding(12.dp)
                ) {
                    Text(text = dialogue.content)
                }
            }
        }
    }
}

@Composable
fun StatusPanelDialog(viewModel: GameViewModel, onDismiss: () -> Unit) {
    val uiState by viewModel.uiState.collectAsState(initial = com.textgame.presentation.viewmodel.GameUiState())
    val scrollState = rememberScrollState()

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("状态面板") },
        text = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(max = 480.dp)
                    .verticalScroll(scrollState)
            ) {
                uiState.protagonist?.let { protag ->
                    Text("主角: ${protag.name}", style = MaterialTheme.typography.titleMedium)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text("位置: ${protag.location}")
                    if (protag.attributes.isNotEmpty()) {
                        Spacer(modifier = Modifier.height(8.dp))
                        Text("属性:")
                        protag.attributes.forEach { (key, value) ->
                            Text("  $key: $value")
                        }
                    }
                    if (protag.inventory.isNotEmpty()) {
                        Spacer(modifier = Modifier.height(8.dp))
                        Text("物品: ${protag.inventory.joinToString("、")}")
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                if (uiState.npcs.isNotEmpty()) {
                    Text("NPC:", style = MaterialTheme.typography.titleMedium)
                    uiState.npcs.forEach { npc ->
                        Spacer(modifier = Modifier.height(4.dp))
                        Text("${npc.name} (${npc.role})")
                        if (npc.briefing.isNotEmpty()) {
                            Text("  简介: ${npc.briefing}")
                        }
                        Text("  情绪: ${npc.mood}")
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                uiState.worldSetting?.let { world ->
                    if (world.worldRules.isNotEmpty()) {
                        Text("世界观细则:", style = MaterialTheme.typography.titleMedium)
                        Spacer(modifier = Modifier.height(4.dp))
                        world.worldRules.forEach { rule ->
                            Text("• ${rule.content}", style = MaterialTheme.typography.bodySmall)
                        }
                        Spacer(modifier = Modifier.height(16.dp))
                    }
                }

                uiState.summary?.let { summary ->
                    if (summary.summaryText.isNotEmpty()) {
                        Text("进度总结:", style = MaterialTheme.typography.titleMedium)
                        Spacer(modifier = Modifier.height(4.dp))
                        RichText {
                            Markdown(summary.summaryText)
                        }
                    }
                }
            }
        },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text("关闭")
            }
        }
    )
}

class GameViewModelFactory(
    private val sessionId: Long,
    private val context: Context
) : androidx.lifecycle.ViewModelProvider.Factory {
    override fun <T : androidx.lifecycle.ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(GameViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return GameViewModel(sessionId, context) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
