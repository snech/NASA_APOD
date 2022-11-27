package com.example.nasa.domain.datasource

import com.example.nasa.base.results.DataSourceResult
import com.example.nasa.domain.model.APODResponse
import com.example.nasa.network.NasaAPIService
import com.example.nasa.network.isConnectionException
import org.json.JSONObject
import retrofit2.Response
import javax.inject.Inject

class NasaNetworkDataSource @Inject constructor(private val apiService: NasaAPIService) {

    suspend fun getAPOD(searchDate: String): DataSourceResult<APODResponse> {
        return apiCall {
            apiService.getAPOD(searchDate = searchDate)
        }
    }
}


suspend fun <T> apiCall(block: suspend () -> Response<T>): DataSourceResult<T> {
    return try {
        val response = block()
        if (response.isSuccessful) {
            response.body()?.let {
                DataSourceResult.Success(it)
            } ?: DataSourceResult.Complete
        } else {
            var errorMsg = "Something Went wrong!"
            response.errorBody()?.let {
                val jsonError = JSONObject(it.string())
                errorMsg = jsonError.getString("msg")
            }
            DataSourceResult.Fail.Error(errorMsg)
        }
    } catch (expected: Exception) {
        if (expected.isConnectionException()) {
            DataSourceResult.Fail.NoConnection
        } else {
            DataSourceResult.Fail.Error(expected.message ?: "Something Went wrong!")
        }
    }
}

