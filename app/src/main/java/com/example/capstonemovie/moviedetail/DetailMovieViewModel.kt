package com.example.capstonemovie.moviedetail

import androidx.lifecycle.ViewModel
import com.example.core.domain.model.MovieDomain
import com.example.core.domain.usecase.MovieUseCase

class DetailMovieViewModel(private val movieUseCase: MovieUseCase): ViewModel() {
    fun setFavoriteMovie(newMovie: MovieDomain, newStatus: Boolean) {
        movieUseCase.setFavoriteMovie(newMovie,newStatus)
    }
}