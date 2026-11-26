package com.example.capstonemovie.movie.mapper

import com.example.capstonemovie.movie.model.MovieUiModel
import com.example.core.domain.model.MovieDomain
import com.example.core.utils.toShortMonth

object MovieMapper {

    fun mapDomainToUiModel(input: MovieDomain): MovieUiModel = MovieUiModel(
        id = input.id,
        title = input.title,
        overview = input.overview,
        releaseDate = input.releaseDate.toShortMonth(),
        rating = input.voteAverage.toString(),
        labelAdult = input.adult,
        posterPath = input.posterPath,
        backdropPath = input.backdropPath,
        isFavorite = input.isFavorite,
    )

    fun mapDomainListToUiModelList(input: List<MovieDomain>): List<MovieUiModel> =
        input.map { mapDomainToUiModel(it) }

    fun mapUiModelToDomain(input: MovieUiModel): MovieDomain = MovieDomain(
        id = input.id,
        title = input.title,
        overview = input.overview,
        releaseDate = input.releaseDate,
        voteAverage = input.rating.toDoubleOrNull() ?: 0.0,
        adult = input.labelAdult,
        posterPath = input.posterPath,
        backdropPath = input.backdropPath,
        isFavorite = input.isFavorite,
        genreIds = emptyList(),
        originalLanguage = "",
        originalTitle = "",
        popularity = 0.0,
        video = false,
        voteCount = 0,
    )
}

fun List<MovieDomain>.toUiModelList(): List<MovieUiModel> =
    MovieMapper.mapDomainListToUiModelList(this)

fun MovieUiModel.toDomain(): MovieDomain = MovieMapper.mapUiModelToDomain(this)
