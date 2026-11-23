package com.example.capstonemovie.moviedetail.model

data class MovieDetailUiModel(
    val id: Int,
    val backdrop: String,
    val poster: String,
    val title: String,
    val tagline: String,
    val rating: Double,
    val duration: String,
    val releaseDate: String,
    val oVerView: String,
)
