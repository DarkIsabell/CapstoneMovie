package com.example.core.data.source

import com.example.core.data.source.local.LocalDataSource
import com.example.core.data.source.remote.RemoteDataSource
import com.example.core.data.source.remote.network.ApiResponse
import com.example.core.data.source.remote.response.MovieResponse
import com.example.core.domain.model.MovieTrailerDomain
import com.example.core.domain.model.MovieDomain
import com.example.core.domain.repository.MovieRepository
import com.example.core.utils.AppExecutors
import com.example.core.utils.DataMapper
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart

class MovieRepositoryImp(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val appExecutors: AppExecutors
) : MovieRepository {

    override fun getAllMovie(): Flow<Resource<List<MovieDomain>>> =
        object : NetworkBoundResource<List<MovieDomain>, List<MovieResponse.NowMovie>>() {

            override fun loadFromDB(): Flow<List<MovieDomain>> {
                return localDataSource.getAllMovie().map {
                    DataMapper.mapEntitiesToDomain(it)
                }
            }

            override fun shouldFetch(data: List<MovieDomain>?) =
                data.isNullOrEmpty()

            override suspend fun createCall(): Flow<ApiResponse<List<MovieResponse.NowMovie>>> {
                return remoteDataSource.getNowPlayingMovie()
            }

            override suspend fun saveCallResult(data: List<MovieResponse.NowMovie>) {
                val movieList = DataMapper.mapResponsesToEntities(data)
                localDataSource.insertMovie(movieList)
            }
        }.asFlow()


    override fun getFavoriteMovie(): Flow<List<MovieDomain>> {
        return localDataSource.getFavoriteMovie().map {
            DataMapper.mapEntitiesToDomain(it)
        }
    }

    override fun setFavoriteMovie(movie: MovieDomain, state: Boolean) {
        val movieEntity = DataMapper.mapDomainToEntity(movie)
        appExecutors.diskIO().execute { localDataSource.setFavoriteMovie(movieEntity, state) }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun getTrailerMovies(): Flow<Resource<List<MovieTrailerDomain>>> {
        return localDataSource.getAllMovie()
            .flatMapLatest { movies ->
                remoteDataSource.getMovieTrailers(movies).map { response ->
                    when (response) {
                        is ApiResponse.Success -> Resource.Success(response.data)
                        is ApiResponse.Empty -> Resource.Error("")
                        is ApiResponse.Error -> Resource.Error(response.errorMessage)
                    }
                }
            }.onStart {
                emit(Resource.Loading())
            }
    }

}
