package com.example.rest.interfaceAdaptersLayer.infrastructure

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
import com.example.rest.interfaceAdaptersLayer.infrastructure.dto.road.DrivingFlowResponseDto
import com.example.rest.interfaceAdaptersLayer.infrastructure.dto.road.DrivingFlowUpdateDto
import com.example.rest.interfaceAdaptersLayer.infrastructure.dto.road.JunctionRequestDto
import com.example.rest.interfaceAdaptersLayer.infrastructure.dto.road.JunctionResponseDto
import com.example.rest.interfaceAdaptersLayer.infrastructure.dto.road.RoadRequestDto
import com.example.rest.interfaceAdaptersLayer.infrastructure.dto.road.RoadResponseDto
import com.example.rest.interfaceAdaptersLayer.infrastructure.dto.road.TrafficResponseDto
import com.example.rest.interfaceAdaptersLayer.infrastructure.dto.road.toDto
import com.example.rest.interfaceAdaptersLayer.infrastructure.dto.road.toModel
import com.example.rest.interfaceAdaptersLayer.infrastructure.dto.semaphore.SemaphoreDto
import com.example.rest.interfaceAdaptersLayer.infrastructure.dto.semaphore.toDto
import com.example.rest.interfaceAdaptersLayer.infrastructure.dto.semaphore.toModel
import java.lang.Integer.parseInt
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.MediaType
import org.springframework.web.client.RestClient

class RoadRemoteDataSource(url: String, semaphoreDtUrl: String, semaphoreDtAdminUrl: String, trafficDtUrl: String, trafficDtAdminUrl: String): RoadDataSourceGateway {

	private val restClient =
		RestClient
			.builder()
			.baseUrl(url)
			.build()

    private val semaphoresDtClient =
        RestClient
            .builder()
            .baseUrl(semaphoreDtUrl)
            .build()

    private val semaphoresAdminDtClient =
        RestClient
            .builder()
            .baseUrl(semaphoreDtAdminUrl)
            .build()

    private val trafficDtClient =
        RestClient
            .builder()
            .baseUrl(trafficDtUrl)
            .build()

    private val trafficAdminDtClient =
        RestClient
            .builder()
            .baseUrl(trafficDtAdminUrl)
            .build()

	override fun addRoad(roadModel: RoadModel): Result<RoadResponseModel> =
		try {
			val response =
				restClient
					.post()
					.uri("/road")
					.contentType(MediaType.APPLICATION_JSON)
					.body(roadModel.toDto())
					.retrieve()
					.toEntity(object : ParameterizedTypeReference<RoadResponseDto>() {})
					.body!!
			//Result.success(RoadResponseModel("","","",0)/*response.toResponseModel()*/)
			Result.success(response.toResponseModel())
		} catch (e: Exception) {
			println("ERRORE: $e")
			Result.failure(e)
		}

	override fun getRoad(roadId: String): Result<RoadResponseModel> =
		try {
			val response =
				restClient
					.get()
					.uri("/road/{roadId}", roadId)
					.accept(MediaType.APPLICATION_JSON)
					.retrieve()
					.toEntity(object : ParameterizedTypeReference<RoadResponseDto>() {})
					.body!!
			Result.success(response.toResponseModel())
		} catch (e: Exception) {
			Result.failure(e)
		}

	override fun updateRoad(roadId: String, roadModel: RoadModel): Result<RoadResponseModel> =
		try {
			val response =
				restClient
					.put()
					.uri("/road/{roadId}", roadId)
					.body(roadModel.toDto())
					.accept(MediaType.APPLICATION_JSON)
					.retrieve()
					.toEntity(object : ParameterizedTypeReference<RoadResponseDto>() {})
					.body!!
			Result.success(response.toResponseModel())
		} catch (e: Exception) {
			Result.failure(e)
		}

