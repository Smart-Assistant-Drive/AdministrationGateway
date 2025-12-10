package com.example.rest.businessLayer.boundaries

import com.example.rest.businessLayer.adapter.road.NewTrafficDigitalTwinRequest
import com.example.rest.businessLayer.adapter.road.drivingFlow.DrivingFlowModel
import com.example.rest.businessLayer.adapter.road.drivingFlow.DrivingFlowResponseModel
import com.example.rest.businessLayer.adapter.road.drivingFlow.DrivingFlowUpdateModel
import com.example.rest.businessLayer.adapter.road.RoadModel
import com.example.rest.businessLayer.adapter.road.RoadResponseModel
import com.example.rest.businessLayer.adapter.road.TrafficDigitalTwinModel
import com.example.rest.businessLayer.adapter.road.TrafficDigitalTwinRequestModel
import com.example.rest.businessLayer.adapter.road.junction.JunctionModel
import com.example.rest.businessLayer.adapter.road.junction.JunctionResponseModel
import com.example.rest.businessLayer.adapter.road.junction.JunctionUpdateModel
import com.example.rest.businessLayer.adapter.semaphore.NewSemaphoreRequestModel
import com.example.rest.businessLayer.adapter.semaphore.SemaphoreResponseModel
import com.example.rest.businessLayer.adapter.semaphore.SemaphoresRequestModel

interface RoadInputBoundary {
	fun addRoad(roadModel: RoadModel): Result<RoadResponseModel>

	fun getRoad(roadId: String): Result<RoadResponseModel>

	fun updateRoad(roadId: String, roadModel: RoadModel): Result<RoadResponseModel>

	fun addDirectionFlow(drivingFlowModel: DrivingFlowModel): Result<DrivingFlowResponseModel>

	fun getAllDirectionFlows(roadId: String): Result<List<DrivingFlowResponseModel>>

	fun changeDrivingFlow(drivingFlowUpdateModel: DrivingFlowUpdateModel): Result<DrivingFlowResponseModel>

	fun addJunction(junctionModel: JunctionModel): Result<JunctionResponseModel>

	fun getJunctionsByRoad(roadId: String): List<JunctionResponseModel>

	fun getJunction(id: String): Result<JunctionResponseModel>

	fun updateJunction(junctionId: String, junctionUpdateModel: JunctionUpdateModel): Result<JunctionResponseModel>

    fun getSemaphores(semaphoresRequestModel: SemaphoresRequestModel): Result<List<SemaphoreResponseModel>>

    fun getSemaphoreColor(idSemaphore: Int): Result<String>

    fun getSemaphoreTopicEvents(): Result<String>

    fun getTrafficDigitalTwin(trafficDigitalTwinRequestModel: TrafficDigitalTwinRequestModel): Result<TrafficDigitalTwinModel>

    fun addSemaphore(newSemaphoreRequestModel: NewSemaphoreRequestModel): Result<String>
    fun addTrafficDt(newTrafficDigitalTwinRequest: NewTrafficDigitalTwinRequest): Result<String>
    fun getAllSemaphores(): Result<List<SemaphoreResponseModel>>
    fun getRoads(): Result<List<RoadResponseModel>>
}
