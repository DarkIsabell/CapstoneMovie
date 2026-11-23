package com.example.trailer

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.core.domain.usecase.MovieUseCase

class TrailerViewModel (movieUseCase: MovieUseCase): ViewModel() {
    val trailer = movieUseCase.getTrailerMovie().asLiveData()
}