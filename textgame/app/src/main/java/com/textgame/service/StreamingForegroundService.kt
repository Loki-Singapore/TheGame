package com.textgame.service

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat

/**
 * Foreground service that keeps the app process alive while AI streaming is in progress.
 *
 * Without this, switching apps causes Android to background the process, and the OS may
 * suspend/kill the streaming HTTP socket — surfacing a "connection interrupted" error to
 * the user. Running as a foreground service (with a low-priority notification) keeps the
 * process in a foreground state so the streaming connection stays alive.
 *
 * The service itself does no work; it only hosts the notification. The streaming logic
 * stays in [com.textgame.presentation.viewmodel.GameViewModel].
 */
class StreamingForegroundService : Service() {

    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        startForeground(NOTIFICATION_ID, buildNotification())
        return START_NOT_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? = null

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                getString(com.textgame.R.string.streaming_notification_channel_name),
                NotificationManager.IMPORTANCE_LOW
            ).apply {
                description = getString(com.textgame.R.string.streaming_notification_channel_desc)
                setShowBadge(false)
            }
            val manager = getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(channel)
        }
    }

    private fun buildNotification(): Notification {
        return NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle(getString(com.textgame.R.string.app_name))
            .setContentText(getString(com.textgame.R.string.streaming_notification_text))
            .setSmallIcon(android.R.drawable.stat_notify_sync)
            .setOngoing(true)
            .setPriority(NotificationCompat.PRIORITY_LOW)
            .build()
    }

    companion object {
        private const val CHANNEL_ID = "streaming_service"
        private const val NOTIFICATION_ID = 1001

        /**
         * Start the foreground service. Safe to call repeatedly; subsequent starts while
         * the service is already running just re-deliver the notification.
         */
        fun start(context: Context) {
            try {
                val intent = Intent(context, StreamingForegroundService::class.java)
                ContextCompat.startForegroundService(context, intent)
            } catch (_: Exception) {
                // Starting the service is best-effort: if it fails (e.g. background-start
                // restrictions on some OEMs), streaming still proceeds without a foreground
                // service — the user just won't be protected from app-switching disconnects.
            }
        }

        /**
         * Stop the foreground service. Safe to call when not running.
         */
        fun stop(context: Context) {
            try {
                context.stopService(Intent(context, StreamingForegroundService::class.java))
            } catch (_: Exception) {
            }
        }
    }
}
