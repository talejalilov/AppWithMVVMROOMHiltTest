package com.talejalilov.appwithmvvmroomhilttest.roomdb

import androidx.room.Database
import androidx.room.RoomDatabase
import com.talejalilov.appwithmvvmroomhilttest.model.Art

@Database(entities = [Art::class], version = 1)
abstract class ArtsDatabase : RoomDatabase(){

    abstract fun artDao() : ArtDao
}