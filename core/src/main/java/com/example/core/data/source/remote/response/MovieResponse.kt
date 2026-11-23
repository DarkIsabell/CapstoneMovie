package com.example.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class MovieResponse(
    @SerializedName("dates")
    val dates: Dates? = null,
    @SerializedName("page")
    val page: Int? = null,
    @SerializedName("results")
    val movieList: List<NowMovie>? = null,
    @SerializedName("total_pages")
    val totalPages: Int? = null,
    @SerializedName("total_results")
    val totalResults: Int? = null
) {
    data class Dates(
        @SerializedName("maximum")
        val maximum: String? = null,
        @SerializedName("minimum")
        val minimum: String? = null
    )

    data class NowMovie(
        @SerializedName("adult")
        val adult: Boolean? = false,
        @SerializedName("backdrop_path")
        val backdropPath: String? = null,
        @SerializedName("genre_ids")
        val genreIds: List<Int>? = null,
        @SerializedName("id")
        val id: Int? = null,
        @SerializedName("original_language")
        val originalLanguage: String? = null,
        @SerializedName("original_title")
        val originalTitle: String? = null,
        @SerializedName("overview")
        val overview: String? = null,
        @SerializedName("popularity")
        val popularity: Double? = null,
        @SerializedName("poster_path")
        val posterPath: String? = null,
        @SerializedName("release_date")
        val releaseDate: String? = null,
        @SerializedName("title")
        val title: String? = null,
        @SerializedName("video")
        val video: Boolean? = false,
        @SerializedName("vote_average")
        val voteAverage: Double? = null,
        @SerializedName("vote_count")
        val voteCount: Int? = null
    )
}
