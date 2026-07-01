package com.textgame.data.audio

import android.content.Context
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.concurrent.atomic.AtomicBoolean
import java.util.concurrent.atomic.AtomicReference

class BgmManager private constructor(private val context: Context) {

    private val currentPlayerRef = AtomicReference<ExoPlayer?>(null)
    private val currentTrackRef = AtomicReference<BgmTrack?>(null)
    private val isSwitching = AtomicBoolean(false)
    private val pendingTrack = AtomicReference<BgmTrack?>(null)
    private var isMusicEnabled: Boolean = true
    private val scope = CoroutineScope(Dispatchers.Main + Job())
    private var fadeJob: Job? = null
    private val maxVolume = 1.0f
    private val fadeDurationMs = 5000L
    private val fadeStepDelayMs = 50L

    fun setMusicEnabled(enabled: Boolean) {
        if (isMusicEnabled == enabled) return
        isMusicEnabled = enabled
        if (enabled) {
            currentTrackRef.get()?.let { play(it) }
        } else {
            stop()
        }
    }

    fun isMusicEnabled(): Boolean = isMusicEnabled

    fun play(track: BgmTrack) {
        val currentTrack = currentTrackRef.get()
        val currentPlayer = currentPlayerRef.get()

        if (currentTrack == track && currentPlayer?.playWhenReady == true && currentPlayer.isPlaying) {
            pendingTrack.set(null)
            return
        }

        if (!isMusicEnabled) {
            pendingTrack.set(null)
            isSwitching.set(false)
            currentPlayerRef.getAndSet(null)?.let { releasePlayer(it) }
            currentTrackRef.set(track)
            return
        }

        if (isSwitching.get()) {
            pendingTrack.set(track)
            return
        }

        fadeJob?.cancel()

        val oldPlayer = currentPlayerRef.get()
        isSwitching.set(true)
        currentTrackRef.set(track)
        pendingTrack.set(null)

        val newPlayer = ExoPlayer.Builder(context).build()
        newPlayer.volume = 0f
        newPlayer.repeatMode = ExoPlayer.REPEAT_MODE_ONE

        val resId = track.resId
        val mediaItem = MediaItem.fromUri("android.resource://${context.packageName}/$resId")
        newPlayer.setMediaItem(mediaItem)

        newPlayer.addListener(object : Player.Listener {
            override fun onPlaybackStateChanged(playbackState: Int) {
                if (playbackState == Player.STATE_READY) {
                    newPlayer.removeListener(this)
                    currentPlayerRef.set(newPlayer)

                    val pending = pendingTrack.get()
                    if (pending != null && pending != track) {
                        releasePlayer(newPlayer)
                        currentPlayerRef.set(null)
                        isSwitching.set(false)
                        play(pending)
                        return
                    }

                    fadeJob = scope.launch {
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
        })

        newPlayer.prepare()
        newPlayer.playWhenReady = true
    }

    private fun checkPending() {
        val pending = pendingTrack.getAndSet(null)
        if (pending != null) {
            play(pending)
        }
    }

    private suspend fun fadeIn(player: ExoPlayer) {
        val steps = (fadeDurationMs / fadeStepDelayMs).toInt()
        for (i in 0..steps) {
            val progress = i.toFloat() / steps
            val eased = easeInOutCubic(progress)
            val volume = eased * maxVolume
            player.volume = volume
            delay(fadeStepDelayMs)
        }
        player.volume = maxVolume
    }

    private suspend fun crossFade(newPlayer: ExoPlayer, oldPlayer: ExoPlayer) {
        val steps = (fadeDurationMs / fadeStepDelayMs).toInt()

        for (i in 0..steps) {
            val progress = i.toFloat() / steps
            val eased = easeInOutCubic(progress)
            val newVolume = eased * maxVolume
            val oldVolume = maxVolume * (1f - eased)

            newPlayer.volume = newVolume
            oldPlayer.volume = oldVolume

            delay(fadeStepDelayMs)
        }

        newPlayer.volume = maxVolume
        releasePlayer(oldPlayer)
    }

    private fun releasePlayer(player: ExoPlayer) {
        try {
            player.stop()
            player.release()
        } catch (e: Exception) {
        }
    }

    fun stop() {
        fadeJob?.cancel()
        isSwitching.set(false)
        pendingTrack.set(null)
        currentPlayerRef.getAndSet(null)?.let { releasePlayer(it) }
    }

    fun pause() {
        currentPlayerRef.get()?.let {
            it.playWhenReady = false
        }
    }

    fun resume() {
        if (isMusicEnabled) {
            currentPlayerRef.get()?.let {
                it.playWhenReady = true
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
