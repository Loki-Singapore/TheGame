package com.textgame.presentation.ui.imagegen

import androidx.activity.compose.BackHandler
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import coil.compose.AsyncImage
import kotlinx.coroutines.launch

/**
 * 全屏可缩放图片预览。
 *
 * 实现要点（解决 Dialog 黑屏问题）：
 * - Dialog(usePlatformDefaultWidth=false) 的 window 高度默认是 wrap-content，
 *   Box(fillMaxSize) 会塌缩，AsyncImage 无绘制空间只剩黑底。
 * - 解决：用 LocalConfiguration 拿屏幕尺寸，给 Box 明确的 width/height，
 *   让 Dialog window 撑满屏幕。
 * - 与缩略图预览用完全相同的 AsyncImage(model = model) 写法，确保加载行为一致。
 * - graphicsLayer 应用在 AsyncImage 上做缩放/平移，不影响外层 Box 尺寸。
 *
 * 交互：
 * - 双指捏合缩放（1x ~ 6x）
 * - 双指拖动平移（仅放大状态下生效）
 * - 双击在 1x 与 2x 之间切换
 * - 单击、右上角按钮或系统返回键关闭
 *
 * @param model Coil 可加载的模型：URL 字符串、data URI 等（与缩略图用同一个值）
 * @param onDismiss 关闭回调
 */
@Composable
fun ZoomableImagePreview(
    model: Any?,
    onDismiss: () -> Unit
) {
    if (model == null) {
        onDismiss()
        return
    }

    val scope = rememberCoroutineScope()
    val configuration = LocalConfiguration.current
    val screenWidthDp = configuration.screenWidthDp.dp
    val screenHeightDp = configuration.screenHeightDp.dp
    val scale = remember { Animatable(1f) }
    val offsetX = remember { Animatable(0f) }
    val offsetY = remember { Animatable(0f) }

    // 每次打开新图时复位变换，避免上次的缩放状态残留
    LaunchedEffect(model) {
        scale.snapTo(1f)
        offsetX.snapTo(0f)
        offsetY.snapTo(0f)
    }

    // 系统返回键关闭
    BackHandler(enabled = true) { onDismiss() }

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(
            usePlatformDefaultWidth = false,
            dismissOnClickOutside = false,
            dismissOnBackPress = true
        )
    ) {
        Box(
            modifier = Modifier
                // 用明确的屏幕尺寸撑开 Dialog window，避免 fillMaxSize 在 wrap-content 下塌缩
                .width(screenWidthDp)
                .height(screenHeightDp)
                .background(Color.Black)
                // 双指变换手势（缩放 + 拖动）
                .pointerInput(model) {
                    detectTransformGestures { _, pan, zoom, _ ->
                        val newScale = (scale.value * zoom).coerceIn(1f, 6f)
                        scope.launch { scale.snapTo(newScale) }
                        if (newScale > 1f) {
                            scope.launch { offsetX.snapTo(offsetX.value + pan.x) }
                            scope.launch { offsetY.snapTo(offsetY.value + pan.y) }
                        } else {
                            scope.launch { offsetX.snapTo(0f) }
                            scope.launch { offsetY.snapTo(0f) }
                        }
                    }
                }
                // 单击 / 双击手势
                .pointerInput(model) {
                    detectTapGestures(
                        onTap = { onDismiss() },
                        onDoubleTap = {
                            val target = if (scale.value > 1.5f) 1f else 2f
                            scope.launch {
                                scale.animateTo(target, tween(250))
                                if (target == 1f) {
                                    offsetX.animateTo(0f, tween(250))
                                    offsetY.animateTo(0f, tween(250))
                                }
                            }
                        }
                    )
                }
        ) {
            // 用与缩略图完全相同的 AsyncImage 写法，确保加载行为一致
            AsyncImage(
                model = model,
                contentDescription = "生成的图片（全屏预览）",
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .fillMaxSize()
                    .graphicsLayer(
                        scaleX = scale.value,
                        scaleY = scale.value,
                        translationX = offsetX.value,
                        translationY = offsetY.value
                    )
            )

            // 右上角关闭按钮
            Surface(
                shape = CircleShape,
                color = Color.Black.copy(alpha = 0.5f),
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(16.dp)
                    .size(40.dp)
            ) {
                IconButton(
                    onClick = onDismiss,
                    colors = IconButtonDefaults.iconButtonColors(
                        contentColor = Color.White
                    )
                ) {
                    Icon(Icons.Default.Close, contentDescription = "关闭预览")
                }
            }

            // 底部操作提示
            Row(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 24.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    shape = MaterialTheme.shapes.small,
                    color = Color.Black.copy(alpha = 0.5f)
                ) {
                    Text(
                        text = "双指缩放 · 双击切换 · 单击关闭",
                        color = Color.White,
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                    )
                }
            }
        }
    }
}
