package com.example.proptit_protify.service

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build

class MyApplication : Application() {

    val CHANNEL_ID: String = "channel_example"
    override fun onCreate() {
        super.onCreate()
        createChannelNotification()
    }

    private fun createChannelNotification() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(CHANNEL_ID, "name", NotificationManager.IMPORTANCE_DEFAULT)
            channel.setSound(null,null)
            // Register the channel with the system.
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
}