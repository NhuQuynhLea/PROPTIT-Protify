package com.example.proptit_protify.song_detail

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.proptit_protify.pojo.RetrofitHelper
import com.example.proptit_protify.pojo.Track
import kotlinx.coroutines.launch

class SongDetailViewModel(application: Application): AndroidViewModel(application){
    private val retrofitBuilder = RetrofitHelper.apiService
    private var _track = MutableLiveData<Track>()
    internal fun findTrack(id: Long){
        viewModelScope.launch {
            val retrofitData = retrofitBuilder.getTrack(id)
            _track.value = retrofitData
        }
    }
    val track: LiveData<Track> = _track
}