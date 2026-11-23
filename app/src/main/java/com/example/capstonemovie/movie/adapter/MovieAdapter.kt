package com.example.capstonemovie.movie.adapter

import android.view.View
import com.example.capstonemovie.databinding.ItemMovieHighlightBinding
import com.example.capstonemovie.utils.view.BaseListAdapter
import com.example.capstonemovie.utils.view.load
import com.example.core.domain.model.MovieDomain
import com.example.core.utils.clickWithDebounce
import com.example.core.utils.toShortMonth

class MovieAdapter : BaseListAdapter<MovieDomain, ItemMovieHighlightBinding>(
    ItemMovieHighlightBinding::inflate
) {

    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }


    override fun onItemBind(): (MovieDomain, ItemMovieHighlightBinding, View, Int) -> Unit =
        { item, binding, itemView, _ ->
            binding.tvRelease.text = item.releaseDate.toShortMonth()
            binding.tvRating.text = item.voteAverage.toString()
            binding.tvAdult.text = item.adult
            binding.ivMovie.load(item.posterPath)

            itemView.clickWithDebounce {
                onItemClickCallback.onItemClicked(item)
            }
        }


    interface OnItemClickCallback {
        fun onItemClicked(data: MovieDomain)
    }
}