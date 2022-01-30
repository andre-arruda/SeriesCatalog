package com.venice.seriescatalog.data.repository

import com.venice.seriescatalog.model.Episode
import com.venice.seriescatalog.model.Series
import com.venice.seriescatalog.model.Show
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/*
 * Created by Andre Arruda on 1/27/22.
 */
interface SeriesCatalogAPI {

    @GET("/shows")
    suspend fun getAllShows(@Query("page") pageNum: Int) : List<Show>

    @GET("/search/shows")
    suspend fun getShowsByFilter(@Query("q") filter: String) : List<Series>

    @GET("/shows/{id}")
    suspend fun getShowInformation(@Path("id") showId: Int) : Show

    @GET("/shows/{id}/episodes")
    suspend fun getShowEpisodeList(@Path("id") showId: Int) : List<Episode>

    @GET("/episodes/{id}")
    suspend fun  getEpisodeInformation(@Path("id") episodeId: Int) : Episode
}