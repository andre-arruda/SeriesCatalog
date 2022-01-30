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
                val errorMessage = "Ops. Tivemos um problema aqui. Por favor, tente novamente."
                SafeResponse.NetworkError(errorMessage)
            }
            is HttpException -> {
                val errorMessage = "Ops. Tivemos um problema aqui. Por favor, tente novamente."
                SafeResponse.GenericError(throwable.code(), throwable.response(), errorMessage)
            }
            else -> {
                val errorMessage = "Ops. Tivemos um problema aqui. Por favor, tente novamente."
                SafeResponse.GenericError(null, null, errorMessage)
            }
        }
    }
}