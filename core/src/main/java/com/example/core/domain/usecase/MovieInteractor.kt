package com.example.core.domain.usecase

import com.example.core.domain.model.MovieDomain
import com.example.core.domain.repository.MovieRepository

class MovieInteractor(private val movieRepo: MovieRepository) : MovieUseCase {
    override fun getAllMovie() = movieRepo.getAllMovie()

    override fun getFavoriteMovie() = movieRepo.getFavoriteMovie()
    override fun setFavoriteMovie(movie: MovieDomain, state: Boolean) {
        movieRepo.setFavoriteMovie(movie, state)
    }

    override fun getTrailerMovie() = movieRepo.getTrailerMovies()

}
