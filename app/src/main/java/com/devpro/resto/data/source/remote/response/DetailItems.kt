package com.devpro.resto.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class DetailItems(
    @field:SerializedName("name_users")
    val nameUsers: String? = null,

    @field:SerializedName("id_menu")
    val idMenu: String? = null,

    @field:SerializedName("total_price")
    val totalPrice: String? = null,

    @field:SerializedName("name_kategori_menu")
    val nameKategoriMenu: String? = null,

    @field:SerializedName("id_users")
    val idUsers: String? = null,

    @field:SerializedName("name_meja")
    val nameMeja: String? = null,

    @field:SerializedName("no_order")
    val noOrder: String? = null,

    @field:SerializedName("id_meja")
    val idMeja: String? = null,

    @field:SerializedName("date_cart")
    val dateCart: String? = null,

    @field:SerializedName("status_cart")
    val statusCart: String? = null,

    @field:SerializedName("qty")
    val qty: String? = null,

    @field:SerializedName("id_cart")
    val idCart: String? = null,

    @field:SerializedName("name_order")
    val nameOrder: String? = null,

    @field:SerializedName("name_menu")
    val nameMenu: String? = null
)