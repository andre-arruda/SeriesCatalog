package com.venice.seriescatalog.domain.usecase

import com.venice.seriescatalog.data.repository.SeriesCatalogRepository
import com.venice.seriescatalog.model.Series
import com.venice.seriescatalog.model.Show

/*
 * Created by Andre Arruda on 1/28/22.
 */

class GetShowsByFilterUseCase(private val repository: SeriesCatalogRepository) {

    suspend operator fun invoke(filter: String): List<Series> {
        return repository.getShowsByFilter(filter)
    }

}