package com.ahanuliak.iwatch.data.entity

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Delete


@Dao
interface MovieDAO {

    @Query("SELECT * from movie_table ORDER BY title ASC")
    fun getAlphabetizedMovies(): LiveData<List<Movie>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(movie: Movie)

    @Query("DELETE FROM movie_table")
    suspend fun deleteAll()

    @Delete
    suspend fun delete(movie: Movie)
}