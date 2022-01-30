package com.venice.seriescatalog.data.di

import com.venice.seriescatalog.data.remote.HttpClient
import com.venice.seriescatalog.data.repository.SeriesCatalogAPI
import com.venice.seriescatalog.data.repository.SeriesCatalogRepository
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module

/*
 * Created by Andre Arruda on 1/28/22.
 */
object DataModule {

    private val modules = module {
        factory { HttpClient() }
        factory { get<HttpClient>().create(SeriesCatalogAPI::class.java) }
        factory { SeriesCatalogRepository(get()) }
    }

    // function to load module
    fun loadModules() {
        loadKoinModules(modules)
    }
}