package com.venice.seriescatalog.data.repository

/*
 * Created by Andre Arruda on 1/27/22.
 */
class SeriesCatalogRepository(
    private val api: SeriesCatalogAPI
) {

    suspend fun getAllShows(pageNum: Int) = api.getAllShows(pageNum)

    suspend fun getShowsByFilter(filter: String) = api.getShowsByFilter(filter)

    suspend fun getShowInformation(showId: Int) = api.getShowInformation(showId)

    suspend fun getShowEpisodeList(showId: Int) = api.getShowEpisodeList(showId)

    suspend fun getEpisodeInformation(episodeId: Int) = api.getEpisodeInformation(episodeId)

}