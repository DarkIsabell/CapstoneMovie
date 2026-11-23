package com.example.capstonemovie.moviedetail

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.capstonemovie.R
import com.example.capstonemovie.databinding.ActivityDetailMovieBinding
import com.example.capstonemovie.movie.MovieFragment.Companion.EXTRA_MOVIE
import com.example.capstonemovie.utils.view.load
import com.example.core.domain.model.MovieDomain
import com.example.core.utils.clickWithDebounce
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailMovieActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailMovieBinding
    private val viewModel: DetailMovieViewModel by viewModel()
    private val movie: MovieDomain by lazy {
        intent?.getParcelableExtra(EXTRA_MOVIE) ?: MovieDomain()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityDetailMovieBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupDetailView()
        setupListener()
    }

    private fun setupListener() = with(binding) {
        var statusFavorite = movie.isFavorite
        setStatusFavorite(statusFavorite)
        fabMovie.clickWithDebounce {
            statusFavorite = !statusFavorite
            viewModel.setFavoriteMovie(movie, statusFavorite)
            setStatusFavorite(statusFavorite)
        }

        ivBack.clickWithDebounce {
            onBackPressedDispatcher.onBackPressed()
        }
    }

    private fun setupDetailView() = with(binding) {
        tvTitle.text = movie.title
        tvOverview.text = movie.overview
        tvDate.text = movie.releaseDate
        tvRating.text = movie.voteAverage.toString()
        ivPoster.load(movie.posterPath)
        ivTrailer.load(movie.backdropPath)
    }

    private fun setStatusFavorite(statusFavorite: Boolean) {
        if (statusFavorite) {
            binding.fabMovie.setImageDrawable(
                ContextCompat.getDrawable(
                    this,
                    R.drawable.ic_favorite_white
                )
            )
        } else {
            binding.fabMovie.setImageDrawable(
                ContextCompat.getDrawable(
                    this,
                    R.drawable.ic_not_favorite_white
                )
            )
        }
    }
}