package com.example.rest.interfaceAdaptersLayer.infrastructure.dto.road

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty
import org.springframework.hateoas.RepresentationModel

data class JunctionResponseDto
	@JsonCreator
	constructor(
		@param:JsonProperty("junctionId") val junctionId: String,
		@param:JsonProperty("outgoingRoads") val outgoingRoads: ArrayList<Pair<String, Int>>,
		@param:JsonProperty("junctionType") val junctionType: Int,
		@param:JsonProperty("position") val position: Pair<Float, Float>,
	) : RepresentationModel<DrivingFlowRequestDto>()
