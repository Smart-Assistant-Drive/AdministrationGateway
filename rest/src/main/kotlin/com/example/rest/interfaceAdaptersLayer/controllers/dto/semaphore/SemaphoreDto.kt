package com.example.rest.interfaceAdaptersLayer.controllers.dto.semaphore

import com.example.rest.businessLayer.adapter.semaphore.NewSemaphoreRequestModel
import com.example.rest.businessLayer.adapter.semaphore.Position
import com.example.rest.businessLayer.adapter.semaphore.SemaphoreResponseModel
import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty
import org.springframework.hateoas.RepresentationModel

data class SemaphoreDto
	@JsonCreator
	constructor(
        @param:JsonProperty("id") val id: String,
        @param:JsonProperty("status") val status: String,
        @param:JsonProperty("road") val road: String,
        @param:JsonProperty("direction") val direction: Int,
        @param:JsonProperty("position") val position: Position,
	) : RepresentationModel<SemaphoreDto>()

fun SemaphoreDto.toModel(): SemaphoreResponseModel =
	SemaphoreResponseModel(
		id,
		status,
		road,
		direction,
		Pair(position.x, position.y)
	)

fun NewSemaphoreRequestModel.toDto(): SemaphoreDto =
    SemaphoreDto(
        id,
        "na",
        road,
        direction,
        position
    )