package com.example.capstonemovie.movie.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.capstonemovie.movie.mapper.toUiModelList
import com.example.core.domain.usecase.MovieUseCase
import com.example.core.domain.utils.Resource
import kotlinx.coroutines.flow.map

class MovieViewModel(movieUseCase: MovieUseCase) : ViewModel() {
    val movies =
        movieUseCase.getAllMovie().map { resource ->
            when (resource) {
                is Resource.Success -> Resource.Success(
                    resource.data?.toUiModelList() ?: emptyList()
                )

                is Resource.Loading -> Resource.Loading()
                is Resource.Error -> Resource.Error(resource.message ?: "")
            }
        }.asLiveData()
}