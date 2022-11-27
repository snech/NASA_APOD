package com.example.nasa.base.results


sealed class DataSourceResult<out T> {
    class Success<T>(val response: T) : DataSourceResult<T>()
    object Complete : DataSourceResult<Nothing>()
    sealed class Fail : DataSourceResult<Nothing>() {
        object NoConnection : Fail()
        class Error(val message: String) : Fail()
    }
}