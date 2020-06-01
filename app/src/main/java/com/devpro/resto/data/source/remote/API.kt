package com.devpro.resto.data.source.remote

import com.devpro.resto.data.source.remote.response.ResponseJSON
import com.devpro.resto.data.source.remote.response.ValuesItems
import retrofit2.http.Body
import retrofit2.http.POST

interface API {
    @POST("user/login.php")
    suspend fun loginUsers(
        @Body data: ValuesItems
    ): ResponseJSON

    @POST("kategori/viewAllKategori.php")
    suspend fun getCategory(
        @Body data: ValuesItems
    ): ResponseJSON

    @POST("menu/viewMenusByKat.php")
    suspend fun getMenuByCategory(
        @Body data: ValuesItems
    ): ResponseJSON

    @POST("meja/viewAllMeja.php")
    suspend fun getEntireTables(
        @Body data: ValuesItems
    ): ResponseJSON

    @POST("meja/updateMeja.php")
    suspend fun getUpdateTables(
        @Body data: ValuesItems
    ): ResponseJSON

    @POST("cart/insertCart.php")
    suspend fun insertCart(
        @Body data: ValuesItems
    ): ResponseJSON

    @POST("cart/viewCartByOrder.php")
    suspend fun getViewCartByOredr(
        @Body data: ValuesItems
    ): ResponseJSON

    @POST("order/insertOrder.php")
    suspend fun insertOrder(
        @Body data: ValuesItems
    ): ResponseJSON
}