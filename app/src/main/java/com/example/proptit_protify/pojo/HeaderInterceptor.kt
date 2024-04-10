package com.example.proptit_protify.pojo

import android.util.Log
import okhttp3.Interceptor
import okhttp3.Response

class HeaderInterceptor : Interceptor {
    private val key: String
        get() = System.getenv("RAPID_API_KEY") ?: "6ee5053a74msh40d24e084cea8e1p1db7ebjsn8c58152ee1e4"
    private val host: String
        get() = System.getenv("RAPID_API_HOST") ?: "deezerdevs-deezer.p.rapidapi.com"
    override fun intercept(chain: Interceptor.Chain): Response {
        Log.e("Header key", key)
        Log.e("Header host", host)
        val request = chain.request().newBuilder()
            .addHeader("X-RapidAPI-Key", key)
            .addHeader("X-RapidAPI-Host", host)
            .build()
        return chain.proceed(request)
    }
}
