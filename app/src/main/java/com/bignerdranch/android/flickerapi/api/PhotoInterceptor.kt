package com.bignerdranch.android.flickerapi.api

import android.util.Log
import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import retrofit2.http.Query

private const val API_KEY = "bee14d7a30ad17b9d4c87cd87770b0ff"
private const val TAG = "PhotoInterceptor"

class PhotoInterceptor : Interceptor{
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest : Request = chain.request()
        val newUrl : HttpUrl = originalRequest.url.newBuilder()
                .addQueryParameter("api_key" , API_KEY)
                .addQueryParameter("format" , "json")
                .addQueryParameter("nojsoncallback" , "1")
                .addQueryParameter("extras" , "url_s,geo")
                .addQueryParameter("safesearch" , "1")
                .build()
        val newRequest : Request = originalRequest.newBuilder()
                .url(newUrl)
                .build()
        return chain.proceed(newRequest)
    }
}