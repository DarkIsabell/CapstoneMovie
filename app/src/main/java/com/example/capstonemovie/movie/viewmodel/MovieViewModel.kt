package com.example.capstonemovie.movie.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.core.domain.usecase.MovieUseCase

class MovieViewModel(movieUseCase: MovieUseCase): ViewModel() {
    val movies = movieUseCase.getAllMovie().asLiveData()
}