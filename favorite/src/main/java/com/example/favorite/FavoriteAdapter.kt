package com.example.favorite

import android.view.View
import com.example.capstonemovie.movie.model.MovieUiModel
import com.example.capstonemovie.utils.view.BaseListAdapter
import com.example.capstonemovie.utils.view.load
import com.example.core.utils.clickWithDebounce
import com.example.favorite.databinding.ItemFavoriteMovieBinding

class FavoriteAdapter : BaseListAdapter<MovieUiModel, ItemFavoriteMovieBinding>(
    inflaterFactory = ItemFavoriteMovieBinding::inflate,
    areItemsTheSame = { oldItem, newItem -> oldItem.id == newItem.id }
) {

    private var onItemClickCallback: OnItemClickCallback? = null

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback?) {
        this.onItemClickCallback = onItemClickCallback
    }


    override fun onItemBind(): (MovieUiModel, ItemFavoriteMovieBinding, View, Int) -> Unit =
        { item, binding, itemView, _ ->
            binding.tvRelease.text = item.releaseDate
            binding.tvRating.text = item.rating
            binding.tvAdult.text = item.labelAdult
            binding.ivMovie.load(item.posterPath)
            binding.tvTitle.text = item.title


            itemView.clickWithDebounce {
                onItemClickCallback?.onItemClicked(item)
            }
        }


    interface OnItemClickCallback {
        fun onItemClicked(data: MovieUiModel)
    }
}
