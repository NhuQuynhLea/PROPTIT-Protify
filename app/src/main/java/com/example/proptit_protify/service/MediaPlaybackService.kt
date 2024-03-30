package com.example.proptit_protify.service

import android.app.Service
import android.content.Intent
import android.net.Uri
import android.os.Binder
import android.os.IBinder
import androidx.annotation.OptIn
import androidx.core.net.toUri
import androidx.media3.common.MediaItem
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.source.DefaultMediaSourceFactory
import androidx.media3.exoplayer.source.MediaSource
import androidx.media3.exoplayer.source.ProgressiveMediaSource
import androidx.media3.ui.PlayerNotificationManager

class MediaPlaybackService: Service() {
    inner class MusicBinder: Binder(){
        fun getService(): MediaPlaybackService{
            return this@MediaPlaybackService
        }
    }
    private lateinit var player: ExoPlayer
    private val binder = MusicBinder()
    override fun onCreate() {
        super.onCreate()
        initializePlayer()
    }
    private fun initializePlayer(){
        player = ExoPlayer.Builder(this).build()
    }
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if (intent != null && intent.hasExtra("songUri")) {
            val uri = intent.getStringExtra("songUri")
            playSong(uri!!)
        }
        return START_STICKY
    }
    private fun playSong(uri: String){
        val mediaItem = MediaItem.fromUri(uri.toUri())
        player.setMediaItem(mediaItem)
        player.prepare()
        player.play()
    }
    override fun onBind(intent: Intent?): IBinder {
        return binder
    }
    override fun onDestroy() {
        super.onDestroy()
        player.release()
    }
}