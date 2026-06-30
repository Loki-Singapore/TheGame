package com.textgame.data.audio

import android.content.Context
import android.media.MediaPlayer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class BgmManager private constructor(private val context: Context) {

    private var mediaPlayer: MediaPlayer? = null
    private var currentTrack: BgmTrack? = null
    private var isMusicEnabled: Boolean = true
    private val fadeScope = CoroutineScope(Dispatchers.Main + Job())
    private var fadeJob: Job? = null
    private val maxVolume = 1.0f
    private val fadeDurationMs = 3000L
    private val fadeStepDelayMs = 30L

    fun setMusicEnabled(enabled: Boolean) {
        isMusicEnabled = enabled
        if (enabled) {
            currentTrack?.let { play(it) }
        } else {
            stop()
        }
    }

    fun isMusicEnabled(): Boolean = isMusicEnabled

    fun play(track: BgmTrack) {
        if (!isMusicEnabled) {
            currentTrack = track
            return
        }

        if (currentTrack == track && mediaPlayer?.isPlaying == true) {
            return
        }

        fadeJob?.cancel()

        val oldPlayer = mediaPlayer
        val oldTrack = currentTrack
        currentTrack = track

        try {
            val newPlayer = MediaPlayer.create(context.applicationContext, track.resId)
            newPlayer.isLooping = true
            newPlayer.setVolume(0f, 0f)
            newPlayer.start()
            mediaPlayer = newPlayer

            fadeJob = fadeScope.launch {
                fadeIn(newPlayer)
                if (oldPlayer != null && oldTrack != null) {
                    fadeOutAndRelease(oldPlayer)
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun stop() {
        fadeJob?.cancel()
        fadeJob = fadeScope.launch {
            mediaPlayer?.let {
                fadeOutAndRelease(it)
            }
            mediaPlayer = null
        }
    }

    fun pause() {
        mediaPlayer?.let {
            if (it.isPlaying) {
                it.pause()
            }
        }
    }

    fun resume() {
        if (isMusicEnabled) {
            mediaPlayer?.let {
                if (!it.isPlaying) {
                    it.start()
                }
            }
        }
    }

    fun getCurrentTrack(): BgmTrack? = currentTrack

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

    private suspend fun fadeOutAndRelease(player: MediaPlayer) {
        val steps = (fadeDurationMs / fadeStepDelayMs).toInt()

        for (i in 0..steps) {
            val progress = i.toFloat() / steps
            val eased = easeInOutCubic(progress)
            val volume = maxVolume * (1f - eased)
            try {
                player.setVolume(volume, volume)
            } catch (e: Exception) {
                break
            }
            delay(fadeStepDelayMs)
        }
        try {
            if (player.isPlaying) {
                player.stop()
            }
            player.release()
        } catch (e: Exception) {
        }
    }

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
