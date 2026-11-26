package com.example.core.utils

import com.example.core.data.source.local.entity.MovieEntity
import com.example.core.data.source.remote.response.MovieResponse
import com.example.core.data.source.remote.response.MovieVideoResponse
import com.example.core.domain.model.MovieDomain
import com.example.core.domain.model.MovieTrailerDomain

object DataMapper {
    fun mapResponsesToEntities(input: List<MovieResponse.NowMovie>): List<MovieEntity> {
        val movieList = ArrayList<MovieEntity>()
        input.forEach {
            val movie = MovieEntity(
                id = it.id.orZero(),
                title = it.title.orEmpty(),
                overview = it.overview.orEmpty(),
                releaseDate = DateHelper.getFormattedDate(it.releaseDate.orEmpty()),
                rating = NumberHelper.roundOneDecimal(it.voteAverage.orZero()),
                adult = ImageUrl.isAdult(it.adult.orFalse()),
                poster = ImageUrl.poster(it.posterPath),
                backdrop = ImageUrl.poster(it.backdropPath),
                isFavorite = false
            )
            movieList.add(movie)
        }
        return movieList
    }

    fun mapEntitiesToDomain(input: List<MovieEntity>): List<MovieDomain> = input.map {
        MovieDomain(
            id = it.id,
            title = it.title,
            overview = it.overview,
            releaseDate = it.releaseDate,
            voteAverage = it.rating,
            adult = it.adult,
            posterPath = it.poster,
            backdropPath = it.backdrop,
            isFavorite = it.isFavorite,
            genreIds = emptyList(),
            originalLanguage = "",
            originalTitle = "",
            popularity = 0.0,
            video = false,
            voteCount = 0
        )
    }

    fun mapDomainToEntity(input: MovieDomain) = MovieEntity(
        id = input.id,
        title = input.title,
        overview = input.overview,
        releaseDate = input.releaseDate,
        rating = input.voteAverage,
        adult = input.adult,
        poster = input.posterPath,
        backdrop = input.backdropPath,
        isFavorite = input.isFavorite
    )

    fun mapTrailerResponsesToDomainAs(
        title: String,
        item: MovieVideoResponse.MovieVideoItem
    ): MovieTrailerDomain {
        return MovieTrailerDomain(
            title = title,
            videoPath = YoutubeHelper.getVideoUrl(item.key),
            thumbnailPath = YoutubeHelper.getThumbnailUrl(item.key)
        )
    }
}
