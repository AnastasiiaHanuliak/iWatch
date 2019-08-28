package com.ahanuliak.iwatch.repos

import androidx.lifecycle.LiveData
import com.ahanuliak.iwatch.data.entity.Movie
import com.ahanuliak.iwatch.data.entity.MovieDAO

class MovieRepository(private val movieDAO: MovieDAO) {

    val allMovies: LiveData<List<Movie>> = movieDAO.getAlphabetizedMovies()

    suspend fun insert(movie: Movie) {
        movieDAO.insert(movie)
    }

    suspend fun delete(movie: Movie) {
        movieDAO.delete(movie)
    }

    suspend fun deleteAll()
    {
        movieDAO.deleteAll()
    }


}
