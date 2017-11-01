package com.arifian.training.wisatasemarangkotlin.drawroutemap.models

import com.google.gson.annotations.SerializedName

data class EndLocation(

	@field:SerializedName("lng")
	val lng: Double? = null,

	@field:SerializedName("lat")
	val lat: Double? = null
)