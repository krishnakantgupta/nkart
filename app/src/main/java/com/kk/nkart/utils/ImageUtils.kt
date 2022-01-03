package com.kk.nkart.utils

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.kk.nkart.R

object ImageUtils {

    fun loadImage(context:Context,imageView: ImageView, url:String){
        if(!url.lowercase().startsWith("http")){
            imageView.setImageResource(R.drawable.ic_image_error)
            return
        }
        Glide.with(context)
            .load(url) // image url
            .placeholder(R.drawable.image_placeholder) // any placeholder to load at start
            .error(R.drawable.ic_image_error)  // any image in case of error
            .into(imageView);  // imageview object
    }
}