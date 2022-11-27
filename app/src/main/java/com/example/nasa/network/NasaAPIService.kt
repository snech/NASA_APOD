package com.example.nasa.network

import com.example.nasa.domain.model.APODResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import java.net.ConnectException
import java.net.NoRouteToHostException
import java.net.SocketException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

interface NasaAPIService {

    companion object {
        const val API_KEY = "Gz8l1raTunaJaGbW5pizGeljHXMNlsEc4E5JBfw6"
    }

    @GET("/planetary/apod")
    suspend fun getAPOD(
        @Query("api_key") apiKey: String = API_KEY,
        @Query("date") searchDate: String,
        @Query("thumbs") thumbs: Boolean = true
    ): Response<APODResponse>
}

fun Throwable.isConnectionException(): Boolean {
    return this is UnknownHostException
            || this is NoRouteToHostException
            || this is ConnectException
            || this is SocketTimeoutException
            || this is SocketException
}