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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.AutoStories
import androidx.compose.material.icons.filled.Backpack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Public
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.halilibo.richtext.markdown.Markdown
import com.halilibo.richtext.ui.RichText
import com.textgame.BuildConfig
import com.textgame.domain.model.AttributeCategory
import com.textgame.domain.model.AttributeType
import com.textgame.domain.model.NPC
import com.textgame.domain.model.Protagonist
import com.textgame.presentation.ui.imagegen.ImageGenerationDialog
import com.textgame.presentation.viewmodel.DialogueDisplay
import com.textgame.presentation.viewmodel.GameViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

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
    val scope = rememberCoroutineScope()
    var showStatusPanel by remember { mutableStateOf(false) }
    var showImageGenerationDialog by remember { mutableStateOf(false) }

    // 取最近一段旁白作为生图场景上下文；无旁白时回退到当前场景名
    val sceneNarrativeForImage = remember(uiState.dialogues, uiState.gameState?.currentScene) {
        uiState.dialogues.lastOrNull { it.isNarrative }?.content
            ?: uiState.gameState?.currentScene
            ?: ""
    }

    // 用户是否主动上滑离开底部：可被发送消息重置
    var isUserScrolledUp by remember { mutableStateOf(false) }

    // 离开底部的判定阈值
    val scrollUpThresholdPx = with(LocalDensity.current) { 24.dp.toPx() }.toInt()

    // 仅在滚动停止时（用户松手）判定"上滑"：
    // 流式内容增长会改变 lastItemBottom 但不会触发 isScrollInProgress，
    // 因此用 isScrollInProgress 区分"用户拖动"与"内容增长"，避免误判。
    // 程序滚动用瞬时 scrollToItem（不触发 isScrollInProgress），故该信号仅由用户拖动产生。
    LaunchedEffect(listState, scrollUpThresholdPx) {
        snapshotFlow { listState.isScrollInProgress }
            .distinctUntilChanged()
            .filter { !it }
            .collect {
                val layoutInfo = listState.layoutInfo
                val lastVisible = layoutInfo.visibleItemsInfo.lastOrNull() ?: return@collect
                val viewportHeight = layoutInfo.viewportSize.height
                val lastItemBottom = lastVisible.offset + lastVisible.size
                val atBottom = lastVisible.index >= uiState.dialogues.size - 1 &&
                    lastItemBottom <= viewportHeight + scrollUpThresholdPx
                isUserScrolledUp = !atBottom
            }
    }

    LaunchedEffect(sessionId) {
        snapshotFlow { uiState.dialogues.size }
            .filter { it > 0 }
            .first()
            .let {
                listState.scrollToItem(uiState.dialogues.size - 1, Int.MAX_VALUE)
            }
    }

    LaunchedEffect(uiState.pendingRegeneratePrompt) {
        uiState.pendingRegeneratePrompt?.let { prompt ->
            inputText = prompt
            viewModel.consumePendingRegeneratePrompt()
        }
    }

    // 新增对话时追底：瞬时滚动到底部，避免 animateScrollToItem 触发 isScrollInProgress 干扰判定
    LaunchedEffect(uiState.dialogues.size) {
        if (uiState.dialogues.isNotEmpty() && !isUserScrolledUp && !listState.isScrollInProgress) {
            listState.scrollToItem(uiState.dialogues.size - 1, Int.MAX_VALUE)
        }
    }

    // 流式更新时高频追底：瞬时滚动 + 大偏移量，确保 item 底部（最新文字）对齐 viewport 底部
    // isUserScrolledUp 作为 key：用户上滑时立即取消挂起的 scrollToItem，避免松手后弹回
    // !isScrollInProgress：用户拖动期间跳过，避免与拖动抢位置
    LaunchedEffect(uiState.dialogues.lastOrNull()?.content, uiState.isStreaming, isUserScrolledUp) {
        if (uiState.dialogues.isNotEmpty() && !isUserScrolledUp && !listState.isScrollInProgress) {
            listState.scrollToItem(uiState.dialogues.size - 1, Int.MAX_VALUE)
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
                        },
                        onGenerateSceneImage = { showImageGenerationDialog = true }
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

            if (BuildConfig.DEBUG) {
                uiState.debugLog?.let { debugLog ->
                    var expanded by remember { mutableStateOf(true) }
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 4.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.tertiaryContainer
                        ),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Column(modifier = Modifier.padding(8.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "调试信息",
                                    style = MaterialTheme.typography.labelMedium,
                                    color = MaterialTheme.colorScheme.onTertiaryContainer
                                )
                                Row {
                                    TextButton(onClick = {
                                        // 复制到剪贴板
                                        val clipboard = context.getSystemService(android.content.Context.CLIPBOARD_SERVICE) as android.content.ClipboardManager
                                        clipboard.setPrimaryClip(android.content.ClipData.newPlainText("debug", debugLog))
                                    }) { Text("复制") }
                                    TextButton(onClick = { expanded = !expanded }) {
                                        Text(if (expanded) "收起" else "展开")
                                    }
                                }
                            }
                            if (expanded) {
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = debugLog,
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onTertiaryContainer,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .verticalScroll(rememberScrollState())
                                        .heightIn(max = 320.dp)
                                )
                            }
                        }
                    }
                }
            }

            if (uiState.choices.isNotEmpty() && !uiState.isLoading && !uiState.isStreaming) {
                var choicesExpanded by remember { mutableStateOf(false) }
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
                                        isUserScrolledUp = false
                                        scope.launch {
                                            if (uiState.dialogues.isNotEmpty()) {
                                                listState.scrollToItem(uiState.dialogues.size - 1, Int.MAX_VALUE)
                                            }
                                        }
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
                        isUserScrolledUp = false
                        scope.launch {
                            if (uiState.dialogues.isNotEmpty()) {
                                listState.scrollToItem(uiState.dialogues.size - 1, Int.MAX_VALUE)
                            }
                        }
                    },
                    enabled = inputText.isNotBlank() && !uiState.isLoading && !uiState.isStreaming
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

    if (showImageGenerationDialog) {
        ImageGenerationDialog(
            sessionId = sessionId,
            sceneNarrative = sceneNarrativeForImage,
            onDismiss = { showImageGenerationDialog = false }
        )
    }
}

