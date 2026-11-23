package com.example.core.domain.repository

import com.example.core.data.source.Resource
import com.example.core.domain.model.MovieTrailerDomain
import com.example.core.domain.model.MovieDomain
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    fun getAllMovie(): Flow<Resource<List<MovieDomain>>>
    fun getFavoriteMovie(): Flow<List<MovieDomain>>
    fun setFavoriteMovie(movie: MovieDomain, state: Boolean)
    fun getTrailerMovies(): Flow<Resource<List<MovieTrailerDomain>>>
}