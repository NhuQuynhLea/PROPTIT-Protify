package com.example.proptit_protify.album_detail


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


    override fun onCLick(song: Song) {

    }


}