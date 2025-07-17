package com.example.rest.businessLayer.adapter.road.junction

data class JunctionUpdateModel(
	val newOutgoingRoads: ArrayList<Pair<String, Int>>,
	val newJunctionType: Int,
)
