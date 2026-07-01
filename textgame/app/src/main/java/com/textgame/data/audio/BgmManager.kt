package com.textgame.data.audio

import android.content.Context
import android.media.MediaPlayer
import android.net.Uri
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
    private val fadeDurationMs = 5000L
    private val fadeStepDelayMs = 50L

    fun setMusicEnabled(enabled: Boolean) {
        isMusicEnabled = enabled
        if (enabled) {
            currentTrack?.let { playAsync(it) }
        } else {
            stop()
        }
    }

    fun isMusicEnabled(): Boolean = isMusicEnabled

    fun play(track: BgmTrack) {
        playAsync(track)
    }

    private fun playAsync(track: BgmTrack) {
        if (currentTrack == track && mediaPlayer?.isPlaying == true) {
            return
        }

        if (!isMusicEnabled) {
            fadeJob?.cancel()
            mediaPlayer?.let { releasePlayer(it) }
            mediaPlayer = null
            currentTrack = track
            return
        }

        fadeJob?.cancel()

        val oldPlayer = mediaPlayer
        currentTrack = track

        val newPlayer = MediaPlayer()
        newPlayer.isLooping = true
        newPlayer.setVolume(0f, 0f)

        newPlayer.setOnPreparedListener { mp ->
            mediaPlayer = mp
            if (oldPlayer != null) {
                mp.start()
                fadeJob = fadeScope.launch {
                    crossFade(mp, oldPlayer)
                }
            } else {
                mp.setVolume(maxVolume, maxVolume)
                mp.start()
            }
        }

        newPlayer.setOnErrorListener { mp, _, _ ->
            releasePlayer(mp)
            true
        }

        try {
            val afd = context.assets.openFd("raw/bgm_${getAssetName(track)}.mp3")
            newPlayer.setDataSource(afd.fileDescriptor, afd.startOffset, afd.length)
            afd.close()
            newPlayer.prepareAsync()
        } catch (e: Exception) {
            try {
                val resId = track.resId
                val uri = Uri.parse("android.resource://${context.packageName}/$resId")
                newPlayer.setDataSource(context, uri)
                newPlayer.prepareAsync()
            } catch (e2: Exception) {
                e2.printStackTrace()
                releasePlayer(newPlayer)
            }
        }
    }

    private fun getAssetName(track: BgmTrack): String {
        return when (track) {
            BgmTrack.MAIN -> "main"
            BgmTrack.BATTLE -> "battle"
            BgmTrack.DANGER -> "danger"
            BgmTrack.VICTORY -> "victory"
            BgmTrack.ROMANCE -> "romance"
            BgmTrack.UNKNOWN_FEAR -> "unknown_fear"
            BgmTrack.SPRING_SUMMER -> "spring_summer"
            BgmTrack.AUTUMN -> "autumn"
            BgmTrack.WINTER -> "winter"
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
        mediaPlayer?.let { releasePlayer(it) }
        mediaPlayer = null
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
