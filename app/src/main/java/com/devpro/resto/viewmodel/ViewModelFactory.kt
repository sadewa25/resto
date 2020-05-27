package com.devpro.resto.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.devpro.resto.data.source.AppRepository
import com.devpro.resto.di.Injection
import com.devpro.resto.ui.dashboard_waiters.DashboardWaitersViewModel
import com.devpro.resto.ui.login.LoginViewModel
import com.devpro.resto.ui.menus.MenusViewModel

class ViewModelFactory() : ViewModelProvider.NewInstanceFactory() {

    @Volatile
    private var INSTANCE: ViewModelFactory? = null

    private var appRepository: AppRepository? = null
    private var application: Application? = null

    constructor(appRepository: AppRepository?, application: Application?) : this() {
        this.appRepository = appRepository
        this.application = application
    }

    fun getInstance(application: Application?): ViewModelFactory? {
        if (INSTANCE == null) {
            synchronized(ViewModelFactory::class.java) {
                INSTANCE = ViewModelFactory(Injection.provideRepository(application), application)
            }
        }
        return INSTANCE
    }

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            return LoginViewModel(appRepository) as T
        } else if (modelClass.isAssignableFrom(DashboardWaitersViewModel::class.java)) {
            return DashboardWaitersViewModel(appRepository) as T
        } else if (modelClass.isAssignableFrom(MenusViewModel::class.java)) {
            return MenusViewModel(appRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }
}