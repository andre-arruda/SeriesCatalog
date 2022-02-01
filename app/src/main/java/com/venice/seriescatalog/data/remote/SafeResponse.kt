package com.venice.seriescatalog.data.remote

import android.util.Log
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

/*
 * Created by Andre Arruda on 1/27/22.
 */

sealed class SafeResponse<out T> {
    data class Success<out T>(val value: T) : SafeResponse<T>()
    data class GenericError(val code: Int? = null, val error: Response<*>? = null, val errorMessage: String) : SafeResponse<Nothing>()
    data class NetworkError(val errorMessage: String) : SafeResponse<Nothing>()
}

suspend fun <T> safeRequest(request: suspend () -> T): SafeResponse<T> {
    return try {
        SafeResponse.Success(request())
    } catch (throwable: Throwable) {
        Log.e("ERROR", throwable.message!!)
        return when (throwable) {
            is IOException -> {
                val errorMessage = "Connectivity problem. Please, check your connection!"
                SafeResponse.NetworkError(errorMessage)
            }
            is HttpException -> {
                val errorMessage = "Ops! Something went wrong"
                SafeResponse.GenericError(throwable.response()?.code(), throwable.response(), errorMessage)
            }
            else -> {
                val errorMessage = "Ops! Something went wrong"
                SafeResponse.GenericError(null, null, errorMessage)
            }
        }
    }
}