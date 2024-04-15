package com.example.proptit_protify.interfaces

import com.example.proptit_protify.models.Song

interface ItemClick {
    fun onCLick(song: Song)
}