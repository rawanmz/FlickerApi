package com.bignerdranch.android.flickerapi.viewmodel



import android.util.Log
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import com.bignerdranch.android.flickerapi.data.GalleryItem
import com.bignerdranch.android.flickerapi.getOrAwaitValue
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class PhotoGalleryViewModelTest {
private lateinit var viewModel: PhotoGalleryViewModel
    @get:Rule
    var rule = InstantTaskExecutorRule()
    @Before
    fun setUp() {
        viewModel = PhotoGalleryViewModel()
    }

    @Test
    fun fetchPhotoTestValue() {
        viewModel.fetchPhoto("24.8539523", "46.7133915")
        val value = viewModel.photos.getOrAwaitValue()
        assert(value.isNotEmpty())
    }
    @Test
    fun fetchPhotoTestWithNoValue() = runBlocking{
        val response: LiveData<List<GalleryItem>> =viewModel.fetchPhoto("","")
        val responseValue=response.value
        assert(responseValue.isNullOrEmpty())
    }
}