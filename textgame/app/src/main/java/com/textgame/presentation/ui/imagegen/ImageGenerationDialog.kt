package com.textgame.presentation.ui.imagegen

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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.textgame.data.local.SettingsPreferences
import com.textgame.data.remote.ai.ImagePromptStyle
import com.textgame.presentation.viewmodel.ImageGenPhase
import com.textgame.presentation.viewmodel.ImageGenerationViewModel
import com.textgame.presentation.viewmodel.ImageGenerationViewModelFactory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ImageGenerationDialog(
    sessionId: Long,
    sceneNarrative: String,
    onDismiss: () -> Unit
) {
    val context = LocalContext.current
    val viewModel: ImageGenerationViewModel = viewModel(
        factory = ImageGenerationViewModelFactory(sessionId, sceneNarrative, context)
    )
    val uiState by viewModel.uiState.collectAsState(initial = com.textgame.presentation.viewmodel.ImageGenerationUiState())
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        containerColor = MaterialTheme.colorScheme.surface,
        shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(max = 760.dp)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp)
                .padding(bottom = 24.dp)
        ) {
            // 顶部标题栏
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(Icons.Default.Image, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = if (uiState.phase == ImageGenPhase.PROMPT_INPUT) "生图提示词" else "生成场景图片",
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier.weight(1f)
                )
                IconButton(onClick = onDismiss) {
                    Icon(Icons.Default.Close, contentDescription = "关闭")
                }
            }

            when (uiState.phase) {
                ImageGenPhase.PROMPT_INPUT -> PromptInputPhase(
                    viewModel = viewModel,
                    uiState = uiState
                )
                ImageGenPhase.IMAGE_GENERATION -> ImageGenerationPhase(
                    viewModel = viewModel,
                    uiState = uiState
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun PromptInputPhase(
    viewModel: ImageGenerationViewModel,
    uiState: com.textgame.presentation.viewmodel.ImageGenerationUiState
) {
    Spacer(modifier = Modifier.height(8.dp))
    Text(
        "选择画面风格，由 AI 根据当前场景生成生图提示词，可重新生成或手动编辑后再继续。",
        style = MaterialTheme.typography.bodySmall,
        color = MaterialTheme.colorScheme.onSurfaceVariant
    )
    Spacer(modifier = Modifier.height(12.dp))

    Text("画面风格", style = MaterialTheme.typography.titleMedium)
    Spacer(modifier = Modifier.height(8.dp))
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        ImagePromptStyle.values().forEach { style ->
            FilterChip(
                selected = uiState.style == style,
                onClick = { viewModel.updateStyle(style) },
                label = { Text(style.label) },
                modifier = Modifier.weight(1f)
            )
        }
    }

    Spacer(modifier = Modifier.height(12.dp))

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Button(
            onClick = { viewModel.generatePrompt() },
            enabled = !uiState.isGeneratingPrompt,
            modifier = Modifier.weight(1f)
        ) {
            if (uiState.isGeneratingPrompt) {
                CircularProgressIndicator(
                    modifier = Modifier.size(18.dp),
                    strokeWidth = 2.dp,
                    color = MaterialTheme.colorScheme.onPrimary
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text("生成中")
            } else {
                Icon(Icons.Default.AutoAwesome, contentDescription = null, modifier = Modifier.size(18.dp))
                Spacer(modifier = Modifier.width(8.dp))
                Text(if (uiState.prompt.isBlank()) "生成提示词" else "重新生成")
            }
        }
    }

    Spacer(modifier = Modifier.height(12.dp))

    OutlinedTextField(
        value = uiState.prompt,
        onValueChange = { viewModel.updatePrompt(it) },
        label = { Text("生图提示词（可编辑）") },
        modifier = Modifier.fillMaxWidth(),
        minLines = 4,
        maxLines = 8,
        placeholder = { Text("点击上方按钮由 AI 生成，或在此手动输入提示词...") }
    )

    uiState.error?.let { error ->
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = error,
            color = MaterialTheme.colorScheme.error,
            style = MaterialTheme.typography.bodySmall
        )
    }

    Spacer(modifier = Modifier.height(16.dp))

    Button(
        onClick = { viewModel.confirmPrompt() },
        enabled = uiState.prompt.isNotBlank() && !uiState.isGeneratingPrompt,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text("确认提示词，进入生图")
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ImageGenerationPhase(
    viewModel: ImageGenerationViewModel,
    uiState: com.textgame.presentation.viewmodel.ImageGenerationUiState
) {
    val currentModel = com.textgame.di.AppModule.getCurrentSettings().imageModel

    Spacer(modifier = Modifier.height(8.dp))

    // 当前模型与提示词预览
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text(
                "模型：$currentModel",
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                "提示词：${uiState.prompt}",
                style = MaterialTheme.typography.bodySmall,
                maxLines = 4
            )
        }
    }

    Spacer(modifier = Modifier.height(12.dp))

    // 尺寸选择
    Text("图片尺寸", style = MaterialTheme.typography.titleMedium)
    Spacer(modifier = Modifier.height(8.dp))
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        uiState.availableSizes.forEach { size ->
            FilterChip(
                selected = uiState.size == size,
                onClick = { viewModel.selectSize(size) },
                label = { Text(SettingsPreferences.IMAGE_SIZE_LABELS[size] ?: size) },
                modifier = Modifier.weight(1f)
            )
        }
    }

    Spacer(modifier = Modifier.height(12.dp))

    Button(
        onClick = { viewModel.generateImage() },
        enabled = !uiState.isGeneratingImage,
        modifier = Modifier.fillMaxWidth()
    ) {
        if (uiState.isGeneratingImage) {
            CircularProgressIndicator(
                modifier = Modifier.size(18.dp),
                strokeWidth = 2.dp,
                color = MaterialTheme.colorScheme.onPrimary
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text("生图中...")
        } else {
            Text("生成图片")
        }
    }

    uiState.error?.let { error ->
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = error,
            color = MaterialTheme.colorScheme.error,
            style = MaterialTheme.typography.bodySmall
        )
    }

    // 图片预览：优先用 URL，回退到 base64 数据 URI
    val previewModel: Any? = when {
        !uiState.imageUrl.isNullOrBlank() -> uiState.imageUrl
        !uiState.imageBase64.isNullOrBlank() ->
            "data:image/png;base64,${uiState.imageBase64}"
        else -> null
    }
    var showFullscreenPreview by remember { mutableStateOf(false) }
    if (previewModel != null) {
        Spacer(modifier = Modifier.height(12.dp))
        Text("生成结果", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold)
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            "点击图片可全屏查看，支持双指缩放",
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Spacer(modifier = Modifier.height(8.dp))
        Surface(
            shape = RoundedCornerShape(12.dp),
            color = MaterialTheme.colorScheme.surfaceVariant,
            modifier = Modifier
                .fillMaxWidth()
                .clickable { showFullscreenPreview = true }
        ) {
            AsyncImage(
                model = previewModel,
                contentDescription = "生成的图片",
                contentScale = ContentScale.FillWidth,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }

    if (showFullscreenPreview && previewModel != null) {
        ZoomableImagePreview(
            model = previewModel,
            onDismiss = { showFullscreenPreview = false }
        )
    }

    uiState.revisedPrompt?.let { revised ->
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            "AI 修订后的提示词：$revised",
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }

    if (previewModel != null) {
        Spacer(modifier = Modifier.height(12.dp))
        Button(
            onClick = { viewModel.saveImage() },
            enabled = !uiState.isSaving,
            modifier = Modifier.fillMaxWidth()
        ) {
            if (uiState.isSaving) {
                CircularProgressIndicator(
                    modifier = Modifier.size(18.dp),
                    strokeWidth = 2.dp,
                    color = MaterialTheme.colorScheme.onPrimary
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text("保存中...")
            } else {
                Icon(Icons.Default.Download, contentDescription = null, modifier = Modifier.size(18.dp))
                Spacer(modifier = Modifier.width(8.dp))
                Text("保存到本地")
            }
        }
    }

    uiState.savedMessage?.let { msg ->
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = msg,
            color = MaterialTheme.colorScheme.primary,
            style = MaterialTheme.typography.bodySmall
        )
    }

    Spacer(modifier = Modifier.height(12.dp))
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        OutlinedButton(
            onClick = { viewModel.backToPromptInput() },
            modifier = Modifier.weight(1f)
        ) {
            Icon(Icons.Default.Refresh, contentDescription = null, modifier = Modifier.size(18.dp))
            Spacer(modifier = Modifier.width(8.dp))
            Text("修改提示词")
        }
        OutlinedButton(
            onClick = { viewModel.generateImage() },
            enabled = !uiState.isGeneratingImage,
            modifier = Modifier.weight(1f)
        ) {
            Text("重新生成")
        }
    }
}
