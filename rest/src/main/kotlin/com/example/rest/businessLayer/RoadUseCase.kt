package com.example.rest.businessLayer

import com.example.rest.businessLayer.adapter.road.drivingFlow.DrivingFlowModel
import com.example.rest.businessLayer.adapter.road.drivingFlow.DrivingFlowResponseModel
import com.example.rest.businessLayer.adapter.road.drivingFlow.DrivingFlowUpdateModel
import com.example.rest.businessLayer.adapter.road.RoadModel
import com.example.rest.businessLayer.adapter.road.RoadResponseModel
import com.example.rest.businessLayer.adapter.road.junction.JunctionModel
import com.example.rest.businessLayer.adapter.road.junction.JunctionResponseModel
import com.example.rest.businessLayer.adapter.road.junction.JunctionUpdateModel
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
		return roadDataSourceGateway.addDirectionFlow(drivingFlowModel)
	}

	override fun getAllDirectionFlows(roadId: String): Result<List<DrivingFlowResponseModel>> {
		return roadDataSourceGateway.getAllDirectionFlows(roadId)
	}

	override fun changeDrivingFlow(drivingFlowUpdateModel: DrivingFlowUpdateModel): Result<DrivingFlowResponseModel> {
		return roadDataSourceGateway.changeDrivingFlow(drivingFlowUpdateModel)
	}

	override fun addJunction(junctionModel: JunctionModel): Result<JunctionResponseModel> {
		return roadDataSourceGateway.addJunction(junctionModel)
	}

	override fun getJunctionsByRoad(roadId: String): List<JunctionResponseModel> {
		return roadDataSourceGateway.getJunctionsByRoad(roadId).getOrElse { ArrayList() }
	}

	override fun getJunction(id: String): Result<JunctionResponseModel> {
		return roadDataSourceGateway.getJunction(id)
	}

	override fun updateJunction(
		junctionId: String,
		junctionUpdateModel: JunctionUpdateModel
	): Result<JunctionResponseModel> {
		return roadDataSourceGateway.updateJunction(junctionId, junctionUpdateModel)
	}

}