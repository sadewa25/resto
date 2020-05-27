package com.devpro.resto.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class ValuesItems(

	@field:SerializedName("name_users")
	val nameUsers: String? = null,

	@field:SerializedName("status_users")
	val statusUsers: String? = null,

	@field:SerializedName("id_users")
	val idUsers: String? = null,

	@field:SerializedName("username")
	val username: String? = null,

	@field:SerializedName("password")
	val password: String? = null,

	@field:SerializedName("password_users")
	val passwordUsers: String? = null,

	@field:SerializedName("apikey")
	val apikey: String? = null,

	@field:SerializedName("name_kategori_menu")
	val nameKategoriMenu: String? = null,

	@field:SerializedName("id_kategori_menu")
	val idKategoriMenu: String? = null,

	@field:SerializedName("id_kategori")
	val idKategori: String? = null,

	@field:SerializedName("id_menu")
	val idMenu: String? = null,

	@field:SerializedName("price_menu")
	val priceMenu: String? = null,

	@field:SerializedName("name_menu")
	val nameMenu: String? = null
)