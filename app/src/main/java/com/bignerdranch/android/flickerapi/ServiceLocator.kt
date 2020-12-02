package com.bignerdranch.android.flickerapi

import android.content.Context
import com.bignerdranch.android.flickerapi.api.FlickrApi
import com.bignerdranch.android.flickerapi.api.PhotoInterceptor
import com.bignerdranch.android.flickerapi.repository.FlickrFetchr

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ServiceLocator {
    private lateinit var app: App
    lateinit var retrofit: Retrofit
    private lateinit var photoRemoteSource: FlickrApi

    fun init(app: App) {
        this.app = app
        initializeNetwork(app)
    }
    private fun getOkhttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(PhotoInterceptor())
            .build()
    }
    private fun initializeNetwork(context: Context) {
        retrofit = Retrofit.Builder()
            .baseUrl("https://api.flickr.com/")
            .client(getOkhttpClient())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        photoRemoteSource = retrofit.create(FlickrApi::class.java)
    }
val flickerResponse:FlickrFetchr by lazy {
    FlickrFetchr(photoRemoteSource)
}
}