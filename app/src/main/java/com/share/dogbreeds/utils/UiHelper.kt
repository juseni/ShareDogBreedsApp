package com.share.dogbreeds.utils

import android.content.Context
import android.widget.ImageView
import com.share.dogbreeds.R

/**
 * @author Juan Sebastian Niño
 */
fun loadImageFromUrl(
    context: Context,
    imageView: ImageView,
    url: String?
) {
   if (url == null) {
       imageView.setImageResource(R.color.teal_200)
   } else {
        GlideApp.with(context)
            .load(url)
            .placeholder(R.color.teal_200)
            .error(R.color.teal_700)
            .into(imageView)
   }
}