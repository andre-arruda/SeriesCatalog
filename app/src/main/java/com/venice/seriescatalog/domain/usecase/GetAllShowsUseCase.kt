package com.venice.seriescatalog.domain.usecase

import com.venice.seriescatalog.data.repository.SeriesCatalogRepository
import com.venice.seriescatalog.model.Show

/*
 * Created by Andre Arruda on 1/27/22.
 */
class GetAllShowsUseCase(private val repository: SeriesCatalogRepository) {

    suspend operator fun invoke(pageNum: Int): List<Show> {
        return repository.getAllShows(pageNum)
    }

}