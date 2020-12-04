package com.bignerdranch.android.flickerapi.api

import com.bignerdranch.android.flickerapi.data.GalleryItem
import com.google.gson.annotations.SerializedName

class PhotoResponse {
    @SerializedName("photo")
    lateinit var photos:List<GalleryItem>
}