package com.example.nasa.base.results

sealed class NasaResult<out T> {
    class Success<T>(val response: T) : NasaResult<T>()
    object Complete : NasaResult<Nothing>()
    sealed class Fail : NasaResult<Nothing>() {
        data class NetworkFailure(val result: DataSourceResult.Fail) : Fail()
        data class Error(val errorMessage: String) : Fail()
    }
}