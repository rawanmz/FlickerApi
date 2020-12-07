package com.bignerdranch.android.flickerapi.viewmodel



import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.bignerdranch.android.flickerapi.getOrAwaitValue
import com.google.common.truth.Truth.assertThat
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
}