package com.example.core.domain.usecase

import com.example.core.data.source.Resource
import com.example.core.domain.model.MovieTrailerDomain
import com.example.core.domain.model.MovieDomain
import kotlinx.coroutines.flow.Flow

interface MovieUseCase {
    fun getAllMovie(): Flow<Resource<List<MovieDomain>>>
    fun getFavoriteMovie(): Flow<List<MovieDomain>>
    fun setFavoriteMovie(movie: MovieDomain, state: Boolean)

    fun getTrailerMovie(): Flow<Resource<List<MovieTrailerDomain>>>
}