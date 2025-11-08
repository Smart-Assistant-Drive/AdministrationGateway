package com.example.rest.interfaceAdaptersLayer.infrastructure.dto.road

import com.example.rest.businessLayer.adapter.road.drivingFlow.DrivingFlowResponseModel
import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty
import org.springframework.hateoas.Link
import org.springframework.hateoas.RepresentationModel

data class DrivingFlowResponseDto
	@JsonCreator
	constructor(
		@param:JsonProperty("flowId") val flowId: String,
		@param:JsonProperty("roadId") val roadId: String,
		@param:JsonProperty("direction") val direction: Int,
		@param:JsonProperty("numOfLanes") val numOfLanes: Int,
		@param:JsonProperty("coordinates") val coordinates: ArrayList<Pair<Int, Int>>,
	) : RepresentationModel<DrivingFlowResponseDto>()

fun DrivingFlowResponseModel.toDto(links: List<Link>): DrivingFlowResponseDto {
	return DrivingFlowResponseDto(
		flowId,
		roadId,
		idDirection,
		numOfLanes,
		roadCoordinates
	).add(
		links
	)
}
