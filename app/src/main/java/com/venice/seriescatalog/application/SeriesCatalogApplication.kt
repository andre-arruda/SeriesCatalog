package com.venice.seriescatalog.application

import android.app.Application
import com.venice.seriescatalog.data.di.DataModule
import com.venice.seriescatalog.domain.di.DomainModule
import com.venice.seriescatalog.view.di.ViewModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

/*
 * Created by Andre Arruda on 1/27/22.
 */
class SeriesCatalogApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        //start koin over here
        startKoin {
            androidContext(this@SeriesCatalogApplication)
            androidLogger()
            DataModule.loadModules()
            DomainModule.loadModules()
            ViewModule.loadModules()
        }
    }

}