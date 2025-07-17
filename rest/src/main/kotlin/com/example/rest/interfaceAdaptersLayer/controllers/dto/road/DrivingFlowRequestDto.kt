package com.example.rest.interfaceAdaptersLayer.controllers.dto.road

import com.example.rest.businessLayer.adapter.road.drivingFlow.DrivingFlowModel
import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty
import org.springframework.hateoas.Link
import org.springframework.hateoas.RepresentationModel

data class DrivingFlowRequestDto
	@JsonCreator
	constructor(
		@param:JsonProperty("roadId") val roadId: String,
		@param:JsonProperty("direction") val direction: Int,
		@param:JsonProperty("numOfLanes") val numOfLanes: Int,
		@param:JsonProperty("coordinates") val coordinates: ArrayList<Pair<Int, Int>>,
	) : RepresentationModel<DrivingFlowRequestDto>()

fun DrivingFlowModel.toDto(link: Link): DrivingFlowRequestDto {
	return DrivingFlowRequestDto(
		roadId,
		idDirection,
		numOfLanes,
		roadCoordinates
	).add(
		link
	)
}

fun DrivingFlowRequestDto.toModel(): DrivingFlowModel {
	return DrivingFlowModel(
		roadId,
		direction,
		numOfLanes,
		coordinates
	)
}
