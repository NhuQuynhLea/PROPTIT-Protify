package com.example.proptit_protify.album_detail


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.proptit_protify.databinding.FragmentAlbumDetailBinding
import com.example.proptit_protify.models.Song


class AlbumDetailFragment : Fragment() {
    private lateinit var binding: FragmentAlbumDetailBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: AlbumDetailAdapter
    private lateinit var listData: List<Song>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAlbumDetailBinding.inflate(inflater,container,false)
        initComponent()
        getList()

        return binding.root
    }

    private fun initComponent() {
        listData = ArrayList()
        recyclerView = binding.recyclerview
        adapter = AlbumDetailAdapter(requireContext(), listData)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(context)
    }

    private fun getList(){
        val list: List<Song> = listOf(
            Song("Title1", "Artist1", "Album1", "Genre1"),
            Song("Title2", "Artist2", "Album2", "Genre2"),
            Song("Title3", "Artist3", "Album3", "Genre3"),
            Song("Title4", "Artist4", "Album4", "Genre4")
        )
        adapter.setData(list)
        listData = list
    }




}