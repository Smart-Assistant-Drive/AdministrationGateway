package com.example.rest.businessLayer.adapter.road.drivingFlow

import com.example.rest.domainLayer.Coordinate

data class DrivingFlowModel(
    val roadId: String,
    val idDirection: Int,
    val numOfLanes: Int,
    val roadCoordinates: ArrayList<Coordinate>,
)