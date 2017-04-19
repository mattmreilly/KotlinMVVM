package com.wengelef.kotlinmvvmtest.util

import android.databinding.BindingAdapter
import android.graphics.Typeface
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import com.bumptech.glide.Glide

@BindingAdapter("showProgress") fun showProgress(progressBar: ProgressBar, loading: Boolean) {
    progressBar.alpha = if (loading) 0f else 1f
    progressBar.animate()
            .alpha(if (loading) 1f else 0f)
            .setDuration(500L)
            .start()
}

@BindingAdapter("loadImage") fun loadImage(imageView: ImageView, url: String?) {
    Glide.with(imageView.context).load(url).into(imageView)
}

@BindingAdapter("setFont") fun setFont(textView: TextView, fontUrl: String) {
    textView.typeface = Typeface.createFromAsset(textView.context.assets, "fonts/$fontUrl")
}