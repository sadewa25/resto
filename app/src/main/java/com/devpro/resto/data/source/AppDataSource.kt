package com.devpro.resto.data.source

import com.devpro.resto.data.source.remote.response.ResponseJSON
import com.devpro.resto.data.source.remote.response.ValuesItems

interface AppDataSource {
    suspend fun loginUsers(datas:ValuesItems): ResponseJSON
    suspend fun getCategory(data:ValuesItems): ResponseJSON
    suspend fun getMenuByCategory(data:ValuesItems): ResponseJSON
}