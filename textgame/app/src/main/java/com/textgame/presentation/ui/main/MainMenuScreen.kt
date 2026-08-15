package com.textgame.presentation.ui.main

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material.icons.filled.MusicOff
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.textgame.R
import com.textgame.data.audio.BgmManager
import com.textgame.data.audio.BgmTrack
import com.textgame.data.local.SettingsManager
import com.textgame.domain.model.GameSession
import com.textgame.presentation.viewmodel.MainViewModel
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun MainMenuScreen(
    onNewGame: () -> Unit,
    onContinueGame: (Long) -> Unit,
    onSettings: () -> Unit
) {
    val viewModel: MainViewModel = viewModel()
    val sessions by viewModel.sessions.collectAsState(initial = emptyList())
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val bgmManager = remember { BgmManager.getInstance(context) }
    var musicEnabled by remember { mutableStateOf(bgmManager.isMusicEnabled()) }
    var hasStartedBgm by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        SettingsManager.getSettingsFlow(context).collect { settings ->
            val wasEnabled = musicEnabled
            musicEnabled = settings.musicEnabled
            if (wasEnabled != settings.musicEnabled) {
                bgmManager.setMusicEnabled(settings.musicEnabled)
            }
            if (!hasStartedBgm) {
                hasStartedBgm = true
                if (settings.musicEnabled) {
                    bgmManager.play(BgmTrack.MAIN)
                }
            }
        }
    }

    fun toggleMusic() {
        val newEnabled = !musicEnabled
        musicEnabled = newEnabled
        coroutineScope.launch {
            SettingsManager.saveMusicEnabled(context, newEnabled)
        }
        if (newEnabled) {
            bgmManager.play(BgmTrack.MAIN)
        } else {
            bgmManager.stop()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            IconButton(onClick = { toggleMusic() }) {
                Icon(
                    if (musicEnabled) Icons.Default.MusicNote else Icons.Default.MusicOff,
                    contentDescription = stringResource(if (musicEnabled) R.string.menu_music_off_cd else R.string.menu_music_on_cd)
                )
            }
        }

        Text(
            text = stringResource(R.string.app_name),
            style = MaterialTheme.typography.headlineLarge
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = stringResource(R.string.menu_tagline),
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = onNewGame,
            modifier = Modifier.fillMaxWidth().height(56.dp)
        ) {
            Text(stringResource(R.string.menu_new_game), style = MaterialTheme.typography.titleMedium)
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = stringResource(R.string.menu_continue_game),
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.align(Alignment.Start)
        )

        Spacer(modifier = Modifier.height(8.dp))

        if (sessions.isEmpty()) {
            Text(
                text = stringResource(R.string.menu_no_saves),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(sessions) { session ->
                    SessionCard(
                        session = session,
                        onClick = { onContinueGame(session.id) },
                        onDelete = { viewModel.deleteSession(session.id) }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        FilledTonalButton(
            onClick = onSettings,
            modifier = Modifier.fillMaxWidth()
        ) {
            Icon(Icons.Default.Settings, contentDescription = null)
            Spacer(modifier = Modifier.height(4.dp))
            Text(stringResource(R.string.menu_ai_settings))
        }
    }
}

@Composable
fun SessionCard(
    session: GameSession,
    onClick: () -> Unit,
    onDelete: () -> Unit
) {
    var showDeleteDialog by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = session.name,
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(modifier = Modifier.height(4.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = stringResource(R.string.menu_turn_count, session.currentTurn),
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        text = formatDate(session.createdAt),
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            IconButton(onClick = { showDeleteDialog = true }) {
                Icon(
                    Icons.Default.Delete,
                    contentDescription = stringResource(R.string.delete),
                    tint = MaterialTheme.colorScheme.error
                )
            }
        }
    }

    if (showDeleteDialog) {
        AlertDialog(
            onDismissRequest = { showDeleteDialog = false },
            title = { Text(stringResource(R.string.menu_delete_confirm_title)) },
            text = { Text(stringResource(R.string.menu_delete_confirm_message, session.name)) },
            confirmButton = {
                TextButton(
                    onClick = {
                        showDeleteDialog = false
                        onDelete()
                    }
                ) {
                    Text(stringResource(R.string.delete), color = MaterialTheme.colorScheme.error)
                }
            },
            dismissButton = {
                TextButton(onClick = { showDeleteDialog = false }) {
                    Text(stringResource(R.string.cancel))
                }
            }
        )
    }
}

private fun formatDate(timestamp: Long): String {
    val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())
    return sdf.format(Date(timestamp))
}