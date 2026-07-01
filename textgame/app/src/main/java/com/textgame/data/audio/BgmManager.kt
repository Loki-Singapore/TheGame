package com.textgame.data.audio

import android.content.Context
import android.media.MediaPlayer
import android.net.Uri
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.concurrent.atomic.AtomicBoolean
import java.util.concurrent.atomic.AtomicReference

class BgmManager private constructor(private val context: Context) {

    private val currentPlayerRef = AtomicReference<MediaPlayer?>(null)
    private val currentTrackRef = AtomicReference<BgmTrack?>(null)
    private val isPreparing = AtomicBoolean(false)
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
        
        if (currentTrack == track && currentPlayer?.isPlaying == true) {
            pendingTrack.set(null)
            return
        }

        if (!isMusicEnabled) {
            pendingTrack.set(null)
            isPreparing.set(false)
            currentPlayerRef.getAndSet(null)?.let { releasePlayer(it) }
            currentTrackRef.set(track)
            return
        }

        if (isPreparing.get()) {
            pendingTrack.set(track)
            return
        }

        fadeJob?.cancel()

        val oldPlayer = currentPlayerRef.get()
        isPreparing.set(true)
        currentTrackRef.set(track)
        pendingTrack.set(null)

        val newPlayer = MediaPlayer()
        newPlayer.isLooping = true
        newPlayer.setVolume(0f, 0f)

        newPlayer.setOnPreparedListener { mp ->
            currentPlayerRef.set(mp)
            isPreparing.set(false)

            val pending = pendingTrack.getAndSet(null)
            if (pending != null && pending != track) {
                releasePlayer(mp)
                currentPlayerRef.set(null)
                play(pending)
                return@setOnPreparedListener
            }

            mp.start()

            fadeJob = scope.launch {
                if (oldPlayer != null) {
                    crossFade(mp, oldPlayer)
                } else {
                    fadeIn(mp)
                }
            }
        }

        newPlayer.setOnErrorListener { mp, _, _ ->
            isPreparing.set(false)
            currentPlayerRef.compareAndSet(mp, null)
            releasePlayer(mp)
            val pending = pendingTrack.getAndSet(null)
            if (pending != null) {
                play(pending)
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
            isPreparing.set(false)
            releasePlayer(newPlayer)
            val pending = pendingTrack.getAndSet(null)
            if (pending != null) {
                play(pending)
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

    fun stop() {
        fadeJob?.cancel()
        isPreparing.set(false)
        pendingTrack.set(null)
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
