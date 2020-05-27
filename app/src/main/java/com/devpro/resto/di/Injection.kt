package com.devpro.resto.di

import android.app.Application
import com.devpro.resto.data.source.AppRepository
import com.devpro.resto.data.source.remote.RemoteRepository
import com.devpro.resto.data.source.remote.RetrofitClient

class Injection {
    companion object {
        fun provideRepository(application: Application?): AppRepository? {
            val remoteRepository = RemoteRepository.getInstance(RetrofitClient())
            return remoteRepository.let {
                AppRepository.getInstance(remoteRepository)
            }
        }
    }
}