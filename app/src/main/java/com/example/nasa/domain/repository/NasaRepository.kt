package com.example.nasa.domain.repository

import com.example.nasa.base.mappings.toNasaResult
import com.example.nasa.base.results.DataSourceResult
import com.example.nasa.base.results.NasaResult
import com.example.nasa.domain.datasource.APODLocalDataSource
import com.example.nasa.domain.datasource.NasaNetworkDataSource
import com.example.nasa.domain.repository.mappers.APODMapper
import com.example.nasa.mvvm.model.NasaAOPD
import javax.inject.Inject

class NasaRepository @Inject constructor(
    private val dataSource: NasaNetworkDataSource,
    private val localDataSource: APODLocalDataSource,
    private val mapper: APODMapper
) {

    suspend fun getAPOD(searchDate: String): NasaResult<NasaAOPD> {
        val result = dataSource.getAPOD(searchDate)
        if (result is DataSourceResult.Fail.NoConnection) {
            return localDataSource.getAPOD().toNasaResult { mapper.map(it) }
        }
        return result.toNasaResult { mapper.map(it) }.apply {
            if (this is NasaResult.Success) {
                localDataSource.clear()
                localDataSource.insertData(this.response)
            }
        }
    }
}