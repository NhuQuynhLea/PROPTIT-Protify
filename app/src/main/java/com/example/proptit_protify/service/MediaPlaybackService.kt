package com.example.proptit_protify.service

import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import com.example.proptit_protify.R
import com.example.proptit_protify.album_detail.AlbumDetailFragment
import com.example.proptit_protify.models.Song


const val ACTION_PAUSE: Int = 1
const val ACTION_RESUME: Int = 2
const val ACTION_START: Int = 3
const val ACTION_NEXT: Int = 4
const val ACTION_PREV: Int = 5


class MediaPlaybackService : Service() {
    private lateinit var player: ExoPlayer
    private var isPlaying = false
    private lateinit var mSong: Song
    private lateinit var mListSong: List<Song>
    private var position = -1


    override fun onCreate() {
        super.onCreate()
        Log.e("TAG", "onCreate: " )
        player = ExoPlayer.Builder(applicationContext).build()
    }

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }
    
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val songs = intent?.getSerializableExtra("songs") as? List<Song>

        if(songs != null){
            mListSong = songs
        }

        val p = intent?.getIntExtra("position_song", -1)!!
        if(p != -1){
            position = p
            mSong = mListSong[position]
            startMusic()

        }

        val action = intent?.getIntExtra("action_music_service",0)
        if (action != null) {
            handleActionMusic(action)
        }
        return START_NOT_STICKY
    }

    private fun startMusic() {
      if(isPlaying){
          pauseMusic()
      }

        val mediaItem = MediaItem.fromUri(mListSong[position].resource)
        player.setMediaItem(mediaItem)
        player.prepare()
        player.play()
        isPlaying = true
        sendNotification(mListSong[position])
        sendActionToFragment(ACTION_START)
    }

    private fun handleActionMusic(action:Int){
       when(action){
           ACTION_PAUSE -> pauseMusic()
           ACTION_RESUME -> resumeMusic()
           ACTION_NEXT -> nextMusic()
           ACTION_PREV -> nextMusic(false)
       }
    }

    private fun resumeMusic() {
        if(!isPlaying){
            player.prepare()
            player.play()
            isPlaying = true
            sendNotification(mSong)
            sendActionToFragment(ACTION_RESUME)
        }
    }

    private fun pauseMusic() {
        if (isPlaying) {
            player.pause()
            isPlaying = false
            sendNotification(mSong)
            sendActionToFragment(ACTION_PAUSE)
        }
    }

    private fun nextMusic(isNext: Boolean = true){
        if(isNext) setPosition()
        else setPosition(false)
        startMusic()
    }

    private fun setPosition(isIncrement: Boolean = true){
        if(isIncrement) {
           if(position == mListSong.size - 1){
               position = 0
           }else {
               position++
           }
        }else {
            if(position == 0){
                position = mListSong.size -1
            }
            else position--
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
        remoteViews.setOnClickPendingIntent(R.id.next_btn, getPendingIntent(this, ACTION_NEXT))
        remoteViews.setOnClickPendingIntent(R.id.previous_btn, getPendingIntent(this, ACTION_PREV))


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

    private fun sendActionToFragment( action: Int){
        val intent = Intent("send_data_to_fragment")
        val bundle = Bundle()
        bundle.putSerializable("song",mListSong[position])
        bundle.putBoolean("status_player", isPlaying)
        bundle.putInt("action_music", action)
        intent.putExtras(bundle)

        LocalBroadcastManager.getInstance(this).sendBroadcast(intent)
    }
}