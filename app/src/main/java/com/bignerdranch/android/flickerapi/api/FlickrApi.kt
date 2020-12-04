package com.bignerdranch.android.flickerapi.api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface FlickrApi {

    @GET("services/rest?method=flickr.photos.search")
    fun searchPhotos(@Query("lat") latutide: String,
                     @Query("lon") longtutide: String,
   ): Call<FlickerResponse>
}

