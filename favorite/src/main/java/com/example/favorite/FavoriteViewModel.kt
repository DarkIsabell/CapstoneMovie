package com.example.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.capstonemovie.movie.mapper.toUiModelList
import com.example.core.domain.usecase.MovieUseCase
import kotlinx.coroutines.flow.map

class FavoriteViewModel(movieUseCase: MovieUseCase) : ViewModel() {
    val favoriteMovie = movieUseCase.getFavoriteMovie()
        .map { it.toUiModelList() }
        .asLiveData()
}