import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.proptit_protify.BaseFragment
import com.example.proptit_protify.R
import com.example.proptit_protify.databinding.FragmentSearchBinding
import com.example.proptit_protify.search.SearchAdapter
import com.example.proptit_protify.search.SearchViewModel

class SearchFragment : BaseFragment<FragmentSearchBinding>() {
    private val searchViewModel: SearchViewModel by viewModels()

    override fun getFragmentBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentSearchBinding {
        return FragmentSearchBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val searchAdapter = SearchAdapter(
            onSongClick = { data ->
                navigateToNowPlayingScreen(data.id)
            }
        )
        binding.cancel.setOnClickListener {
            searchViewModel.search(binding.searchBar.text.toString())
        }
        binding.listSong.adapter = searchAdapter
        searchViewModel.data.observe(viewLifecycleOwner) { myData ->
            searchAdapter.submitList(myData.data)
        }
    }

    private fun navigateToNowPlayingScreen(idTrack: Long) {
        val bundle = Bundle().apply {
            putLong("idTrack", idTrack)
        }
        findNavController().navigate(R.id.action_searchFragment_to_songDetailFragment, bundle)
    }
}
