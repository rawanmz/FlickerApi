package com.bignerdranch.android.flickerapi.database

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.bignerdranch.android.flickerapi.data.GalleryItem
import com.bignerdranch.android.flickerapi.getOrAwaitValue
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@SmallTest
class PhotoDaoTest {

    @get:Rule
    var instantTaskExcutiveRule= InstantTaskExecutorRule()
    private lateinit var database: PhotoGalleryDatabase
    private lateinit var dao: PhotoDao

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            PhotoGalleryDatabase::class.java
        ).allowMainThreadQueries().build()
        dao = database.photoDao()
    }

    @After
    fun teardown() {
        database.close()
    }

    @Test
     fun addPhoto()= runBlockingTest{
            val Photo= GalleryItem("title","1",true,"url",
                "owner",24.444,-24.3333)
           dao.addPhoto(Photo)
            val allPhotos=dao.readAllPhoto().getOrAwaitValue()
        assertThat(allPhotos).contains(Photo)

    }
    @Test
    fun deletePhoto()= runBlockingTest {
        val Photo=GalleryItem("title","1",true,"url",
            "owner",24.444,-24.3333)
        dao.addPhoto(Photo)
        dao.deletePhoto(Photo)
        val allPhoto=dao.readAllPhoto().getOrAwaitValue()
        assertThat(allPhoto).doesNotContain(Photo)
    }

}