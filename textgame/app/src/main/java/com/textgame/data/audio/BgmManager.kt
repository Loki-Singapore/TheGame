package com.textgame.data.audio

import android.content.Context
import android.media.MediaPlayer
import android.net.Uri
import android.os.Handler
import android.os.Looper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.concurrent.atomic.AtomicBoolean
import java.util.concurrent.atomic.AtomicReference

class BgmManager private constructor(private val context: Context) {

    private val currentPlayerRef = AtomicReference<MediaPlayer?>(null)
    private val currentTrackRef = AtomicReference<BgmTrack?>(null)
    private val isSwitching = AtomicBoolean(false)
    private val pendingTrack = AtomicReference<BgmTrack?>(null)
    private var isMusicEnabled: Boolean = true
    private val scope = CoroutineScope(Dispatchers.Default + Job())
    private var fadeJob: Job? = null
    private val mainHandler = Handler(Looper.getMainLooper())
    private val maxVolume = 1.0f
    private val fadeDurationMs = 5000L
    private val fadeStepDelayMs = 50L

    fun setMusicEnabled(enabled: Boolean) {
        if (isMusicEnabled == enabled) return
        isMusicEnabled = enabled
        mainHandler.post {
            if (enabled) {
                currentTrackRef.get()?.let { play(it) }
            } else {
                stopInternal()
            }
        }
    }

    fun isMusicEnabled(): Boolean = isMusicEnabled

    fun play(track: BgmTrack) {
        mainHandler.post {
            val currentTrack = currentTrackRef.get()
            val currentPlayer = currentPlayerRef.get()
            if (currentTrack == track && currentPlayer?.isPlaying == true) {
                pendingTrack.set(null)
                return@post
            }

            if (!isMusicEnabled) {
                pendingTrack.set(null)
                isSwitching.set(false)
                stopInternal()
                currentTrackRef.set(track)
                return@post
            }

            if (isSwitching.get()) {
                pendingTrack.set(track)
                return@post
            }

            val oldPlayer = currentPlayer
            isSwitching.set(true)
            currentTrackRef.set(track)
            pendingTrack.set(null)

            scope.launch(Dispatchers.Default) {
                val newPlayer = try {
                    val player = MediaPlayer()
                    player.isLooping = true
                    player.setVolume(0f, 0f)
                    val resId = track.resId
                    val uri = Uri.parse("android.resource://${context.packageName}/$resId")
                    player.setDataSource(context, uri)
                    player.prepare()
                    player
                } catch (e: Exception) {
                    e.printStackTrace()
                    withContext(Dispatchers.Main) {
                        isSwitching.set(false)
                        checkPending()
                    }
                    return@launch
                }

                withContext(Dispatchers.Main) {
                    currentPlayerRef.set(newPlayer)
                    newPlayer.start()

                    fadeJob?.cancel()
                    fadeJob = scope.launch(Dispatchers.Main) {
                        if (oldPlayer != null) {
                            crossFade(newPlayer, oldPlayer)
                        } else {
                            fadeIn(newPlayer)
                        }
                        isSwitching.set(false)
                        checkPending()
                    }
                }
            }
        }
    }

    private fun checkPending() {
        val pending = pendingTrack.getAndSet(null)
        if (pending != null) {
            mainHandler.post { play(pending) }
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
        releasePlayer(oldPlayer)
    }

    private fun releasePlayer(player: MediaPlayer) {
        try {
            if (player.isPlaying) {
                player.stop()
            }
            player.release()
        } catch (e: Exception) {
        }
    }

    private fun stopInternal() {
        fadeJob?.cancel()
        isSwitching.set(false)
        pendingTrack.set(null)
        currentPlayerRef.getAndSet(null)?.let { releasePlayer(it) }
    }

    fun stop() {
        mainHandler.post { stopInternal() }
    }

    fun pause() {
        mainHandler.post {
            currentPlayerRef.get()?.let {
                if (it.isPlaying) {
                    it.pause()
                }
            }
        }
    }

    fun resume() {
        mainHandler.post {
            if (isMusicEnabled) {
                currentPlayerRef.get()?.let {
                    if (!it.isPlaying) {
                        it.start()
                    }
                }
            }
        }
    }

    fun getCurrentTrack(): BgmTrack? = currentTrackRef.get()

    private fun easeInOutCubic(t: Float): Float {
        return if (t < 0.5f) {
            4f * t * t * t
        } else {
            1f - (-2f * t + 2f).let { it * it * it } / 2f
        }
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
