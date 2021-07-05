package com.gery.apprenticegallery.presentation.common

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade

fun ImageView.load(uri: String?) {
    Glide.with(context)
        .load(uri)
        .transition(withCrossFade())
        .fitCenter()
        .into(this)
}