package com.textgame.data.audio

import android.content.Context
import android.media.MediaPlayer
import android.net.Uri

class BgmManager private constructor(private val context: Context) {

    private var activePlayer: MediaPlayer? = null
    private var currentTrack: BgmTrack? = null
    private var isMusicEnabled: Boolean = true

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
        if (currentTrack == track && activePlayer?.isPlaying == true) {
            return
        }

        if (!isMusicEnabled) {
            currentTrack = track
            return
        }

        val oldPlayer = activePlayer
        activePlayer = null
        
        if (oldPlayer != null) {
            releasePlayer(oldPlayer)
        }

        currentTrack = track

        try {
            val uri = Uri.parse("android.resource://${context.packageName}/${track.resId}")
            val newPlayer = MediaPlayer()
            newPlayer.setDataSource(context, uri)
            newPlayer.isLooping = true
            newPlayer.setVolume(0f, 0f)
            
            newPlayer.setOnPreparedListener { mp ->
                if (!isMusicEnabled) {
                    releasePlayer(mp)
                    return
                }
                
                activePlayer = mp
                mp.setVolume(1f, 1f)
                mp.start()
            }
            
            newPlayer.setOnErrorListener { mp, _, _ ->
                releasePlayer(mp)
                if (activePlayer == mp) activePlayer = null
                true
            }
            
            newPlayer.prepareAsync()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun releasePlayer(player: MediaPlayer) {
        try {
            player.stop()
        } catch (e: Exception) {
        }
        try {
            player.release()
        } catch (e: Exception) {
        }
    }

    fun stop() {
        activePlayer?.let { releasePlayer(it) }
        activePlayer = null
    }

    fun pause() {
        activePlayer?.let {
            if (it.isPlaying) {
                it.pause()
            }
        }
    }

    fun resume() {
        if (isMusicEnabled) {
            activePlayer?.let {
                if (!it.isPlaying) {
                    it.start()
                }
            }
        }
    }

    fun getCurrentTrack(): BgmTrack? = currentTrack

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
