package com.arifian.training.wisatasemarangkotlin.drawroutemap.models

import com.google.gson.annotations.SerializedName

data class Response(

	@field:SerializedName("routes")
	val routes: List<RoutesItem?>? = null,

	@field:SerializedName("geocoded_waypoints")
	val geocodedWaypoints: List<GeocodedWaypointsItem?>? = null,

	@field:SerializedName("status")
	val status: String? = null
)