package com.textgame.data.audio

import android.content.Context
import android.media.MediaPlayer
import android.net.Uri
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.concurrent.atomic.AtomicReference

class BgmManager private constructor(private val context: Context) {

    private val playerMap = mutableMapOf<BgmTrack, MediaPlayer>()
    private val currentTrackRef = AtomicReference<BgmTrack?>(null)
    private var isMusicEnabled: Boolean = true
    private val scope = CoroutineScope(Dispatchers.Default + Job())
    private var fadeJob: Job? = null
    private var initialized = false
    private val maxVolume = 1.0f
    private val fadeDurationMs = 5000L
    private val fadeStepDelayMs = 50L

    fun initialize(callback: (() -> Unit)? = null) {
        if (initialized) {
            callback?.invoke()
            return
        }
        scope.launch(Dispatchers.Default) {
            BgmTrack.values().forEach { track ->
                try {
                    val player = MediaPlayer()
                    player.isLooping = true
                    player.setVolume(0f, 0f)
                    val uri = Uri.parse("android.resource://${context.packageName}/${track.resId}")
                    player.setDataSource(context, uri)
                    player.prepare()
                    playerMap[track] = player
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
            initialized = true
            withContext(Dispatchers.Main) {
                callback?.invoke()
            }
        }
    }

    fun setMusicEnabled(enabled: Boolean) {
        if (isMusicEnabled == enabled) return
        isMusicEnabled = enabled
        if (enabled) {
            currentTrackRef.get()?.let { play(it) }
        } else {
            stopAll()
        }
    }

    fun isMusicEnabled(): Boolean = isMusicEnabled

    fun play(track: BgmTrack) {
        if (!isMusicEnabled) {
            currentTrackRef.set(track)
            return
        }

        val currentTrack = currentTrackRef.get()
        if (currentTrack == track) return

        val newPlayer = playerMap[track]
        if (newPlayer == null) {
            currentTrackRef.set(track)
            return
        }

        val oldPlayer = currentTrack?.let { playerMap[it] }
        currentTrackRef.set(track)

        fadeJob?.cancel()

        newPlayer.seekTo(0)
        newPlayer.start()

        fadeJob = scope.launch(Dispatchers.Default) {
            if (oldPlayer != null && oldPlayer != newPlayer) {
                crossFade(newPlayer, oldPlayer)
            } else {
                fadeIn(newPlayer)
            }
        }
    }

    private suspend fun fadeIn(player: MediaPlayer) {
        val steps = (fadeDurationMs / fadeStepDelayMs).toInt()
        for (i in 0..steps) {
            val progress = i.toFloat() / steps
            val eased = easeInOutCubic(progress)
            val volume = eased * maxVolume
            try {
                player.setVolume(volume, volume)
            } catch (e: Exception) {
                break
            }
            delay(fadeStepDelayMs)
        }
        try {
            player.setVolume(maxVolume, maxVolume)
        } catch (e: Exception) {
        }
    }

    private suspend fun crossFade(newPlayer: MediaPlayer, oldPlayer: MediaPlayer) {
        val steps = (fadeDurationMs / fadeStepDelayMs).toInt()

        for (i in 0..steps) {
            val progress = i.toFloat() / steps
            val eased = easeInOutCubic(progress)
            val newVolume = eased * maxVolume
            val oldVolume = maxVolume * (1f - eased)

            try {
                newPlayer.setVolume(newVolume, newVolume)
                oldPlayer.setVolume(oldVolume, oldVolume)
            } catch (e: Exception) {
                break
            }
            delay(fadeStepDelayMs)
        }

        try {
            newPlayer.setVolume(maxVolume, maxVolume)
        } catch (e: Exception) {
        }
        try {
            oldPlayer.setVolume(0f, 0f)
            oldPlayer.pause()
            oldPlayer.seekTo(0)
        } catch (e: Exception) {
        }
    }

    private fun stopAll() {
        fadeJob?.cancel()
        playerMap.values.forEach { player ->
            try {
                player.setVolume(0f, 0f)
                if (player.isPlaying) {
                    player.pause()
                }
                player.seekTo(0)
            } catch (e: Exception) {
            }
        }
    }

    fun stop() {
        fadeJob?.cancel()
        val current = currentTrackRef.getAndSet(null)
        current?.let { track ->
            playerMap[track]?.let { player ->
                try {
                    player.setVolume(0f, 0f)
                    if (player.isPlaying) {
                        player.pause()
                    }
                    player.seekTo(0)
                } catch (e: Exception) {
                }
            }
        }
    }

    fun pause() {
        currentTrackRef.get()?.let { track ->
            playerMap[track]?.let { player ->
                if (player.isPlaying) {
                    player.pause()
                }
            }
        }
    }

    fun resume() {
        if (isMusicEnabled) {
            currentTrackRef.get()?.let { track ->
                playerMap[track]?.let { player ->
                    if (!player.isPlaying) {
                        player.start()
                    }
                }
            }
        }
    }

    fun getCurrentTrack(): BgmTrack? = currentTrackRef.get()

    fun isInitialized(): Boolean = initialized

    private fun easeInOutCubic(t: Float): Float {
        return if (t < 0.5f) {
            4f * t * t * t
        } else {
            1f - (-2f * t + 2f).let { it * it * it } / 2f
        }
    }

    fun release() {
        fadeJob?.cancel()
        playerMap.values.forEach { player ->
            try {
                player.release()
            } catch (e: Exception) {
            }
        }
        playerMap.clear()
        initialized = false
    }

    companion object {
        @Volatile
        private var instance: BgmManager? = null

        fun getInstance(context: Context): BgmManager {
            return instance ?: synchronized(this) {
                instance ?: BgmManager(context.applicationContext).also { instance = it }
            }
        }
    }
}
