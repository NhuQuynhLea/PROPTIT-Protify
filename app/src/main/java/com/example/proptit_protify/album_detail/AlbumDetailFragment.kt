package com.example.proptit_protify.album_detail


import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.proptit_protify.databinding.FragmentAlbumDetailBinding
import com.example.proptit_protify.interfaces.ItemClick
import com.example.proptit_protify.models.Song
import com.example.proptit_protify.service.MediaPlaybackService


class AlbumDetailFragment : Fragment(), ItemClick {
    private lateinit var binding: FragmentAlbumDetailBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: AlbumDetailAdapter
    private lateinit var viewModel: AlbumDetailViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAlbumDetailBinding.inflate(inflater,container,false)
        initComponent()

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

        viewModel = ViewModelProvider(this)[AlbumDetailViewModel::class.java]
        viewModel.getList()
        viewModel.listSongs.observe(viewLifecycleOwner) {
            adapter.setData(it)
        }
    }

    private fun clickStopService() {
        val intent = Intent(context, MediaPlaybackService::class.java)
        context?.stopService(intent)
    }
    private fun clickStartService() {
        val song = Song("Từng quen","Wren Evans","https://e-cdns-images.dzcdn.net/images/artist/19cc38f9d69b352f718782e7a22f9c32/56x56-000000-80-0-0.jpg","https://cdns-preview-1.dzcdn.net/stream/c-1ed50e5b3118c99be858fc305609e62a-15.mp3")
        val intent = Intent(context, MediaPlaybackService::class.java)
        val bundle = Bundle()
        bundle.putSerializable("song",song)
        intent.putExtras(bundle)

        context?.startService(intent)
    }



    override fun onCLick(song: Song) {
        clickStartService()
    }


}