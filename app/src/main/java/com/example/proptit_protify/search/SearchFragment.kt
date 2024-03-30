package com.example.proptit_protify.search

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.proptit_protify.R
import com.example.proptit_protify.databinding.FragmentSearchBinding
import com.example.proptit_protify.service.MediaPlaybackService

class SearchFragment : Fragment() {
    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!
    private val searchViewModel: SearchViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater,container,false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val searchAdapter = SearchAdapter(
            onSongClick = {data ->
                this.navigateToNowPlayingScreen(data.id)
            }
        )
        //Dùng tạm nút Cancel làm nút Search
        binding.cancel.setOnClickListener{
            searchViewModel.search(binding.searchBar.text.toString())
        }
        binding.listSong.adapter = searchAdapter
        binding.listSong.layoutManager = LinearLayoutManager(binding.root.context)
        searchViewModel.data.observe(viewLifecycleOwner){myData->
            searchAdapter.submitList(myData.data)
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    companion object{
        fun Fragment.navigateToNowPlayingScreen(idTrack: Long){
            val bundle = Bundle()
            bundle.putLong("idTrack", idTrack)
            findNavController().navigate(R.id.action_searchFragment_to_songDetailFragment,bundle)
        }
    }
}