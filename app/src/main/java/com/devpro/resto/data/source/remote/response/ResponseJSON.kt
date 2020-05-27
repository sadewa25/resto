package com.devpro.resto.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class ResponseJSON(

	@field:SerializedName("values")
	val values: List<ValuesItems?>? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("error")
	val error: Boolean? = null
)