package com.venice.seriescatalog.domain.di

import com.venice.seriescatalog.domain.usecase.*
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module

/*
 * Created by Andre Arruda on 1/28/22.
 */
object DomainModule {

    private val modules = module {
        factory { GetAllShowsUseCase(get()) }
        factory { GetShowsByFilterUseCase(get()) }
        factory { GetShowUseCase(get()) }
        factory { GetShowEpisodesUseCase(get()) }
        factory { GetEpisodeUseCase(get()) }
    }

    // function to load module
    fun loadModules() {
        loadKoinModules(modules)
    }
}