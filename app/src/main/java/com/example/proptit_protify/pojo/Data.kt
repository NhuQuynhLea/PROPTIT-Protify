package com.example.proptit_protify.pojo

import com.google.gson.annotations.SerializedName

data class Data(
    val album: Album,
    val artist: Artist,
    val duration: Int,
    @SerializedName("explicit_content_cover")
    val explicitContentCover: Int,
    @SerializedName("explicit_content_lyrics")
    val explicitContentLyrics: Int,
    @SerializedName("explicit_lyrics")
    val explicitLyrics: Boolean,
    val id: Long,
    val link: String,
    @SerializedName("md5_image")
    val md5Image: String,
    val preview: String,
    val rank: Int,
    val readable: Boolean,
    val title: String,
    @SerializedName("title_short")
    val titleShort: String,
    @SerializedName("title_version")
    val titleVersion: String,
    val type: String
)
