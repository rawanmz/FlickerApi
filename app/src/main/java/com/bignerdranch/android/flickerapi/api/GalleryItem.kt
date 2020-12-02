package com.bignerdranch.android.flickerapi.api

import android.net.Uri
import com.google.gson.annotations.SerializedName

data class GalleryItem(
    @SerializedName("title") var title: String = "",

    @SerializedName("id") var id: String = "",
    @SerializedName("url_s") var url: String = "",
    @SerializedName("owner") var owner: String = "",
    @SerializedName("latitude") var latitude: Double,
    @SerializedName("longitude") var longitude: Double,


    )
