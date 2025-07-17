package com.example.rest.businessLayer.adapter.road.junction

data class JunctionModel(
	val outgoingRoads: ArrayList<Pair<String, Int>>,
	val junctionType: Int,
	val position: Pair<Float, Float>,
)
