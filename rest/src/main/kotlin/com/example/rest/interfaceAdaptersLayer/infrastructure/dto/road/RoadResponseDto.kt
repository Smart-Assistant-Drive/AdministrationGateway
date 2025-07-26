package com.example.rest.interfaceAdaptersLayer.infrastructure.dto.road

import com.example.rest.businessLayer.adapter.road.RoadModel
import com.example.rest.businessLayer.adapter.road.RoadResponseModel
import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty
import org.springframework.hateoas.Link
import org.springframework.hateoas.RepresentationModel

data class RoadResponseDto
	@JsonCreator
	constructor(
		@param:JsonProperty("roadId")
		val roadId: String,
		@param:JsonProperty("roadNumber")
		val roadNumber: String,
		@param:JsonProperty("roadName")
		val roadName: String,
		@param:JsonProperty("category")
		val category: String
	) : RepresentationModel<RoadResponseDto>()

fun RoadResponseModel.toDto(links: List<Link>): RoadResponseDto =
	RoadResponseDto(
		this.roadId,
		this.roadNumber,
		this.roadName,
		this.category.toString()
	).add(links)