	override fun addDirectionFlow(drivingFlowModel: DrivingFlowModel): Result<DrivingFlowResponseModel> =
		try {
			val response =
				restClient
					.post()
					.uri("/drivingFlow")
					.accept(MediaType.APPLICATION_JSON)
					.body(drivingFlowModel)
					.retrieve()
					.toEntity(object : ParameterizedTypeReference<DrivingFlowResponseDto>() {})
					.body!!
			Result.success(response.toModel())
		} catch (e: Exception) {
			Result.failure(e)
		}

	override fun getAllDirectionFlows(roadId: String): Result<List<DrivingFlowResponseModel>> =
		try {
			val response =
				restClient
					.get()
					.uri("/flows/{roadId}", roadId)
					.accept(MediaType.APPLICATION_JSON)
					.retrieve()
					.toEntity(object : ParameterizedTypeReference<List<DrivingFlowResponseDto>>() {})
					.body ?: emptyList()
			Result.success(response.map { it.toModel() })
		} catch (e: Exception) {
			Result.failure(e)
		}

	override fun changeDrivingFlow(drivingFlowUpdateModel: DrivingFlowUpdateModel): Result<DrivingFlowResponseModel> =
		try {
			val response =
				restClient
					.put()
					.uri("/flows")
					.body(drivingFlowUpdateModel.toDto())
					.accept(MediaType.APPLICATION_JSON)
					.retrieve()
					.toEntity(object : ParameterizedTypeReference<DrivingFlowResponseDto>() {})
					.body!!
			Result.success(response.toModel())
		} catch (e: Exception) {
			Result.failure(e)
		}

	override fun addJunction(junctionModel: JunctionModel): Result<JunctionResponseModel> =
		try {
			val response =
				restClient
					.post()
					.uri("/junction")
					.accept(MediaType.APPLICATION_JSON)
					.body(junctionModel)
					.retrieve()
					.toEntity(object : ParameterizedTypeReference<JunctionResponseDto>() {})
					.body!!
			Result.success(response.toModel())
		} catch (e: Exception) {
			Result.failure(e)
		}

	override fun getJunctionsByRoad(roadId: String): Result<List<JunctionResponseModel>> =
		try {
			val response =
				restClient
					.get()
					.uri("/junction/road/{roadId}", roadId)
					.accept(MediaType.APPLICATION_JSON)
					.retrieve()
					.toEntity(object : ParameterizedTypeReference<List<JunctionResponseDto>>() {})
					.body ?: emptyList()
			Result.success(response.map { it.toModel() })
		} catch (e: Exception) {
			Result.failure(e)
		}

	override fun getJunction(id: String): Result<JunctionResponseModel> =
		try {
			val response =
				restClient
					.get()
					.uri("/junction/{id}", id)
					.accept(MediaType.APPLICATION_JSON)
					.retrieve()
					.toEntity(object : ParameterizedTypeReference<JunctionResponseDto>() {})
					.body!!
			Result.success(response.toModel())
		} catch (e: Exception) {
			Result.failure(e)
		}

	override fun updateJunction(
		junctionId: String,
		junctionUpdateModel: JunctionUpdateModel
	): Result<JunctionResponseModel>  =
		try {
			val response =
				restClient
					.put()
					.uri("/junction/{junctionId}", junctionId)
					.body(junctionUpdateModel.toDto())
					.accept(MediaType.APPLICATION_JSON)
					.retrieve()
					.toEntity(object : ParameterizedTypeReference<JunctionResponseDto>() {})
					.body!!
			Result.success(response.toModel())
		} catch (e: Exception) {
			Result.failure(e)
		}

