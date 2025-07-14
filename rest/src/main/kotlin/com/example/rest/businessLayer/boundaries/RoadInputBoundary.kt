package com.example.rest.businessLayer.boundaries

import com.example.rest.businessLayer.adapter.road.DrivingFlowModel
import com.example.rest.businessLayer.adapter.road.DrivingFlowResponseModel
import com.example.rest.businessLayer.adapter.road.DrivingFlowUpdateModel
import com.example.rest.businessLayer.adapter.road.RoadModel
import com.example.rest.businessLayer.adapter.road.RoadResponseModel

interface RoadInputBoundary {
	fun addRoad(roadModel: RoadModel): Result<RoadResponseModel>

	fun getRoad(roadId: String): Result<RoadModel>

	fun updateRoad(roadId: String, roadModel: RoadModel): Result<RoadResponseModel>

	fun addDirectionFlow(drivingFlowModel: DrivingFlowModel): Result<DrivingFlowResponseModel>

	fun getAllDirectionFlows(roadId: String): Result<List<DrivingFlowResponseModel>>

	fun changeDrivingFlow(drivingFlowUpdateModel: DrivingFlowUpdateModel): Result<DrivingFlowResponseModel>
}
