package com.example.rest.interfaceAdaptersLayer.infrastructure.dto.road

import com.example.rest.businessLayer.adapter.road.RoadModel
import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty
import org.springframework.hateoas.RepresentationModel

data class RoadRequestDto
	@JsonCreator
	constructor(
		@param:JsonProperty("roadNumber")
		val roadNumber: String,
		@param:JsonProperty("roadName")
		val roadName: String,
		@param:JsonProperty("category")
		val category: Int
	) : RepresentationModel<RoadRequestDto>()

fun RoadRequestDto.toModel(): RoadModel =
	RoadModel(
		this.roadNumber,
		this.roadName,
		this.category
	)