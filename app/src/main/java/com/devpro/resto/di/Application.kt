package com.devpro.resto.di

import android.app.Application
import com.devpro.resto.di.AppModule.repositoryModule
import com.devpro.resto.di.AppModule.view_models
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class Application : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(applicationContext)
            modules(listOf(view_models, repositoryModule))
        }
    }

}