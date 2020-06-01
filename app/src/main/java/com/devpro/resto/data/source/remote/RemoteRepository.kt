package com.devpro.resto.data.source.remote

import com.devpro.resto.BuildConfig
import com.devpro.resto.data.source.remote.response.ValuesItems

class RemoteRepository(private val apiService: RetrofitClient) {
    companion object {
        private var INSTANCE: RemoteRepository? = null

        fun getInstance(apiService: RetrofitClient): RemoteRepository {
            if (INSTANCE == null) {
                INSTANCE = RemoteRepository(apiService)
            }
            return INSTANCE as RemoteRepository
        }
    }

    suspend fun loginUsers(data:ValuesItems) =
        apiService.response().loginUsers(data)

    suspend fun getCategory(data:ValuesItems) =
        apiService.response().getCategory(data)

    suspend fun getMenuByCategory(data:ValuesItems) =
        apiService.response().getMenuByCategory(data)

    suspend fun getEntireTables(data:ValuesItems) =
        apiService.response().getEntireTables(data)

    suspend fun getUpdateTables(data:ValuesItems) =
        apiService.response().getUpdateTables(data)

    suspend fun insertCart(data:ValuesItems) =
        apiService.response().insertCart(data)

    suspend fun getViewCartByOrder(data:ValuesItems) =
        apiService.response().getViewCartByOredr(data)

    suspend fun insertOrder(data:ValuesItems) =
        apiService.response().insertOrder(data)
}