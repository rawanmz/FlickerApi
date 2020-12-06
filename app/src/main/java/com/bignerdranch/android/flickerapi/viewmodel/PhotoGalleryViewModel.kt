package com.bignerdranch.android.flickerapi.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bignerdranch.android.flickerapi.ServiceLocator
import com.bignerdranch.android.flickerapi.data.GalleryItem
import kotlinx.coroutines.launch

class PhotoGalleryViewModel :ViewModel(){

    var flickrfitcher= ServiceLocator.flickerResponse

    val photos: LiveData<List<GalleryItem>> = flickrfitcher.readAllProduct
    fun addPhoto(photo: GalleryItem) {
        viewModelScope.launch {
            flickrfitcher.addPhoto(photo)
        }
    }
    fun deletePhoto(photo: GalleryItem) {
        viewModelScope.launch {
            flickrfitcher.deletePhoto(photo)
        }
    }
    fun fetchPhoto(lat: String, lon: String): LiveData<List<GalleryItem>> {
        val imagelist=MutableLiveData<List<GalleryItem>>()
        viewModelScope.launch {
            imagelist.value= flickrfitcher.searchPhotos(lat,lon)
        }
        return imagelist
    }
}