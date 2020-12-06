package com.bignerdranch.android.flickerapi

import com.bignerdranch.android.flickerapi.api.FlickrApi
import org.junit.Before
import org.mockito.Mockito

class ApiTest {
    private lateinit var flickrApi: FlickrApi

    @Before
    fun setup() {
        flickrApi = Mockito.mock(FlickrApi::class.java)

    }
}