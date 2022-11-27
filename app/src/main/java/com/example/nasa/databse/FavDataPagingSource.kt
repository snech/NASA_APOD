package com.example.nasa.databse

import androidx.paging.PagingSource
import androidx.paging.PagingState

class FavDataPagingSource(private val nasaDao: NasaDao) : PagingSource<Int, FavDataEntity>() {

    private val pageSize: Int = 3
    private val startingPageIndex = 0

    override fun getRefreshKey(state: PagingState<Int, FavDataEntity>): Int? = null

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, FavDataEntity> {
        val page = params.key ?: startingPageIndex
        return try {
            val response = nasaDao.getFavList(params.loadSize, page * params.loadSize)
            LoadResult.Page(
                data = response,
                prevKey = null,
                nextKey = if (response.isEmpty() || response.size < pageSize) {
                    null
                } else {
                    page + 1
                }
            )
        } catch (exception: Exception) {
            return LoadResult.Error(exception)
        }
    }
}