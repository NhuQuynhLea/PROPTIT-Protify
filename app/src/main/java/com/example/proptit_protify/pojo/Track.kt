package com.example.proptit_protify.pojo

import com.google.gson.annotations.SerializedName

data class Track(
    val album: AlbumX,
    val artist: ArtistX,
    @SerializedName("available_countries")
    val availableCountries: List<String>,
    val bpm: Double,
    val contributors: List<Contributor>,
    @SerializedName("disk_number")
    val diskNumber: Int,
    val duration: Int,
    @SerializedName("explicit_content_cover")
    val explicitContentCover: Int,
    @SerializedName("explicit_content_lyrics")
    val explicitContentLyrics: Int,
    @SerializedName("explicit_lyrics")
    val explicitLyrics: Boolean,
    val gain: Double,
    val id: Int,
    val isrc: String,
    val link: String,
    @SerializedName("md5_image")
    val md5Image: String,
    val preview: String,
    val rank: Int,
    val readable: Boolean,
    @SerializedName("release_date")
    val releaseDate: String,
    val share: String,
    val title: String,
    @SerializedName("title_short")
    val titleShort: String,
    @SerializedName("title_version")
    val titleVersion: String,
    @SerializedName("track_position")
    val trackPosition: Int,
    val type: String
)
