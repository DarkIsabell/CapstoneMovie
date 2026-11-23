package com.example.core.data.source.remote.network

import com.example.core.data.source.remote.response.MovieResponse
import com.example.core.data.source.remote.response.MovieReviewResponse
import com.example.core.data.source.remote.response.MovieVideoResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieService {

    @GET("movie/now_playing")
    suspend fun getNowPlayingMovie(
        @Query("language") language: String = "en-US",
        @Query("page") page: Int = 1
    ): MovieResponse

    @GET("movie/{movie_id}/videos")
    suspend fun getMovieVideos(
        @Path("movie_id") movieId: Int
    ): MovieVideoResponse

    @GET("movie/{movie_id}/reviews")
    suspend fun getMovieReviews(
        @Path("movie_id") movieId: Int,
        @Query("page") page: Int = 1
    ): MovieReviewResponse

}