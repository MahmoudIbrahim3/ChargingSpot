package com.chargingspots.core.entities

data class SpotEntity (
	val id : String? = null,
	val name : String? = null,
	val thumbnail : String? = null,
	val address : String? = null,
	val lat : String? = null,
	val lng : String? = null,
	var distance: Double = 0.0,
	val status : Boolean? = null,
)
