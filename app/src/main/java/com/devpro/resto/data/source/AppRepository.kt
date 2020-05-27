package com.devpro.resto.data.source

import com.devpro.resto.data.source.remote.RemoteRepository
import com.devpro.resto.data.source.remote.response.ResponseJSON
import com.devpro.resto.data.source.remote.response.ValuesItems
import com.devpro.resto.utils.ContextProvider

class AppRepository(
    private val remoteRepository: RemoteRepository,
    private val coroutineContext: ContextProvider
) : AppDataSource {

    companion object {
        @Volatile
        private var INSTANCE: AppRepository? = null

        fun getInstance(
            remoteRepository: RemoteRepository
        ): AppRepository = INSTANCE
            ?: synchronized(AppRepository::class.java) {
                AppRepository(
                    remoteRepository,
                    ContextProvider.getInstance()
                ).also {
                    INSTANCE = it
                }
            }
    }

    override suspend fun loginUsers(datas: ValuesItems): ResponseJSON {
        return remoteRepository.loginUsers(datas)
    }

    override suspend fun getCategory(data: ValuesItems): ResponseJSON {
        return remoteRepository.getCategory(data)
    }

    override suspend fun getMenuByCategory(data: ValuesItems): ResponseJSON {
        return remoteRepository.getMenuByCategory(data)
    }

}