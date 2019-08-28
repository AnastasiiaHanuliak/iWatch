package com.ahanuliak.iwatch.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.ahanuliak.iwatch.data.entity.Movie
import com.ahanuliak.iwatch.data.entity.MovieRoomDatabase
import com.ahanuliak.iwatch.repos.MovieRepository
import kotlinx.coroutines.launch

class MovieViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: MovieRepository

    val allMovies: LiveData<List<Movie>>

    init {
        val moviesDao = MovieRoomDatabase.getDatabase(
            application,
            viewModelScope
        ).movieDAO()
        repository = MovieRepository(moviesDao)
        allMovies = repository.allMovies
    }

    fun insert(movie: Movie) = viewModelScope.launch {
        repository.insert(movie)
    }

    fun delete(movie: Movie) = viewModelScope.launch {
        repository.delete(movie)
    }

    fun deleteAll() = viewModelScope.launch {
        repository.deleteAll()
    }

}