@Composable
fun DialogueItem(
    dialogue: com.textgame.presentation.viewmodel.DialogueDisplay,
    onRegenerate: (Int) -> Unit,
    onGenerateSceneImage: () -> Unit = {}
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
                            if (dialogue.turnNumber > 0) {
                                DropdownMenuItem(
                                    text = { Text("从此轮重新生成") },
                                    onClick = {
                                        showMenu = false
                                        onRegenerate(dialogue.turnNumber)
                                    }
                                )
                            }
                            DropdownMenuItem(
                                text = { Text("生成当前场景图片") },
                                onClick = {
                                    showMenu = false
                                    onGenerateSceneImage()
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
                        DropdownMenuItem(
                            text = { Text("生成当前场景图片") },
                            onClick = {
                                showMenu = false
                                onGenerateSceneImage()
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
                            DropdownMenuItem(
                                text = { Text("生成当前场景图片") },
                                onClick = {
                                    showMenu = false
                                    onGenerateSceneImage()
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StatusPanelDialog(viewModel: GameViewModel, onDismiss: () -> Unit) {
    val uiState by viewModel.uiState.collectAsState(initial = com.textgame.presentation.viewmodel.GameUiState())
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val scrollState = rememberScrollState()

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        containerColor = MaterialTheme.colorScheme.surface,
        shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(max = 720.dp)
                .verticalScroll(scrollState)
                .padding(horizontal = 16.dp)
                .padding(bottom = 24.dp)
        ) {
            // 顶部标题栏
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "状态面板",
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier.weight(1f)
                )
                IconButton(onClick = onDismiss) {
                    Icon(Icons.Default.Close, contentDescription = "关闭")
                }
            }
            uiState.gameState?.let { gs ->
                Text(
                    text = "第 ${gs.turnCount} 轮 · ${gs.currentScene}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // 主角卡片
            uiState.protagonist?.let { protag ->
                SectionCard(
                    title = "主角",
                    icon = { Icon(Icons.Default.Person, contentDescription = null) }
                ) {
                    ProtagonistContent(
                        protagonist = protag,
                        categories = uiState.worldSetting?.attributeCategories ?: emptyList()
                    )
                }
            }

            // NPC 卡片
            if (uiState.npcs.isNotEmpty()) {
                Spacer(modifier = Modifier.height(12.dp))
                SectionCard(
                    title = "在场角色 (${uiState.npcs.size})",
                    icon = { Icon(Icons.Default.Group, contentDescription = null) }
                ) {
                    uiState.npcs.forEachIndexed { index, npc ->
                        if (index > 0) Divider(modifier = Modifier.padding(vertical = 8.dp))
                        NpcContent(npc = npc)
                    }
                }
            }

            // 世界观细则卡片
            uiState.worldSetting?.let { world ->
                if (world.worldRules.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(12.dp))
                    SectionCard(
                        title = "世界观细则 (${world.worldRules.size})",
                        icon = { Icon(Icons.Default.Public, contentDescription = null) }
                    ) {
                        world.worldRules.forEach { rule ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 4.dp)
                            ) {
                                Text(
                                    text = "•",
                                    color = MaterialTheme.colorScheme.primary,
                                    modifier = Modifier.padding(end = 8.dp)
                                )
                                Text(
                                    text = rule.content,
                                    style = MaterialTheme.typography.bodySmall
                                )
                            }
                        }
                    }
                }
            }

            // 进度总结卡片
            uiState.summary?.let { summary ->
                if (summary.summaryText.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(12.dp))
                    SectionCard(
                        title = "进度总结",
                        icon = { Icon(Icons.Default.AutoStories, contentDescription = null) }
                    ) {
                        RichText {
                            Markdown(summary.summaryText)
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))
            // 底部关闭按钮，便于单手操作
            Button(
                onClick = onDismiss,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                shape = RoundedCornerShape(16.dp)
            ) {
                Text("关闭面板")
            }
        }
    }
}

/**
 * 通用分区卡片：左侧图标 + 标题，下方为内容。
 */
@Composable
private fun SectionCard(
    title: String,
    icon: @androidx.compose.runtime.Composable () -> Unit,
    content: @androidx.compose.runtime.Composable () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
        )
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(28.dp)
                        .background(
                            color = MaterialTheme.colorScheme.primaryContainer,
                            shape = RoundedCornerShape(8.dp)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    icon()
                }
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold
                )
            }
            Spacer(modifier = Modifier.height(10.dp))
            content()
        }
    }
}

