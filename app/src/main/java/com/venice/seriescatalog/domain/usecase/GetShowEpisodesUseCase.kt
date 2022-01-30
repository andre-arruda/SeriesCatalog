package com.venice.seriescatalog.domain.usecase

import com.venice.seriescatalog.data.repository.SeriesCatalogRepository
import com.venice.seriescatalog.model.Episode
/*
 * Created by Andre Arruda on 1/28/22.
 */
class GetShowEpisodesUseCase(private val repository: SeriesCatalogRepository) {

    suspend operator fun invoke(showId: Int): List<Episode> {
        return repository.getShowEpisodeList(showId)
    }
}