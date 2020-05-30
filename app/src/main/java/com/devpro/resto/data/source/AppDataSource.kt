package com.devpro.resto.data.source

import com.devpro.resto.data.source.remote.response.ResponseJSON
import com.devpro.resto.data.source.remote.response.ValuesItems

interface AppDataSource {
    suspend fun loginUsers(datas:ValuesItems): ResponseJSON
    suspend fun getCategory(data:ValuesItems): ResponseJSON
    suspend fun getMenuByCategory(data:ValuesItems): ResponseJSON
    suspend fun getEntireTables(data:ValuesItems): ResponseJSON
    suspend fun getUpdateTables(data:ValuesItems): ResponseJSON
    suspend fun insertCart(data:ValuesItems): ResponseJSON
    suspend fun getViewCartByOrder(data:ValuesItems): ResponseJSON
}