package com.bignerdranch.android.flickerapi.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.bignerdranch.android.flickerapi.ServiceLocator
import com.bignerdranch.android.flickerapi.api.GalleryItem

class PhotoGalleryViewModel :ViewModel(){

    var flickrfitcher= ServiceLocator.flickerResponse
    fun fetchPhoto(lat: String, lon: String): LiveData<List<GalleryItem>> {
        return flickrfitcher.searchPhotos(lat,lon)
    }
}