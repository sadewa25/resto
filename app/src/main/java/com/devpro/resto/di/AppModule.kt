package com.devpro.resto.di

import com.devpro.resto.data.source.AppRepository
import com.devpro.resto.data.source.remote.RemoteRepository
import com.devpro.resto.data.source.remote.RetrofitClient
import com.devpro.resto.ui.cart.CartViewModel
import com.devpro.resto.ui.dashboard_waiters.DashboardWaitersViewModel
import com.devpro.resto.ui.login.LoginViewModel
import com.devpro.resto.ui.menus.MenusViewModel
import com.devpro.resto.ui.tables.TablesViewModel
import org.koin.dsl.module

object AppModule {
    val view_models = module {
        single { LoginViewModel(get()) }
        single { DashboardWaitersViewModel(get()) }
        single { MenusViewModel(get()) }
        single { TablesViewModel(get()) }
        single { CartViewModel(get()) }
    }

    val repositoryModule = module {
        fun provideRepository(): AppRepository? {
            val remoteRepository =
                RemoteRepository.getInstance(RetrofitClient())
            return remoteRepository.let { localrepo ->
                AppRepository.getInstance(remoteRepository)
            }
        }
        single { provideRepository() }
    }
}