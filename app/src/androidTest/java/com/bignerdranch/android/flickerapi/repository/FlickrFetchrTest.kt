package com.bignerdranch.android.flickerapi.repository

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.bignerdranch.android.flickerapi.api.FlickerResponse
import com.bignerdranch.android.flickerapi.api.FlickrApi
import com.bignerdranch.android.flickerapi.api.PhotoResponse
import com.bignerdranch.android.flickerapi.database.PhotoGalleryDatabase
import com.bignerdranch.android.flickerapi.viewmodel.PhotoGalleryViewModel
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers
import org.mockito.Mockito
import org.mockito.Mockito.*


class FlickrFetchrTest{
    private lateinit var flickrFetchr: FlickrFetchr
    private lateinit var database: PhotoGalleryDatabase
    private lateinit var flickrApiMock: FlickrApi
    private lateinit var viewModel: PhotoGalleryViewModel
    @Before
    fun setup() {
        flickrApiMock = Mockito.mock(FlickrApi::class.java)
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            PhotoGalleryDatabase::class.java
        ).allowMainThreadQueries().build()
        flickrFetchr = FlickrFetchr(
            flickrApi = flickrApiMock,
            phototDao = database.photoDao()
        )
        viewModel = PhotoGalleryViewModel()

    }
    //@Test
//    open fun testApiFetchDataSuccess() {
//        // Mock API response
//        `when`(flickrApiMock.searchPhotos("","")).thenReturn(XsiNilLoader.Single.just(NewsList()))
//        viewModel.fetchPhoto("","")
//        verify(observer).onChanged(flickrApiMock.LOADING_STATE)
//        verify(observer).onChanged(flickrApiMock.SUCCESS_STATE)
    }
//    suspend fun venueSearchListSavedOnSuccessfulSearch() {
//        //val responseCall: Call<FlickerResponse> = mock()
//        val responsee:FlickerResponse= FlickerResponse()
//
//        Mockito.`when`(flickrApiMock.searchPhotos(ArgumentMatchers.anyString(),ArgumentMatchers.anyString()))
//            .thenReturn(responsee)
//        flickrFetchr.searchPhotos("","")
//        Mockito.verify(responsee.photos.photos).indexOf(flickrFetchr.searchPhotos("",""))
//        val firstVenueName = "Cool first venue"
//        val firstVenue: GalleryItem = mock()
//        Mockito.`when`(firstVenue.url).thenReturn(firstVenueName)
//        val secondVenueName = "awesome second venue"
//        val secondVenue: GalleryItem = mock()
//        Mockito.`when`(secondVenue.url).thenReturn(secondVenueName)
//        val venueList = listOf(firstVenue, secondVenue)
//        val venueSearchResponse: FlickerResponse = mock()
//        Mockito.`when`(venueSearchResponse.photos).thenReturn(venueList)
//        val response = Response.success(venueSearchResponse)
//        flickrFetchr..value.onResponse(responsee, response)
//        val dataManagerVenueList = fl.venueList
//        MatcherAssert.assertThat(dataManagerVenueList,
//            CoreMatchers.`is`(CoreMatchers.equalTo(venueList)))
//    }


//    @Test
//    suspend fun testSearchPhotosRequest() {
//        val photos = PhotoResponse()
//        photos.photos = listOf()
//        Mockito.`when`(
//            flickrFetchr.searchPhotos(
//                ArgumentMatchers.anyString(),
//                ArgumentMatchers.anyString()
//            )
//        )
//        val responsee:FlickerResponse= FlickerResponse()
//        responsee.photos = photos;

        //fun callll(): FlickerResponse {
           // return object: FlickerRespons
//                override fun execute(): Response<FlickerResponse> {
//                   return Response.success<FlickerResponse>(responsee )
//
//                }
//
//                override fun clone(): Call<FlickerResponse> {
//                    TODO("Not yet implemented")
//                }
//
//                override fun enqueue(callback: Callback<FlickerResponse>) {
//                    callback.onResponse(this,Response.success<FlickerResponse>(responsee ))
//                }
//
//                override fun isExecuted(): Boolean {
//                    TODO("Not yet implemented")
//                }
//
//                override fun cancel() {
//                    TODO("Not yet implemented")
//                }
//
//                override fun isCanceled(): Boolean {
//                    TODO("Not yet implemented")
//                }
//
//                override fun request(): Request {
//                    TODO("Not yet implemented")
//                }
//
//                override fun timeout(): Timeout {
//                    TODO("Not yet implemented")
//                }
//
//            }
     //   }
//        val response: FlickerResponse
//                //= callll()
//        Mockito.`when`(flickrApiMock.searchPhotos("10", "20"))
//            .thenReturn(responsee)
//       val responseList= flickrFetchr.searchPhotos("10", "20")
//            assert(photos.photos == responseList)
//        Mockito.verify(flickrApiMock, times(1))
//            .searchPhotos("10", "20")
//        }
//  }




