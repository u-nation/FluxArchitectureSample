package com.ogaclejapan.flux.bindings

import android.databinding.BindingAdapter
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class ImageBindingAdapters {
  companion object {

    @JvmStatic
    @BindingAdapter("image_crop_circle")
    fun loadImage(view: ImageView, url: String?) {
      Glide.with(view.context.applicationContext)
          .load(url)
          .apply(RequestOptions.circleCropTransform())
          .into(view)
    }
  }
}
