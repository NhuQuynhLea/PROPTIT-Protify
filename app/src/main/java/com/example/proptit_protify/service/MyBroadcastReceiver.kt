package com.example.proptit_protify.service

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent


class MyBroadcastReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val action = intent.getIntExtra("action_music",0)

        val intentService = Intent(context, MediaPlaybackService::class.java)
        intentService.putExtra("action_music_service", action)
        context.startService(intentService)
    }
}