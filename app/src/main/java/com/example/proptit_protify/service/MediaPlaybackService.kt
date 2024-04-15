package com.example.proptit_protify.service

import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.IBinder
import android.util.Log
import android.widget.ImageView
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import com.example.proptit_protify.R
import com.example.proptit_protify.album_detail.AlbumDetailFragment
import com.example.proptit_protify.models.Song

private const val ACTION_PAUSE: Int = 1
private const val ACTION_RESUME: Int = 2

class MediaPlaybackService : Service() {
    private lateinit var player: ExoPlayer
    private var isPlaying = false
    private lateinit var mSong: Song
    override fun onCreate() {
        super.onCreate()
        player = ExoPlayer.Builder(applicationContext).build()
        Log.e("TAG", "onCreate: " )
    }

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val song = intent?.getSerializableExtra("song") as? Song
        if(song != null){
            mSong = song
            startMusic(song)
            //sendNotification(song)
        }
        val action = intent?.getIntExtra("action_music_service",0)
        if (action != null) {
            handleActionMusic(action)
        }
        return START_NOT_STICKY
    }

    private fun startMusic(song: Song) {
        val mediaItem = MediaItem.fromUri(song.resource)
        player.setMediaItem(mediaItem)
        player.prepare()
        player.play()
        isPlaying = true
    }

    private fun handleActionMusic(action:Int){
       when(action){
           ACTION_PAUSE -> pauseMusic()
           ACTION_RESUME -> resumeMusic()
       }
    }

    private fun resumeMusic() {
        if(!isPlaying){
            player.prepare()
            player.play()
            isPlaying = true
            sendNotification(mSong)
        }
    }

    private fun pauseMusic() {
        if (isPlaying) {
            player.pause()
            isPlaying = false
            //sendNotification(mSong)
        }
    }

    private fun sendNotification(song: Song) {
        val intent = Intent(this, AlbumDetailFragment::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent: PendingIntent =
            PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
            
        val remoteViews = RemoteViews(packageName,R.layout.layout_custom_notification)
        remoteViews.setTextViewText(R.id.artist_name_txt, song.artist)
        remoteViews.setTextViewText(R.id.title_song_txt, song.title)
        remoteViews.setImageViewResource(R.id.img_song, R.drawable.spotify)

        if(isPlaying){
            remoteViews.setOnClickPendingIntent(R.id.play_or_pause, getPendingIntent(this, ACTION_PAUSE ))
            remoteViews.setImageViewResource(R.id.play_or_pause, R.drawable.ic_pause)
        }else{
            remoteViews.setOnClickPendingIntent(R.id.play_or_pause, getPendingIntent(this, ACTION_RESUME ))
            remoteViews.setImageViewResource(R.id.play_or_pause, R.drawable.ic_play)
        }


        val notification = NotificationCompat.Builder(this, "channel_example")
            .setSmallIcon(R.drawable.ic_music)
            .setContentIntent(pendingIntent)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setCustomContentView(remoteViews)
            .setSound(null)
            .build()


        startForeground(1, notification)

    }

    private fun getPendingIntent(context: Context, action: Int): PendingIntent? {
        val intent = Intent(this, MyBroadcastReceiver::class.java)
        intent.putExtra("action_music", action)
        return PendingIntent.getBroadcast(context,action,intent,PendingIntent.FLAG_UPDATE_CURRENT)
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.e("TAG", "onDestroy: " )
        player.release()
    }
}