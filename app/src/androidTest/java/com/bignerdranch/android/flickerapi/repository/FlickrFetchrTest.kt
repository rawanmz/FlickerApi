package com.bignerdranch.android.flickerapi.repository

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.bignerdranch.android.flickerapi.api.FlickerResponse
import com.bignerdranch.android.flickerapi.api.FlickrApi
import com.bignerdranch.android.flickerapi.api.PhotoResponse
import com.bignerdranch.android.flickerapi.database.PhotoGalleryDatabase
import okhttp3.Request
import okio.Timeout
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.times
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Call as Call

class FlickrFetchrTest{
    private lateinit var flickrFetchr: FlickrFetchr
    private lateinit var database: PhotoGalleryDatabase
    private lateinit var flickrApiMock: FlickrApi


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
    }

    @Test
    fun testSearchPhotosRequest() {
        val photos = PhotoResponse();
        photos.photos = listOf()
        val responsee:FlickerResponse= FlickerResponse()
        responsee.photos = photos;

        fun callll(): Call<FlickerResponse> {
            return object: Call<FlickerResponse>{
                override fun execute(): Response<FlickerResponse> {
                   return Response.success<FlickerResponse>(responsee )

                }

                override fun clone(): Call<FlickerResponse> {
                    TODO("Not yet implemented")
                }

                override fun enqueue(callback: Callback<FlickerResponse>) {
                    callback.onResponse(this,Response.success<FlickerResponse>(responsee ))
                }

                override fun isExecuted(): Boolean {
                    TODO("Not yet implemented")
                }

                override fun cancel() {
                    TODO("Not yet implemented")
                }

                override fun isCanceled(): Boolean {
                    TODO("Not yet implemented")
                }

                override fun request(): Request {
                    TODO("Not yet implemented")
                }

                override fun timeout(): Timeout {
                    TODO("Not yet implemented")
                }

            }
        }
        val response: Call<FlickerResponse> = callll()
        Mockito.`when`(flickrApiMock.searchPhotos("10","20"))
            .thenReturn(response)


       val responseList= flickrFetchr.searchPhotos("10","20").getOrAwaitValue()
            assert(photos.photos == responseList)
        Mockito.verify(flickrApiMock,times(1))
            .searchPhotos("10","20")
        }

    }




