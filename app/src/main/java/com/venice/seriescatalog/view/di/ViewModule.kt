package com.venice.seriescatalog.view.di

import com.venice.seriescatalog.view.fragment.SearchFragment
import com.venice.seriescatalog.view.fragment.SeriesFragment
import com.venice.seriescatalog.view.fragment.command.PeopleFragment
import com.venice.seriescatalog.view.viewmodel.EpisodeViewModel
import com.venice.seriescatalog.view.viewmodel.HomeViewModel
import com.venice.seriescatalog.view.viewmodel.ShowViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module

/*
 * Created by Andre Arruda on 1/27/22.
 */
object ViewModule {

    private val modules = module {
        viewModel { HomeViewModel(get(), get()) }
        viewModel { ShowViewModel(get(), get()) }
        viewModel { EpisodeViewModel(get()) }
        factory { SeriesFragment() }
        factory { SearchFragment() }
        factory { PeopleFragment() }
    }

    // function to load module
    fun loadModules() {
        loadKoinModules(modules)
    }
}