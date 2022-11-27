package com.example.nasa.mvvm

import android.content.Context
import android.content.Intent
import android.net.Uri

object NasaUtil {

    fun openBrowserToPlayVideo(url: String, mContext: Context) {
        val browserIntent =
            Intent(Intent.ACTION_VIEW, Uri.parse(url))
        mContext.startActivity(browserIntent)
    }

    fun isVideoTypeMedia(mediaType: String): Boolean {
        return mediaType == "video"
    }
}