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
import java.util.concurrent.atomic.AtomicReference

class BgmManager private constructor(private val context: Context) {

    private val currentPlayerRef = AtomicReference<MediaPlayer?>(null)
    private val currentTrackRef = AtomicReference<BgmTrack?>(null)
    private var isMusicEnabled: Boolean = true
    private val fadeScope = CoroutineScope(Dispatchers.Main + Job())
    private var fadeJob: Job? = null
    private val mainHandler = Handler(Looper.getMainLooper())
    private val maxVolume = 1.0f
    private val fadeDurationMs = 5000L
    private val fadeStepDelayMs = 50L

    fun setMusicEnabled(enabled: Boolean) {
        if (isMusicEnabled == enabled) return
        isMusicEnabled = enabled
        if (enabled) {
            currentTrackRef.get()?.let { playAsync(it) }
        } else {
            stop()
        }
    }

    fun isMusicEnabled(): Boolean = isMusicEnabled

    fun play(track: BgmTrack) {
        playAsync(track)
    }

    private fun playAsync(track: BgmTrack) {
        val existingTrack = currentTrackRef.get()
        if (existingTrack == track && currentPlayerRef.get()?.isPlaying == true) {
            return
        }

        if (!isMusicEnabled) {
            fadeJob?.cancel()
            currentPlayerRef.getAndSet(null)?.let { releasePlayer(it) }
            currentTrackRef.set(track)
            return
        }

        fadeJob?.cancel()

        val oldPlayer = currentPlayerRef.getAndSet(null)
        currentTrackRef.set(track)

        val newPlayer = MediaPlayer()
        newPlayer.isLooping = true
        newPlayer.setVolume(0f, 0f)

        newPlayer.setOnPreparedListener { mp ->
            currentPlayerRef.set(mp)
            fadeJob = fadeScope.launch {
                if (oldPlayer != null) {
                    mp.start()
                    crossFade(mp, oldPlayer)
                } else {
                    mp.setVolume(maxVolume, maxVolume)
                    mp.start()
                }
            }
        }

        newPlayer.setOnErrorListener { mp, _, _ ->
            if (currentPlayerRef.compareAndSet(mp, null)) {
                mainHandler.post { releasePlayer(mp) }
            }
            true
        }

        try {
            val resId = track.resId
            val uri = Uri.parse("android.resource://${context.packageName}/$resId")
            newPlayer.setDataSource(context, uri)
            newPlayer.prepareAsync()
        } catch (e: Exception) {
            e.printStackTrace()
            if (currentPlayerRef.compareAndSet(newPlayer, null)) {
                mainHandler.post { releasePlayer(newPlayer) }
            }
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
        mainHandler.post { releasePlayer(oldPlayer) }
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

    fun stop() {
        fadeJob?.cancel()
        currentPlayerRef.getAndSet(null)?.let { releasePlayer(it) }
    }

    fun pause() {
        currentPlayerRef.get()?.let {
            if (it.isPlaying) {
                it.pause()
            }
        }
    }

    fun resume() {
        if (isMusicEnabled) {
            currentPlayerRef.get()?.let {
                if (!it.isPlaying) {
                    it.start()
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
