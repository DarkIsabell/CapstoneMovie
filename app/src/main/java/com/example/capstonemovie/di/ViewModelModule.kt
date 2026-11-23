package com.example.capstonemovie.di

import com.example.capstonemovie.movie.viewmodel.MovieViewModel
import com.example.capstonemovie.moviedetail.DetailMovieViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { MovieViewModel(get()) }
    viewModel { DetailMovieViewModel(get()) }
}