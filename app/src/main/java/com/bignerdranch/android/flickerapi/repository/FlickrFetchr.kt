package com.bignerdranch.android.flickerapi.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.bignerdranch.android.flickerapi.api.FlickerResponse
import com.bignerdranch.android.flickerapi.api.FlickrApi
import com.bignerdranch.android.flickerapi.data.GalleryItem
import com.bignerdranch.android.flickerapi.api.PhotoResponse
import com.bignerdranch.android.flickerapi.database.PhotoDao
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private const val TAG = "FlickrFetchr"
class FlickrFetchr (private val phototDao : PhotoDao,
                    private val flickrApi: FlickrApi) {
    val readAllProduct : LiveData<List<GalleryItem>> = phototDao.readAllPhoto()
suspend fun addPhoto(photo:GalleryItem){
    phototDao.addPhoto(photo)
}
    fun searchPhotosRequest(lon: String,lat:String): Call<FlickerResponse> {
        return flickrApi.searchPhotos(lon,lat)
    }
    fun searchPhotos(lon: String,lat:String): LiveData<List<GalleryItem>> {
        return fetchPhotoMetadata(searchPhotosRequest(lon,lat))
    }
     fun fetchPhotoMetadata(flickrRequest: Call<FlickerResponse>)
            : LiveData<List<GalleryItem>> {
        val responseLiveData: MutableLiveData<List<GalleryItem>> = MutableLiveData()
        flickrRequest.enqueue(object : Callback<FlickerResponse> {

            override fun onFailure(call: Call<FlickerResponse>, t: Throwable) {
                Log.e(TAG, "Failed to fetch photos", t)
            }

            override fun onResponse(call: Call<FlickerResponse>, response: Response<FlickerResponse>) {
                Log.d(TAG, "Response received")
                val flickrResponse: FlickerResponse? = response.body()
                Log.d(TAG, "Response received")

                val photoResponse: PhotoResponse? = flickrResponse?.photos
                var galleryItems: List<GalleryItem> = photoResponse?.photos
                        ?: mutableListOf()
                galleryItems = galleryItems.filterNot {
                    it.url.isBlank()
                }
                responseLiveData.value = galleryItems
            }
        })

        return responseLiveData
    }
}


