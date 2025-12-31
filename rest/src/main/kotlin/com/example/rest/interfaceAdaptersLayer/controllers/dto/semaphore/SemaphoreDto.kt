package com.example.rest.interfaceAdaptersLayer.controllers.dto.semaphore

import com.example.rest.businessLayer.adapter.semaphore.NewSemaphoreRequestModel
import com.example.rest.businessLayer.adapter.semaphore.Position
import com.example.rest.businessLayer.adapter.semaphore.SemaphoreResponseModel
import com.example.rest.domainLayer.Coordinate
import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty
import org.springframework.hateoas.RepresentationModel

data class SemaphoreDto
	@JsonCreator
	constructor(
        @param:JsonProperty("id") val id: String,
        @param:JsonProperty("link") val link: String,
        @param:JsonProperty("status") val status: String,
        @param:JsonProperty("road") val road: String,
        @param:JsonProperty("direction") val direction: Int,
        @param:JsonProperty("position") val position: Coordinate,
        @param:JsonProperty("idIndex") val idIndex: Int
	) : RepresentationModel<SemaphoreDto>()

fun SemaphoreDto.toModel(): SemaphoreResponseModel =
	SemaphoreResponseModel(
		id,
		status,
		road,
		direction,
        Coordinate(position.x, position.y),
        id,
        idIndex
	)

fun NewSemaphoreRequestModel.toDto(): SemaphoreDto =
    SemaphoreDto(
        id,
        "",
        "na",
        road,
        direction,
        Coordinate(position.x, position.y),
        0
    )