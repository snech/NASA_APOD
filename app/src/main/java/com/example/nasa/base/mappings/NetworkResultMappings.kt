package com.example.nasa.base.mappings

import com.example.nasa.base.results.DataSourceResult
import com.example.nasa.base.results.NasaResult

fun <T, R> DataSourceResult<T>.toNasaResult(mapper: (T) -> R): NasaResult<R> {
    return when (this) {
        is DataSourceResult.Success -> NasaResult.Success(mapper(this.response))
        is DataSourceResult.Complete -> NasaResult.Complete
        is DataSourceResult.Fail.Error -> NasaResult.Fail.Error(this.message)
        is DataSourceResult.Fail.NoConnection -> NasaResult.Fail.NetworkFailure(this)
    }
}


