package com.example.capstonemovie.movie.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MovieUiModel(
    val id: Int = 0,
    val title: String = "",
    val overview: String = "",
    val releaseDate: String = "",
    val rating: String = "",
    val labelAdult: String = "",
    val posterPath: String = "",
    val backdropPath: String = "",
    val isFavorite: Boolean = false
) : Parcelable
