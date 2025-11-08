package com.example.rest.interfaceAdaptersLayer.infrastructure.dto.road

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty
import org.springframework.hateoas.RepresentationModel

data class DrivingFlowUpdateDto
	@JsonCreator
	constructor(
		@param:JsonProperty("flowId") val flowId: String,
		@param:JsonProperty("direction") val direction: Int,
		@param:JsonProperty("numOfLanes") val numOfLanes: Int,
		@param:JsonProperty("coordinates") val coordinates: ArrayList<Pair<Int, Int>>,
	) : RepresentationModel<DrivingFlowUpdateDto>()