@Composable
private fun ProtagonistContent(
    protagonist: Protagonist,
    categories: List<AttributeCategory>
) {
    Text(
        text = protagonist.name,
        style = MaterialTheme.typography.titleMedium,
        fontWeight = FontWeight.Bold
    )
    if (protagonist.location.isNotBlank()) {
        Spacer(modifier = Modifier.height(2.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                Icons.Default.Place,
                contentDescription = null,
                modifier = Modifier.size(14.dp),
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = protagonist.location,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }

    if (protagonist.attributes.isNotEmpty()) {
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = "属性",
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Spacer(modifier = Modifier.height(6.dp))
        protagonist.attributes.forEach { (key, value) ->
            val cat = categories.find { it.name == key }
            AttributeRow(name = key, value = value, category = cat)
            Spacer(modifier = Modifier.height(6.dp))
        }
    }

    if (protagonist.inventory.isNotEmpty()) {
        Spacer(modifier = Modifier.height(8.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                Icons.Default.Backpack,
                contentDescription = null,
                modifier = Modifier.size(16.dp),
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = "物品",
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
        Spacer(modifier = Modifier.height(4.dp))
        // 物品用 chip 形式横排，自动换行，移动端更紧凑
        FlowChips(items = protagonist.inventory)
    }
}

@Composable
private fun NpcContent(npc: NPC) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(
            text = npc.name,
            style = MaterialTheme.typography.titleSmall,
            fontWeight = FontWeight.Bold
        )
        if (npc.role.isNotBlank()) {
            Spacer(modifier = Modifier.width(6.dp))
            Surface(
                shape = RoundedCornerShape(8.dp),
                color = MaterialTheme.colorScheme.secondaryContainer
            ) {
                Text(
                    text = npc.role,
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSecondaryContainer,
                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                )
            }
        }
    }
    if (npc.briefing.isNotBlank()) {
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = npc.briefing,
            style = MaterialTheme.typography.bodySmall
        )
    }
    if (npc.mood.isNotBlank()) {
        Spacer(modifier = Modifier.height(4.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = "情绪：",
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Surface(
                shape = RoundedCornerShape(8.dp),
                color = MaterialTheme.colorScheme.tertiaryContainer
            ) {
                Text(
                    text = npc.mood,
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onTertiaryContainer,
                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                )
            }
        }
    }
}

/**
 * 单条属性展示。按 [category] 的类型分别渲染：
 * - NUMERIC 且有 maxValue：用进度条展示当前值/最大值
 * - BOOLEAN：用 chip 展示 是/否
 * - ENUM：用 chip 展示当前选项
 * - TEXT：直接展示文本
 * - TABLE：展示列头 + 逐行单元格（紧凑网格，适合手机）
 */
@Composable
private fun AttributeRow(
    name: String,
    value: Any?,
    category: AttributeCategory?
) {
    val type = category?.type
    val displayValue = formatScalarValue(value)

    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = name,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.weight(1f, fill = false)
            )
            Spacer(modifier = Modifier.width(8.dp))

            when (type) {
                AttributeType.BOOLEAN -> {
                    val checked = value?.toString()?.equals("true", ignoreCase = true) == true
                    AssistChip(
                        onClick = {},
                        label = { Text(if (checked) "是" else "否", style = MaterialTheme.typography.labelSmall) },
                        colors = AssistChipDefaults.assistChipColors(
                            containerColor = if (checked) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surfaceVariant,
                            labelColor = if (checked) MaterialTheme.colorScheme.onPrimaryContainer else MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    )
                }
                AttributeType.ENUM -> {
                    AssistChip(
                        onClick = {},
                        label = { Text(displayValue, style = MaterialTheme.typography.labelSmall) },
                        colors = AssistChipDefaults.assistChipColors(
                            containerColor = MaterialTheme.colorScheme.secondaryContainer,
                            labelColor = MaterialTheme.colorScheme.onSecondaryContainer
                        )
                    )
                }
                else -> {
                    Text(
                        text = displayValue,
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Medium,
                        textAlign = TextAlign.End
                    )
                }
            }
        }

        when (type) {
            AttributeType.NUMERIC -> {
                val max = category?.maxValue
                val numValue = (value as? Number)?.toDouble()
                if (max != null && max > 0 && numValue != null) {
                    Spacer(modifier = Modifier.height(4.dp))
                    val progress = (numValue / max).coerceIn(0.0, 1.0).toFloat()
                    LinearProgressIndicator(
                        progress = progress,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(6.dp),
                        color = progressColor(progress),
                        trackColor = MaterialTheme.colorScheme.surfaceVariant
                    )
                    if (category.minValue != null) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "${category.minValue}",
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            Text(
                                text = "$max",
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }
            }
            AttributeType.TABLE -> {
                Spacer(modifier = Modifier.height(6.dp))
                TableAttributeView(
                    value = value,
                    columns = category?.columns ?: emptyList()
                )
            }
            else -> {}
        }
    }
}

/**
 * TABLE 属性的紧凑网格展示。手机端宽度有限，采用：
 * - 表头：列名，使用浅色背景
 * - 每行：按列顺序渲染单元格，单元格用浅边框分隔
 */
@Composable
private fun TableAttributeView(
    value: Any?,
    columns: List<com.textgame.domain.model.TableColumn>
) {
    val rows = extractTableRowsForUi(value)
    if (columns.isEmpty()) {
        Text(
            text = "（未定义列）",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        return
    }
    if (rows.isEmpty()) {
        Text(
            text = "（空表）",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        return
    }

    Surface(
        shape = RoundedCornerShape(10.dp),
        color = MaterialTheme.colorScheme.surface,
        border = androidx.compose.foundation.BorderStroke(
            1.dp,
            MaterialTheme.colorScheme.outlineVariant
        )
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            // 表头
            Row(modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.primaryContainer)
                .padding(horizontal = 8.dp, vertical = 6.dp)
            ) {
                columns.forEachIndexed { i, col ->
                    Text(
                        text = col.name,
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                        modifier = Modifier.weight(1f)
                    )
                    if (i < columns.size - 1) {
                        Spacer(modifier = Modifier.width(8.dp))
                    }
                }
            }
            // 数据行
            rows.forEachIndexed { rowIndex, row ->
                if (rowIndex > 0) {
                    Divider(
                        thickness = 1.dp,
                        color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f)
                    )
                }
                Row(modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp, vertical = 6.dp)
                ) {
                    columns.forEachIndexed { i, col ->
                        Text(
                            text = formatScalarValue(row[col.name]),
                            style = MaterialTheme.typography.bodySmall,
                            modifier = Modifier.weight(1f)
                        )
                        if (i < columns.size - 1) {
                            Spacer(modifier = Modifier.width(8.dp))
                        }
                    }
                }
            }
        }
    }
}

