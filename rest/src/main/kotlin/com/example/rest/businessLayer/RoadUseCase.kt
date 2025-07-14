package com.example.rest.businessLayer

import com.example.rest.businessLayer.adapter.road.DrivingFlowModel
import com.example.rest.businessLayer.adapter.road.DrivingFlowResponseModel
import com.example.rest.businessLayer.adapter.road.DrivingFlowUpdateModel
import com.example.rest.businessLayer.adapter.road.RoadModel
import com.example.rest.businessLayer.adapter.road.RoadResponseModel
import com.example.rest.businessLayer.boundaries.RoadDataSourceGateway
import com.example.rest.businessLayer.boundaries.RoadInputBoundary

class RoadUseCase(
	private val roadDataSourceGateway: RoadDataSourceGateway,
): RoadInputBoundary {
	override fun addRoad(roadModel: RoadModel): Result<RoadResponseModel> {
		return roadDataSourceGateway.addRoad(roadModel)
	}

	override fun getRoad(roadId: String): Result<RoadModel> {
		return roadDataSourceGateway.getRoad(roadId)
	}

	override fun updateRoad(roadId: String, roadModel: RoadModel): Result<RoadResponseModel> {
		return roadDataSourceGateway.updateRoad(roadId, roadModel)
	}

	override fun addDirectionFlow(drivingFlowModel: DrivingFlowModel): Result<DrivingFlowResponseModel> {
		TODO("Not yet implemented")
	}

	override fun getAllDirectionFlows(roadId: String): Result<List<DrivingFlowResponseModel>> {
		TODO("Not yet implemented")
	}

	override fun changeDrivingFlow(drivingFlowUpdateModel: DrivingFlowUpdateModel): Result<DrivingFlowResponseModel> {
		TODO("Not yet implemented")
	}

}