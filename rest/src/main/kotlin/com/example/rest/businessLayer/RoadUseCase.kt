package com.example.rest.businessLayer

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
import com.example.rest.businessLayer.boundaries.RoadDataSourceGateway
import com.example.rest.businessLayer.boundaries.RoadInputBoundary

class RoadUseCase(
	private val roadDataSourceGateway: RoadDataSourceGateway,
): RoadInputBoundary {

    companion object {
        private val value: String? = System.getenv("BROKER_URL")

        val BROKER_URL: String = if(value.isNullOrEmpty()) "mqtt://127.0.0.1:1883" else value
    }

	override fun addRoad(roadModel: RoadModel): Result<RoadResponseModel> {
		return roadDataSourceGateway.addRoad(roadModel)
	}

	override fun getRoad(roadId: String): Result<RoadResponseModel> {
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
		return roadDataSourceGateway.getJunctionsByRoad(roadId).getOrElse {
            println("ERROR: " + it.message)
            ArrayList()
        }
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

    override fun getSemaphores(semaphoresRequestModel: SemaphoresRequestModel): Result<List<SemaphoreResponseModel>> {
        return roadDataSourceGateway.getSemaphores(semaphoresRequestModel)
    }

    override fun getSemaphoreColor(idSemaphore: Int): Result<String> {
        return roadDataSourceGateway.getSemaphoreColor(idSemaphore)
    }

    override fun addSemaphore(newSemaphoreRequestModel: NewSemaphoreRequestModel): Result<String> {
        return roadDataSourceGateway.createSemaphore(newSemaphoreRequestModel)
    }

    override fun addTrafficDt(newTrafficDigitalTwinRequest: NewTrafficDigitalTwinRequest): Result<String> {
        return roadDataSourceGateway.createTrafficDt(newTrafficDigitalTwinRequest)
    }

    override fun getSemaphoreTopicEvents(): Result<String> {
        return Result.success(BROKER_URL)
    }

    override fun getTrafficDigitalTwin(trafficDigitalTwinRequestModel: TrafficDigitalTwinRequestModel): Result<TrafficDigitalTwinModel> {
        return roadDataSourceGateway.getTrafficDigitalTwins(trafficDigitalTwinRequestModel)
    }

    override fun getAllSemaphores(): Result<List<SemaphoreResponseModel>> {
        return roadDataSourceGateway.getAllSemaphores()
    }

    override fun getRoads(): Result<List<RoadResponseModel>> {
        return roadDataSourceGateway.getRoads()
    }

}