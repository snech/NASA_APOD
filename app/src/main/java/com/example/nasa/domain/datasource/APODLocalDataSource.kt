package com.example.nasa.domain.datasource

import com.example.nasa.base.results.DataSourceResult
import com.example.nasa.databse.APODEntity
import com.example.nasa.databse.NasaDataBase
import com.example.nasa.mvvm.model.NasaAOPD
import javax.inject.Inject

class APODLocalDataSource @Inject constructor(private val dataBase: NasaDataBase) {

    suspend fun getAPOD(): DataSourceResult<APODEntity> =
        DataSourceResult.Success(dataBase.nasaDao().getLatestAPOD())

    suspend fun insertData(data: NasaAOPD) = dataBase.nasaDao().insertAPOD(
        APODEntity(
            imageDate = data.imageDate,
            imageTitle = data.imageTitle,
            imageExplanation = data.imageExplanation,
            imageUrl = data.imageUrl,
            mediaType = if (data.isVideo) "video" else "image",
            videoLinkUrl = data.videoLinkUrl
        )
    )

    suspend fun clear() {
        dataBase.nasaDao().deleteAllAOPD()
    }
}