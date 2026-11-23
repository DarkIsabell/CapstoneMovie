package com.example.capstonemovie.moviedetail.model

data class ReviewMovieUIModel(
    val reviewList: List<ReviewItemUI> = listOf()
)

data class ReviewItemUI(
    val avatar: String = "",
    val name: String = "",
    val time: String = "",
    val review: String = "",
    val rating: Double = 0.0,
)
