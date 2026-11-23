package com.example.favorite

import android.view.View
import com.example.capstonemovie.utils.view.BaseListAdapter
import com.example.capstonemovie.utils.view.load
import com.example.core.domain.model.MovieDomain
import com.example.core.utils.clickWithDebounce
import com.example.core.utils.toShortMonth
import com.example.favorite.databinding.ItemFavoriteMovieBinding

class FavoriteAdapter : BaseListAdapter<MovieDomain, ItemFavoriteMovieBinding>(
    ItemFavoriteMovieBinding::inflate
) {

    private var onItemClickCallback: OnItemClickCallback? = null

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback?) {
        this.onItemClickCallback = onItemClickCallback
    }


    override fun onItemBind(): (MovieDomain, ItemFavoriteMovieBinding, View, Int) -> Unit =
        { item, binding, itemView, _ ->
            binding.tvRelease.text = item.releaseDate.toShortMonth()
            binding.tvRating.text = item.voteAverage.toString()
            binding.tvAdult.text = item.adult
            binding.ivMovie.load(item.posterPath)
            binding.tvTitle.text = item.title


            itemView.clickWithDebounce {
                onItemClickCallback?.onItemClicked(item)
            }
        }


    interface OnItemClickCallback {
        fun onItemClicked(data: MovieDomain)
    }
}