/**
 * 简单的横排自动换行 chip 容器，用于物品等短文本列表。
 */
@Composable
private fun FlowChips(items: List<String>) {
    // Compose BOM 2023.08 没有 FlowRow 稳定 API，这里手动折行。
    // 按字符宽度估算每行容纳数量，简单可靠。
    val rows = mutableListOf<MutableList<String>>()
    var currentRow = mutableListOf<String>()
    var currentLen = 0
    items.forEach { item ->
        val itemLen = item.length + 2
        if (currentLen + itemLen > 24 && currentRow.isNotEmpty()) {
            rows.add(currentRow)
            currentRow = mutableListOf()
            currentLen = 0
        }
        currentRow.add(item)
        currentLen += itemLen
    }
    if (currentRow.isNotEmpty()) rows.add(currentRow)

    rows.forEach { rowItems ->
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 2.dp),
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            rowItems.forEach { item ->
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = MaterialTheme.colorScheme.surfaceVariant
                ) {
                    Text(
                        text = item,
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                    )
                }
            }
        }
    }
}

@Composable
private fun progressColor(progress: Float): Color {
    return when {
        progress < 0.25f -> MaterialTheme.colorScheme.error
        progress < 0.5f -> Color(0xFFFFA000) // amber
        else -> Color(0xFF2E7D32) // green
    }
}

private fun formatScalarValue(value: Any?): String {
    if (value == null) return ""
    return when (value) {
        is Boolean -> value.toString()
        is Double -> if (value % 1.0 == 0.0) value.toInt().toString() else value.toString()
        is Number -> value.toString()
        is String -> value
        is Map<*, *> -> value.entries.joinToString(", ") { "${it.key}=${it.value}" }
        is List<*> -> value.joinToString(", ") { it.toString() }
        else -> value.toString()
    }
}

/**
 * 把 TABLE 属性值统一成 List<Map<String, Any>>，兼容 Gson 反序列化结果。
 */
private fun extractTableRowsForUi(value: Any?): List<Map<String, Any>> {
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
