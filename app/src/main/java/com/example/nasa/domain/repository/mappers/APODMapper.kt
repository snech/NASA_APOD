package com.example.nasa.domain.repository.mappers

import com.example.nasa.databse.APODEntity
import com.example.nasa.domain.model.APODResponse
import com.example.nasa.mvvm.NasaUtil
import com.example.nasa.mvvm.model.NasaAOPD
import javax.inject.Inject

class APODMapper @Inject constructor() {
    fun map(response: APODResponse): NasaAOPD = NasaAOPD(imageTitle = response.title,
        imageUrl = if (NasaUtil.isVideoTypeMedia(response.mediaType)) response.videoThumbNailUrl else {
            response.url.ifEmpty { response.hdUrl }
        },
        imageExplanation = response.explanation,
        imageDate = response.date,
        videoLinkUrl = if (!response.url.startsWith("http://") && !response.url.startsWith("https://")) "http://" + response.url else response.url,
        isVideo = NasaUtil.isVideoTypeMedia(response.mediaType))

    fun map(response: APODEntity): NasaAOPD = NasaAOPD(
        imageTitle = response.imageTitle,
        imageUrl = response.imageUrl,
        imageExplanation = response.imageExplanation,
        imageDate = response.imageDate,
        videoLinkUrl = response.videoLinkUrl,
        isVideo = NasaUtil.isVideoTypeMedia(response.mediaType)
    )
}