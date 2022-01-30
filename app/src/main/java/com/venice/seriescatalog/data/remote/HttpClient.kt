package com.venice.seriescatalog.data.remote

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/*
 * Created by Andre Arruda on 1/27/22.
 */
class HttpClient {

    val BASE_URL =  "https://api.tvmaze.com/"

    fun <T> create(restApiClass: Class<T>): T {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(restApiClass)
    }

}