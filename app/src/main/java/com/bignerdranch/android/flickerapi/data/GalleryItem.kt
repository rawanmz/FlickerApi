package com.bignerdranch.android.flickerapi.data

import android.net.Uri
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "photo_table")
data class GalleryItem(
    @SerializedName("title") var title: String = "",
    @SerializedName("id")
    @PrimaryKey var id: String = "",
    @SerializedName("url_s") var url: String = "",
    @SerializedName("owner") var owner: String = "",
    @SerializedName("latitude") var latitude: Double,
    @SerializedName("longitude") var longitude: Double,
    ): Parcelable
