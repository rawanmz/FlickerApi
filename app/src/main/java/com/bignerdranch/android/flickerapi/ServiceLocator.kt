package com.bignerdranch.android.flickerapi

import android.content.Context
import androidx.room.Room
import com.bignerdranch.android.flickerapi.api.FlickrApi
import com.bignerdranch.android.flickerapi.api.PhotoInterceptor
import com.bignerdranch.android.flickerapi.database.PhotoGalleryDatabase
import com.bignerdranch.android.flickerapi.repository.FlickrFetchr

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ServiceLocator {
    private lateinit var app: App
    lateinit var retrofit: Retrofit
    private lateinit var photoRemoteSource: FlickrApi
    lateinit var database: PhotoGalleryDatabase
    fun init(app: App) {
        this.app = app
        initializeNetwork(app)
        initializeDatabase(app)
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
    private fun initializeDatabase(context: Context) {
        database = Room.databaseBuilder(
            context,
            PhotoGalleryDatabase::class.java,
            "photo_db"
        ).fallbackToDestructiveMigration().build()
    }
    val flickerResponse:FlickrFetchr by lazy {
        FlickrFetchr(database.photoDao(), photoRemoteSource)
    }
}