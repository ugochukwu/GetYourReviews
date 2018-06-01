package com.onwordiesquire.android.getyourreviews.data.datasource

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Entity
import android.arch.persistence.room.Insert
import android.arch.persistence.room.PrimaryKey
import android.arch.persistence.room.Query
import io.reactivex.Single

@Entity
data class Review(

        @PrimaryKey(autoGenerate = true)
        val review_id: Int = 0,
        val rating: Double,
        val title: String,
        val message: String,
        val author: String,
        val date: String
)

@Dao
interface ReviewDao {
    @Query("Select * from review")
    fun getAll(): Single<List<Review>>

    @Insert
    fun insertAll(vararg review: Review)

    @Query("DELETE FROM Review")
    fun deleteAll()
}