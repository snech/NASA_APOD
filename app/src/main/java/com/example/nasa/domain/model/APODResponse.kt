package com.example.nasa.domain.model

import com.google.gson.annotations.SerializedName

data class APODResponse(
    @SerializedName("copyright") val copyright: String,
    @SerializedName("title") val title: String,
    @SerializedName("url") val url: String,
    @SerializedName("explanation") val explanation: String,
    @SerializedName("date") val date: String,
    @SerializedName("media_type") val mediaType: String,
    @SerializedName("hdurl") val hdUrl: String,
    @SerializedName("thumbnail_url") val videoThumbNailUrl: String
)
