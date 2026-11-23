package com.example.capstonemovie.utils.view

import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions

fun ImageView?.load(url: String?, @DrawableRes placeholder: Int? = null) {
    if (this == null) return
    val drawablePlaceholder = placeholder?.let { ContextCompat.getDrawable(this.context, it) }
    if (url.isNullOrBlank()) {
        setImageDrawable(drawablePlaceholder)
    } else {
        loadFromUrl(url = url, placeholder = drawablePlaceholder)
    }
}

fun ImageView?.loadFromUrl(
    url: String?,
    placeholder: Drawable? = null,
    diskCacheStrategy: DiskCacheStrategy = DiskCacheStrategy.AUTOMATIC,
    skipMemory: Boolean = false,
) {
    if (this == null) return
    if (url.isNullOrBlank()) {
        setImageDrawable(placeholder)
        return
    }

    glideLoadImage(
        this,
        url,
        placeholder = placeholder,
        error = placeholder,
        diskCacheStrategy = diskCacheStrategy,
        skipMemory = skipMemory
    )
}

fun glideLoadImage(
    view: ImageView,
    from: Any,
    requestOption: RequestOptions = RequestOptions(),
    placeholder: Drawable? = null,
    error: Drawable? = null,
    diskCacheStrategy: DiskCacheStrategy = DiskCacheStrategy.AUTOMATIC,
    skipMemory: Boolean = false
) {
    Glide.with(view)
        .load(from)
        .placeholder(placeholder)
        .error(error)
        .diskCacheStrategy(diskCacheStrategy)
        .skipMemoryCache(skipMemory)
        .apply(requestOption)
        .into(view)
}