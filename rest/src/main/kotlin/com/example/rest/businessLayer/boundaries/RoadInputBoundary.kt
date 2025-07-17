package com.example.rest.businessLayer.boundaries

import com.example.rest.businessLayer.adapter.road.drivingFlow.DrivingFlowModel
import com.example.rest.businessLayer.adapter.road.drivingFlow.DrivingFlowResponseModel
import com.example.rest.businessLayer.adapter.road.drivingFlow.DrivingFlowUpdateModel
import com.example.rest.businessLayer.adapter.road.RoadModel
import com.example.rest.businessLayer.adapter.road.RoadResponseModel
import com.example.rest.businessLayer.adapter.road.junction.JunctionModel
import com.example.rest.businessLayer.adapter.road.junction.JunctionResponseModel
import com.example.rest.businessLayer.adapter.road.junction.JunctionUpdateModel

interface RoadInputBoundary {
	fun addRoad(roadModel: RoadModel): Result<RoadResponseModel>

	fun getRoad(roadId: String): Result<RoadModel>

	fun updateRoad(roadId: String, roadModel: RoadModel): Result<RoadResponseModel>

	fun addDirectionFlow(drivingFlowModel: DrivingFlowModel): Result<DrivingFlowResponseModel>

	fun getAllDirectionFlows(roadId: String): Result<List<DrivingFlowResponseModel>>

	fun changeDrivingFlow(drivingFlowUpdateModel: DrivingFlowUpdateModel): Result<DrivingFlowResponseModel>

	fun addJunction(junctionModel: JunctionModel): Result<JunctionResponseModel>

	fun getJunctionsByRoad(roadId: String): List<JunctionResponseModel>

	fun getJunction(id: String): Result<JunctionResponseModel>

	fun updateJunction(junctionId: String, junctionUpdateModel: JunctionUpdateModel): Result<JunctionResponseModel>
}
