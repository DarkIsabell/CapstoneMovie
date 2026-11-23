package com.example.core.data.source.remote

import com.example.core.data.source.local.entity.MovieEntity
import com.example.core.data.source.remote.network.ApiResponse
import com.example.core.data.source.remote.network.MovieService
import com.example.core.data.source.remote.response.MovieResponse
import com.example.core.domain.model.MovieTrailerDomain
import com.example.core.utils.DataMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.sync.Semaphore
import kotlinx.coroutines.sync.withPermit

class RemoteDataSource(private val movieService: MovieService) {

    fun getNowPlayingMovie(): Flow<ApiResponse<List<MovieResponse.NowMovie>>> {
        return flow {
            try {
                val response = movieService.getNowPlayingMovie()
                val dataArray = response.movieList
                if (!dataArray.isNullOrEmpty()) {
                    emit(ApiResponse.Success(dataArray))
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.message.toString()))
            }
        }.flowOn(Dispatchers.IO)
    }

    fun getMovieTrailers(
        movies: List<MovieEntity>
    ): Flow<ApiResponse<List<MovieTrailerDomain>>> {
        return flow {
            if (movies.isEmpty()) {
                emit(ApiResponse.Empty)
                return@flow
            }

            try {
                val semaphore = Semaphore(permits = 4)
                val trailers = coroutineScope {
                    movies.distinctBy { it.id }.map { movie ->
                        async {
                            semaphore.withPermit {
                                val lastVideo = movieService.getMovieVideos(movie.id)
                                    .results
                                    ?.lastOrNull { !it.key.isNullOrBlank() }

                                lastVideo?.let {
                                    DataMapper.mapTrailerResponsesToDomainAs(
                                        movie.title,
                                        it
                                    )
                                }
                            }
                        }
                    }.awaitAll().filterNotNull()
                }

                if (trailers.isNotEmpty()) {
                    emit(ApiResponse.Success(trailers))
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.message.toString()))
            }
        }.flowOn(Dispatchers.IO)
    }
}