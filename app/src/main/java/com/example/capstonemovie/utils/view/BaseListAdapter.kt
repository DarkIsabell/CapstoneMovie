package com.example.capstonemovie.utils.view

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

abstract class BaseListAdapter<T : Any, VB : ViewBinding>(
    private val inflaterFactory: (LayoutInflater, ViewGroup?, Boolean) -> VB,
    areItemsTheSame: (T, T) -> Boolean = { oldItem, newItem -> oldItem == newItem }
) : ListAdapter<T, BaseListAdapter.BaseViewHolder<T, VB>>(BaseItemCallback(areItemsTheSame)) {
    abstract fun onItemBind(): (T, VB, View, Int) -> Unit
    protected open fun onPartialViewBind(): (T, VB, View, Int) -> Unit = { _, _, _, _ -> }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<T, VB> {
        val binding = inflaterFactory(LayoutInflater.from(parent.context), parent, false)
        return BaseViewHolder(binding.root, binding, onItemBind(), onPartialViewBind())
    }

    override fun onBindViewHolder(holder: BaseViewHolder<T, VB>, position: Int) {
        if (position >= itemCount) return
        val item = getItem(position)
        holder.bind(item)
    }

    override fun onBindViewHolder(
        holder: BaseViewHolder<T, VB>,
        position: Int,
        payloads: List<Any>
    ) {
        if (position >= itemCount) return
        val item = getItem(position)
        if (payloads.isEmpty()) {
            holder.bind(item)
        } else {
            holder.partialBind(item)
        }
    }

    class BaseViewHolder<T : Any, VB : ViewBinding>(
        view: View,
        private val binding: VB,
        private val onItemBind: (T, VB, View, Int) -> Unit,
        private val onPartialViewBind: ((T, VB, View, Int) -> Unit)? = null
    ) : RecyclerView.ViewHolder(view) {
        fun bind(item: T, position: Int = bindingAdapterPosition) {
            val validPosition =
                if (position != RecyclerView.NO_POSITION) position else bindingAdapterPosition
            onItemBind(item, binding, itemView, validPosition)
        }

        fun partialBind(item: T, position: Int = bindingAdapterPosition) {
            val validPosition =
                if (position != RecyclerView.NO_POSITION) position else bindingAdapterPosition
            onPartialViewBind?.invoke(item, binding, itemView, validPosition)
        }
    }

    class BaseItemCallback<T : Any>(
        private val areItemsTheSameImpl: (T, T) -> Boolean
    ) : DiffUtil.ItemCallback<T>() {
        override fun areItemsTheSame(oldItem: T, newItem: T): Boolean {
            return areItemsTheSameImpl(oldItem, newItem)
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: T, newItem: T): Boolean {
            return oldItem == newItem
        }
    }
}