    override fun getSemaphores(semaphoresRequestModel: SemaphoresRequestModel): Result<List<SemaphoreResponseModel>> =
        try {
            val road = semaphoresRequestModel.road
            val direction = semaphoresRequestModel.direction
            val response =
                semaphoresDtClient
                    .get().uri {
                        it.path("/semaphores/filter").queryParam("road", road).queryParam("direction", direction).build()
                    }
                    .accept(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .toEntity(object : ParameterizedTypeReference<List<SemaphoreDto>>() {})
                    .body ?: emptyList()
            Result.success(response.map { it.toModel() })
        } catch (e: Exception) {
            Result.failure(e)
        }

    override fun getAllSemaphores(): Result<List<SemaphoreResponseModel>> =
        try {
            val response =
                semaphoresDtClient
                    .get()
                    .uri("/semaphores")
                    .accept(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .toEntity(object : ParameterizedTypeReference<List<SemaphoreDto>>() {})
                    .body ?: emptyList()
            Result.success(response.map { it.toModel() })
        } catch (e: Exception) {
            Result.failure(e)
        }

    override fun getRoads(): Result<List<RoadResponseModel>> =
        try {
            val response =
                restClient
                    .get()
                    .uri("/roads")
                    .accept(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .toEntity(object : ParameterizedTypeReference<List<RoadResponseDto>>() {})
                    .body ?: emptyList()
            Result.success(response.map { it.toResponseModel() })
        } catch (e: Exception) {
            Result.failure(e)
        }

    override fun getSemaphoreColor(idSemaphore: Int): Result<String> =
        try {
            val response =
                semaphoresDtClient
                    .get()
                    .uri("/semaphores/color?idIndex={idSemaphore}", idSemaphore)
                    .accept(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .toEntity(object : ParameterizedTypeReference<String>() {})
                    .body!!
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }

    override fun createSemaphore(semaphoreRequestModel: NewSemaphoreRequestModel): Result<String> =
        try {
            val response =
                semaphoresAdminDtClient
                    .post()
                    .uri("/state/actions/createSemaphore")
                    .body(semaphoreRequestModel.toDto())
                    .accept(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .toEntity(object : ParameterizedTypeReference<String>() {})
                    .toString()
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }

    override fun createTrafficDt(newTrafficDigitalTwinRequest: NewTrafficDigitalTwinRequest): Result<String> =
        try {
            val response =
                trafficAdminDtClient
                    .post()
                    .uri("/state/actions/createTrafficDt")
                    .body(newTrafficDigitalTwinRequest.toDto())
                    .accept(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .toEntity(object : ParameterizedTypeReference<String>() {})
                    .toString()
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }

    override fun getTrafficDigitalTwins(trafficDigitalTwinRequestModel: TrafficDigitalTwinRequestModel): Result<TrafficDigitalTwinModel> =
        try {
            val roadId: String = trafficDigitalTwinRequestModel.roadId
            val direction: Int = trafficDigitalTwinRequestModel.direction
            println("REQUEST WITH $roadId and $direction")
            val response =
                trafficDtClient
                    .get()
                    .uri("/getByRoadId?roadId={roadId}&direction={direction}", roadId, direction)
                    .accept(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .toEntity(object : ParameterizedTypeReference<TrafficResponseDto>() {})
                    .body!!
            Result.success(response.toModel())
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(e)
        }

	private fun RoadResponseDto.toResponseModel(): RoadResponseModel =
		RoadResponseModel(
			this.roadId,
			this.roadNumber,
			this.roadName,
			parseInt(this.category)
		)

	private fun RoadResponseDto.toModel(): RoadModel =
		RoadModel(
			this.roadNumber,
			this.roadName,
			parseInt(this.category)
		)

	private fun RoadModel.toDto(): RoadRequestDto =
		RoadRequestDto(
			this.roadNumber,
			this.roadName,
			this.category
		)

	private fun DrivingFlowResponseDto.toModel(): DrivingFlowResponseModel =
		DrivingFlowResponseModel(
			this.roadId,
			this.flowId,
			this.direction,
			this.numOfLanes,
			this.coordinates
		)

	private fun JunctionResponseDto.toModel(): JunctionResponseModel =
		JunctionResponseModel(
			this.junctionId,
			this.outgoingRoads,
			this.junctionType,
			this.position
		)

	fun DrivingFlowUpdateModel.toDto(): DrivingFlowUpdateDto {
		return DrivingFlowUpdateDto(
			flowId,
			idDirection,
			numOfLanes,
			roadCoordinates
		)
	}

	fun JunctionUpdateModel.toDto(): JunctionRequestDto {
		return JunctionRequestDto(
			newOutgoingRoads,
			newJunctionType
		)
	}
}