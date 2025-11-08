package com.example.rest.businessLayer.adapter.road.junction

data class JunctionResponseModel(
	val junctionId: String,
	val outgoingRoads: ArrayList<Pair<String, Int>>,
	val junctionType: Int,
	val position: Pair<Float, Float>,
)