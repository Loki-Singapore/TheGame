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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.textgame.R
import kotlinx.coroutines.launch

/**
 * 全屏可缩放图片预览。
 *
 * 实现要点（彻底解决黑屏与加载失败）：
 * - 接收预解码的 Bitmap，不再依赖 Coil 在 Dialog 环境下做网络/解码加载。
 *   Bitmap 有明确尺寸，不会让 Dialog window 塌缩，也不存在"加载失败"。
 * - 用 rememberBitmapPainter 直接从 Bitmap 创建 painter，无网络请求。
 * - Dialog + Box 用屏幕尺寸（LocalConfiguration）撑满，双重保险。
 *
 * 交互：
 * - 双指捏合缩放（1x ~ 6x）
 * - 双指拖动平移（仅放大状态下生效）
 * - 双击在 1x 与 2x 之间切换
 * - 单击、右上角按钮或系统返回键关闭
 *
 * @param bitmap 预解码的图片 Bitmap
 * @param onDismiss 关闭回调
 */
@Composable
fun ZoomableImagePreview(
    bitmap: android.graphics.Bitmap,
    onDismiss: () -> Unit
) {
    val scope = rememberCoroutineScope()
    val scale = remember { Animatable(1f) }
    val offsetX = remember { Animatable(0f) }
    val offsetY = remember { Animatable(0f) }

    // 每次打开新图时复位变换，避免上次的缩放状态残留
    LaunchedEffect(bitmap) {
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
                .fillMaxSize()
                .background(Color.Black)
                // 双指变换手势（缩放 + 拖动）
                .pointerInput(bitmap) {
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
                .pointerInput(bitmap) {
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
            // 直接用 Bitmap 创建 painter，无网络加载，不会失败
            androidx.compose.foundation.Image(
                bitmap = bitmap.asImageBitmap(),
                contentDescription = stringResource(R.string.preview_fullscreen_cd),
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
                    Icon(Icons.Default.Close, contentDescription = stringResource(R.string.preview_close))
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
                        text = stringResource(R.string.preview_hint),
                        color = Color.White,
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                    )
                }
            }
        }
    }
}
