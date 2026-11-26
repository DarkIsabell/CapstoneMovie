package com.example.capstonemovie.moviedetail

import androidx.lifecycle.ViewModel
import com.example.capstonemovie.movie.mapper.toDomain
import com.example.capstonemovie.movie.model.MovieUiModel
import com.example.core.domain.usecase.MovieUseCase

class DetailMovieViewModel(private val movieUseCase: MovieUseCase) : ViewModel() {
    fun setFavoriteMovie(newMovie: MovieUiModel, newStatus: Boolean) {
        movieUseCase.setFavoriteMovie(newMovie.toDomain(), newStatus)
    }
}