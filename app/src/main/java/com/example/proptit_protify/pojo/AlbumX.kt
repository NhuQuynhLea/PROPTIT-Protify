package com.example.proptit_protify.pojo

import com.google.gson.annotations.SerializedName

data class AlbumX(
    val cover: String,
    @SerializedName("cover_big")
    val coverBig: String,
    @SerializedName("cover_medium")
    val coverMedium: String,
    @SerializedName("cover_small")
    val coverSmall: String,
    @SerializedName("cover_xl")
    val coverXl: String,
    val id: Int,
    val link: String,
    @SerializedName("md5_image")
    val md5Image: String,
    @SerializedName("release_date")
    val releaseDate: String,
    val title: String,
    @SerializedName("track_list")
    val trackList: String,
    val type: String
)
