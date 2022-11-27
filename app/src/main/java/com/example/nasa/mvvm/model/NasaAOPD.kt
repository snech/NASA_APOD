package com.example.nasa.mvvm.model

data class NasaAOPD(
    val imageTitle: String,
    val imageUrl: String,
    val imageExplanation: String,
    val imageDate: String,
    val videoLinkUrl: String,
    val isVideo: Boolean = false,
)