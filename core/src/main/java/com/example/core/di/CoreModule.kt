package com.example.core.di

import androidx.room.Room
import com.example.core.BuildConfig
import com.example.core.data.source.MovieRepositoryImp
import com.example.core.data.source.local.LocalDataSource
import com.example.core.data.source.local.room.MovieDatabase
import com.example.core.data.source.remote.RemoteDataSource
import com.example.core.data.source.remote.network.MovieService
import com.example.core.domain.repository.MovieRepository
import com.example.core.domain.usecase.MovieInteractor
import com.example.core.domain.usecase.MovieUseCase
import com.example.core.utils.AppExecutors
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val databaseModule = module {
    factory { get<MovieDatabase>().movieDao() }

    single {
        Room.databaseBuilder(
            androidContext(),
            MovieDatabase::class.java, "Tourism.db"
        ).fallbackToDestructiveMigration().build()
    }
}

val networkModule = module {
    single {
        OkHttpClient.Builder()
            .addInterceptor { chain ->
                val req = chain.request().newBuilder()
                    .addHeader("Authorization", "Bearer ${BuildConfig.TOKEN}")
                    .build()
                chain.proceed(req)
            }
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .connectTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .build()
    }

    single {
        val retrofit = Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(get())
            .build()

        retrofit.create(MovieService::class.java)
    }
}

val repositoryModule = module {
    single { LocalDataSource(get()) }
    single { RemoteDataSource(get()) }
    factory { AppExecutors() }
    single<MovieRepository> { MovieRepositoryImp(get(), get(), get()) }
}

val useCaseModule = module {
    factory<MovieUseCase> { MovieInteractor(get()) }
}