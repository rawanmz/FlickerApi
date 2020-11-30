package com.bignerdranch.android.flickerapi.api

import com.google.gson.annotations.SerializedName

class PhotoResponse {
    @SerializedName("photo")
    lateinit var photos:List<GalleryItem>
}