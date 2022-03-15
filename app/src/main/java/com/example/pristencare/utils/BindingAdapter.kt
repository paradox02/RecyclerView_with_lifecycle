package com.example.pristencare.utils

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.example.pristencare.R

fun ImageView.loadUrl(url: String?) {
    Glide.with(this.context).load(url).error(R.drawable.ic_launcher_background).into(this)
}