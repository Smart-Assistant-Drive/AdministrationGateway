package com.example.rest.businessLayer.adapter.road.drivingFlow

data class DrivingFlowUpdateModel(
	val flowId: String,
	val idDirection: Int,
	val numOfLanes: Int,
	val roadCoordinates: ArrayList<Pair<Int, Int>>,
)
