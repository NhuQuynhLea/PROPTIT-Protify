package com.example.proptit_protify.song_detail

import android.app.Application
import android.content.Intent
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.proptit_protify.data.ApiService
import com.example.proptit_protify.data.RetrofitHelper
import com.example.proptit_protify.data.Track
import kotlinx.coroutines.launch

class SongDetailViewModel(application: Application): AndroidViewModel(application){
    private val retrofitBuilder = RetrofitHelper.getInstance().create(ApiService::class.java)
    private var _track = MutableLiveData<Track>()
    internal fun findTrack(id: Long){
        viewModelScope.launch {
            val retrofitData = retrofitBuilder.getTrack(id)
            _track.value = retrofitData
        }
    }
    val track: LiveData<Track> = _track
}