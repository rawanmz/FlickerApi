package com.bignerdranch.android.flickerapi.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.bignerdranch.android.flickerapi.data.GalleryItem

@Database(entities = [GalleryItem::class] ,  version = 1)
abstract class PhotoGalleryDatabase :RoomDatabase(){
    abstract fun photoDao() : PhotoDao
}