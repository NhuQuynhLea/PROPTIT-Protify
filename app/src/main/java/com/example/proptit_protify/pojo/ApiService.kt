package com.example.proptit_protify.pojo

import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("search")
    suspend fun getData(@Query("q") query: String): MyData
    @GET("track/{id}")
    suspend fun getTrack(@Path("id") id: Long): Track
}
