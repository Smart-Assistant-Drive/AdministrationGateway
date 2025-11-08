package com.example.rest.businessLayer.adapter.road.drivingFlow

data class DrivingFlowResponseModel(
	val roadId: String,
	val flowId: String,
	val idDirection: Int,
	val numOfLanes: Int,
	val roadCoordinates: ArrayList<Pair<Int, Int>>,
)
