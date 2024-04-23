package com.example.proptit_protify.album_detail


import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.proptit_protify.R
import com.example.proptit_protify.databinding.FragmentAlbumDetailBinding
import com.example.proptit_protify.interfaces.ItemClick
import com.example.proptit_protify.models.Song
import com.example.proptit_protify.service.ACTION_NEXT
import com.example.proptit_protify.service.ACTION_PAUSE
import com.example.proptit_protify.service.ACTION_PREV
import com.example.proptit_protify.service.ACTION_RESUME
import com.example.proptit_protify.service.ACTION_START
import com.example.proptit_protify.service.MediaPlaybackService
import java.io.Serializable


class AlbumDetailFragment : Fragment(), ItemClick {
    private lateinit var binding: FragmentAlbumDetailBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: AlbumDetailAdapter
    private lateinit var viewModel: AlbumDetailViewModel
    private lateinit var broadCastReceiver: BroadcastReceiver
    private var isPlaying = false
    private lateinit var mSong: Song


    private lateinit var mediaPlaybackService: MediaPlaybackService

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAlbumDetailBinding.inflate(inflater,container,false)
        binding.dock.layoutNotify.isVisible = false
        initComponent()

        LocalBroadcastManager.getInstance(requireContext()).registerReceiver(broadCastReceiver, IntentFilter("send_data_to_fragment"))

        binding.startPlaylistBtn.setOnClickListener { clickStopService() }

        return binding.root
    }


    private fun initComponent() {
        recyclerView = binding.recyclerview
        adapter = AlbumDetailAdapter(requireContext(), this)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.addItemDecoration(
            DividerItemDecoration(
                context,
                DividerItemDecoration.VERTICAL
            )
        )

        mediaPlaybackService = MediaPlaybackService()


        viewModel = ViewModelProvider(this)[AlbumDetailViewModel::class.java]
        viewModel.getList()
        viewModel.listSongs.observe(viewLifecycleOwner) {
            adapter.setData(it)

            val i = Intent(context, MediaPlaybackService::class.java)
            val bundle = Bundle()
            bundle.putSerializable("songs", it as Serializable)
            i.putExtras(bundle)
            context?.startService(i)
        }


        broadCastReceiver = object :BroadcastReceiver() {
            override fun onReceive(p0: Context?, p1: Intent?) {
                if(p1 != null){
                    val bundle = p1.extras
                    if(bundle != null){
                        mSong = (bundle.getSerializable("song") as? Song)!!
                        isPlaying = bundle.getBoolean("status_player")
                        val actionMusic = bundle.getInt("action_music")

                        handleMusicDock(actionMusic)
                    }
                }
            }

        }


    }

    private fun handleMusicDock(actionMusic: Int) {
        when(actionMusic){
            ACTION_START -> {
                binding.dock.layoutNotify.isVisible = true
                showInfoSong()
                setStatusPlayOrPause()
            }
            ACTION_PAUSE -> setStatusPlayOrPause()
            ACTION_RESUME -> setStatusPlayOrPause()
        }
    }

    private fun showInfoSong() {
        binding.dock.imgSong.setImageResource(R.drawable.spotify)
        binding.dock.titleSongTxt.text = mSong.title
        binding.dock.artistNameTxt.text = mSong.artist

        binding.dock.playOrPause.setOnClickListener {
            if(isPlaying){
                sentActionToService(ACTION_PAUSE)
            }else{
                sentActionToService(ACTION_RESUME)
            }
        }
        binding.dock.nextBtn.setOnClickListener { sentActionToService(ACTION_NEXT) }
        binding.dock.previousBtn.setOnClickListener { sentActionToService(ACTION_PREV) }
    }


    private fun setStatusPlayOrPause(){
        if(isPlaying){
            binding.dock.playOrPause.setImageResource(R.drawable.ic_pause)
        }else {
            binding.dock.playOrPause.setImageResource(R.drawable.ic_play)
        }
    }


    private fun sentActionToService(action:Int) {
        val intent = Intent(context, MediaPlaybackService::class.java)
        intent.putExtra("action_music_service",action)

        context?.startService(intent)
    }

    private fun clickStopService() {
        val intent = Intent(context, MediaPlaybackService::class.java)
        context?.stopService(intent)
    }
    private fun clickStartService(position: Int) {
        val intent = Intent(context, MediaPlaybackService::class.java)
        val bundle = Bundle()
        bundle.putInt("position_song", position)
       // bundle.putSerializable("song",song)
        intent.putExtras(bundle)

        context?.startService(intent)
    }



    override fun onCLick(position: Int) {
        clickStartService(position)
    }

    override fun onDestroy() {
        super.onDestroy()
        LocalBroadcastManager.getInstance(requireContext()).unregisterReceiver(broadCastReceiver)
    }




}