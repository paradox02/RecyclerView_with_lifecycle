package com.example.pristencare.utils

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.example.pristencare.ItemViewModel
import com.example.pristencare.R
import com.example.pristencare.activity.NextActivity
import com.example.pristencare.model.Photo

@BindingAdapter("app:loadUrl")
fun ImageView.loadUrl(photo: Photo) {

    val url =
        "https://live.staticflickr.com/" + photo.server + "/" + photo.id + "_" + photo.secret + "_" + "w.jpg"
    Glide.with(this.context).load(url).into(this)

    setOnClickListener {
        it.context.launchActivity<NextActivity> {
            putExtra("url", url)
        }
    }

}


fun ImageView.loadUrl(url: String?) {
    Glide.with(this.context).load(url).error(R.drawable.ic_launcher_background).into(this)
}

@BindingAdapter("app:text")
fun TextView.setText(itemViewModel: ItemViewModel) {



}