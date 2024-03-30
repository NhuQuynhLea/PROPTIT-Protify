package com.example.proptit_protify.data

import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @Headers("X-RapidAPI-Key: 6ee5053a74msh40d24e084cea8e1p1db7ebjsn8c58152ee1e4",
        "X-RapidAPI-Host: deezerdevs-deezer.p.rapidapi.com")
    @GET("search")
    suspend fun getData(@Query("q") query: String): MyData
    @Headers("X-RapidAPI-Key: 6ee5053a74msh40d24e084cea8e1p1db7ebjsn8c58152ee1e4",
        "X-RapidAPI-Host: deezerdevs-deezer.p.rapidapi.com")
    @GET("track/{id}")
    suspend fun getTrack(@Path("id") id: Long): Track
}