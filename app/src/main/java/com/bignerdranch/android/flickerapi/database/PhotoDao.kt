package com.bignerdranch.android.flickerapi.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.bignerdranch.android.flickerapi.data.GalleryItem

@Dao
interface PhotoDao {
    @Query("SELECT * FROM photo_table ORDER BY id ASC ")
    fun readAllPhoto (): LiveData<List<GalleryItem>>
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addPhoto (vararg photo: GalleryItem)
    @Delete
    suspend fun deletePhoto (vararg photo: GalleryItem)
}