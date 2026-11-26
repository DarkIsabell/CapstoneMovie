package com.example.capstonemovie.movie.adapter

import android.view.View
import com.example.capstonemovie.databinding.ItemMovieHighlightBinding
import com.example.capstonemovie.movie.model.MovieUiModel
import com.example.capstonemovie.utils.view.BaseListAdapter
import com.example.capstonemovie.utils.view.load
import com.example.core.utils.clickWithDebounce

class MovieAdapter : BaseListAdapter<MovieUiModel, ItemMovieHighlightBinding>(
    inflaterFactory = ItemMovieHighlightBinding::inflate,
    areItemsTheSame = { oldItem, newItem -> oldItem.id == newItem.id }
) {

    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }


    override fun onItemBind(): (MovieUiModel, ItemMovieHighlightBinding, View, Int) -> Unit =
        { item, binding, itemView, _ ->
            binding.tvRelease.text = item.releaseDate
            binding.tvRating.text = item.rating
            binding.tvAdult.text = item.labelAdult
            binding.ivMovie.load(item.posterPath)

            itemView.clickWithDebounce {
                onItemClickCallback.onItemClicked(item)
            }
        }


    interface OnItemClickCallback {
        fun onItemClicked(data: MovieUiModel)
    }
}