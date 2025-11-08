package com.example.rest.businessLayer.adapter.road.drivingFlow

data class DrivingFlowModel(
	val roadId: String,
	val idDirection: Int,
	val numOfLanes: Int,
	val roadCoordinates: ArrayList<Pair<Int, Int>>,
)