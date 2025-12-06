package com.example.rest.interfaceAdaptersLayer.infrastructure.dto.road

import com.example.rest.businessLayer.adapter.road.TrafficDigitalTwinModel
import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty
import org.springframework.hateoas.RepresentationModel

data class TrafficResponseDto
	@JsonCreator
	constructor(
		@param:JsonProperty("roadId")
		val roadId: String,
		@param:JsonProperty("direction")
		val direction: Int,
		@param:JsonProperty("link")
		val link: String,
		@param:JsonProperty("numLanes")
		val numLanes: Int
	) : RepresentationModel<TrafficResponseDto>()

fun TrafficResponseDto.toModel(): TrafficDigitalTwinModel {
	return TrafficDigitalTwinModel(
		this.roadId,
		this.direction,
		this.link,
		this.numLanes
	)
}