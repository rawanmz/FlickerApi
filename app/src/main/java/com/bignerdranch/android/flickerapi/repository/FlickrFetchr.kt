package com.bignerdranch.android.flickerapi.repository

import androidx.lifecycle.LiveData
import com.bignerdranch.android.flickerapi.api.FlickerResponse
import com.bignerdranch.android.flickerapi.api.FlickrApi
import com.bignerdranch.android.flickerapi.data.GalleryItem
import com.bignerdranch.android.flickerapi.database.PhotoDao


private const val TAG = "FlickrFetchr"
open class FlickrFetchr (private val phototDao : PhotoDao,
                         private val flickrApi: FlickrApi) {
    val readAllProduct : LiveData<List<GalleryItem>> = phototDao.readAllPhoto()
    suspend fun addPhoto(photo:GalleryItem){
        phototDao.addPhoto(photo)
    }
    suspend fun deletePhoto(photo:GalleryItem){
        phototDao.deletePhoto(photo)
    }
    suspend fun searchPhotos(lat: String,lon:String): List<GalleryItem> {
        return flickrApi.searchPhotos(lat,lon).photos.photos
    }
}


