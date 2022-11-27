package com.example.nasa.domain.usecase

import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.nasa.base.results.NasaResult
import com.example.nasa.databse.FavDataEntity
import com.example.nasa.databse.FavDataPagingSource
import com.example.nasa.databse.NasaDao
import com.example.nasa.mvvm.model.NasaAOPD
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class InsertFavUseCase @Inject constructor(private val nasaDao: NasaDao) {

    suspend fun insert(favData: NasaAOPD): NasaResult<Boolean> {
        return try {
            nasaDao.insertFav(
                FavDataEntity(
                    imageDate = favData.imageDate,
                    imageTitle = favData.imageTitle,
                    imageExplanation = favData.imageExplanation,
                    imageUrl = favData.imageUrl,
                    mediaType = if (favData.isVideo) "video" else "image",
                    videoLinkUrl = favData.videoLinkUrl
                )
            )
            NasaResult.Success(
                true
            )
        } catch (exception: Exception) {
            NasaResult.Fail.Error(exception.message ?: "Error")
        }
    }

    suspend fun getFavList(): Flow<PagingData<FavDataEntity>> = Pager(
        PagingConfig(
            pageSize = 3,
            enablePlaceholders = false,
            initialLoadSize = 3
        )
    ) { FavDataPagingSource(nasaDao) }
        .flow
}