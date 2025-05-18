package com.example.week7

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.Build
import android.os.IBinder


class ForegroundService : Service() {

    override fun onBind(intent: Intent): IBinder? {
        return null // 사용하지 않음을 의미한다.
    }

    override fun onCreate() {
        super.onCreate()

    }


}