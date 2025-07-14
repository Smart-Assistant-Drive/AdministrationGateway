package com.example.rest.interfaceAdaptersLayer.infrastructure

import com.example.rest.businessLayer.adapter.road.DrivingFlowModel
import com.example.rest.businessLayer.adapter.road.DrivingFlowResponseModel
import com.example.rest.businessLayer.adapter.road.DrivingFlowUpdateModel
import com.example.rest.businessLayer.adapter.road.RoadModel
import com.example.rest.businessLayer.adapter.road.RoadResponseModel
import com.example.rest.businessLayer.boundaries.RoadDataSourceGateway
import com.example.rest.interfaceAdaptersLayer.infrastructure.dto.road.DrivingFlowResponseDto
import com.example.rest.interfaceAdaptersLayer.infrastructure.dto.road.DrivingFlowUpdateDto
import com.example.rest.interfaceAdaptersLayer.infrastructure.dto.road.RoadRequestDto
import com.example.rest.interfaceAdaptersLayer.infrastructure.dto.road.RoadResponseDto
import com.example.rest.interfaceAdaptersLayer.infrastructure.dto.road.toDto
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.MediaType
import org.springframework.web.client.RestClient

class RoadRemoteDataSource(url: String): RoadDataSourceGateway {

	private val restClient =
		RestClient
			.builder()
			.baseUrl(url)
			.build()

	override fun addRoad(roadModel: RoadModel): Result<RoadResponseModel> =
		try {
			val response =
				restClient
					.post()
					.uri("/road")
					.accept(MediaType.APPLICATION_JSON)
					.body(roadModel)
					.retrieve()
					.toEntity(object : ParameterizedTypeReference<RoadResponseDto>() {})
					.body!!
			Result.success(response.toResponseModel())
		} catch (e: Exception) {
			Result.failure(e)
		}

	override fun getRoad(roadId: String): Result<RoadModel> =
		try {
			val response =
				restClient
					.get()
					.uri("/road/{$roadId}")
					.accept(MediaType.APPLICATION_JSON)
					.retrieve()
					.toEntity(object : ParameterizedTypeReference<RoadResponseDto>() {})
					.body!!
			Result.success(response.toModel())
		} catch (e: Exception) {
			Result.failure(e)
		}

	override fun updateRoad(roadId: String, roadModel: RoadModel): Result<RoadResponseModel> =
		try {
			val response =
				restClient
					.put()
					.uri("/road/{${roadId}}")
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
					.uri("/flows/{$roadId}")
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

	private fun RoadResponseDto.toResponseModel(): RoadResponseModel =
		RoadResponseModel(
			this.roadId,
			this.roadNumber,
			this.roadName,
			this.category
		)

	private fun RoadResponseDto.toModel(): RoadModel =
		RoadModel(
			this.roadNumber,
			this.roadName,
			this.category
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

	fun DrivingFlowUpdateModel.toDto(): DrivingFlowUpdateDto {
		return DrivingFlowUpdateDto(
			flowId,
			idDirection,
			numOfLanes,
			roadCoordinates
		)
	}
}