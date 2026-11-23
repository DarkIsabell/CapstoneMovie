package com.example.trailer

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val trailerModule = module {
    viewModel { TrailerViewModel(get()) }
}