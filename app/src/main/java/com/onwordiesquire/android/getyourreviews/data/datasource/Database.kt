package com.onwordiesquire.android.getyourreviews.data.datasource

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase

@Database(entities = arrayOf(Review::class), version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun reviewDao(): ReviewDao
}