package com.example.trailer

import android.view.View
import com.example.capstonemovie.utils.view.BaseListAdapter
import com.example.capstonemovie.utils.view.load
import com.example.core.domain.model.MovieTrailerDomain
import com.example.core.utils.clickWithDebounce
import com.example.trailer.databinding.ItemTrailerBinding

class TrailerAdapter : BaseListAdapter<MovieTrailerDomain, ItemTrailerBinding>(
    ItemTrailerBinding::inflate
) {

    private var onItemClickCallback: OnItemClickCallback? = null

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback?) {
        this.onItemClickCallback = onItemClickCallback
    }


    override fun onItemBind(): (MovieTrailerDomain, ItemTrailerBinding, View, Int) -> Unit =
        { item, binding, itemView, _ ->
            binding.ivTrailer.load(item.thumbnailPath)
            binding.tvTitle.text = item.title


            itemView.clickWithDebounce {
                onItemClickCallback?.onItemClicked(item.videoPath)
            }
        }


    interface OnItemClickCallback {
        fun onItemClicked(link: String)
    }
}