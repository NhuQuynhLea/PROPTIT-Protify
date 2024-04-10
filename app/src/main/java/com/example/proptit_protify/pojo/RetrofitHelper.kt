package com.example.proptit_protify.pojo

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitHelper {
    private val BASE_URL="https://deezerdevs-deezer.p.rapidapi.com/"
    private val retrofit by lazy {
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(HeaderInterceptor()) // Thêm Interceptor vào OkHttpClient
            .build()
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient) // Sử dụng OkHttpClient đã được cấu hình
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    val apiService: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }
}