package com.example.proptit_protify.album_detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.proptit_protify.models.Song

class AlbumDetailViewModel : ViewModel() {

    val listSongs by lazy { MutableLiveData<List<Song>>() }

    fun getList(){
        val list: List<Song> = listOf(
            Song("Title1", "Artist1", "Album1", "Genre1"),
            Song("Title2", "Artist2", "Album2", "Genre2"),
            Song("Title3", "Artist3", "Album3", "Genre3"),
            Song("Title4", "Artist4", "Album4", "Genre4")
        )
        listSongs.value = list
    }
}