package com.example.proptit_protify.song_detail

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.OptIn
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.media3.common.MediaItem
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.proptit_protify.R
import com.example.proptit_protify.databinding.FragmentSongDetailBinding
import com.example.proptit_protify.pojo.Track
import com.example.proptit_protify.service.MediaPlaybackService

class SongDetailFragment : Fragment() {
    private var _binding: FragmentSongDetailBinding? = null
    private val binding get() = _binding!!
    private val songDetailViewModel: SongDetailViewModel by viewModels()
    private var selectedTrack: Track? = null
    private var idTrack: Long = 0
    private lateinit var exoPlayer: ExoPlayer
    private lateinit var mediaItem: MediaItem
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSongDetailBinding.inflate(inflater, container, false)
        return binding.root
    }
    @OptIn(UnstableApi::class) override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.downButton.setOnClickListener {
            findNavController().popBackStack()
        }
        idTrack = arguments?.getLong("idTrack") ?: 6461440
        songDetailViewModel.findTrack(idTrack)
        songDetailViewModel.track.observe(viewLifecycleOwner){track->
            selectedTrack = track
            binding.name.text = selectedTrack?.title
            binding.artist.text = selectedTrack?.artist?.name
            Glide.with(this)
                .load(selectedTrack?.album?.cover)
                .error(R.drawable.ic_pause)
                .placeholder(R.drawable.ic_download)
                .into(binding.avatar)
            val intent = Intent(context, MediaPlaybackService::class.java).apply {
                putExtra("songUri", selectedTrack?.preview)
            }
            context?.startService(intent)
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}