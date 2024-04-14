package com.example.proptit_protify.pojo

import android.util.Log
import com.example.proptit_protify.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

class HeaderInterceptor : Interceptor {
    private val key = BuildConfig.RAPID_API_KEY
    private val host = BuildConfig.RAPID_API_HOST
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
