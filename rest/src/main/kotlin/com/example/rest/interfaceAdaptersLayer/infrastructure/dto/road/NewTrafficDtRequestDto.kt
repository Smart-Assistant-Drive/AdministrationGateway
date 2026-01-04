package com.example.rest.interfaceAdaptersLayer.infrastructure.dto.road

import com.example.rest.businessLayer.adapter.road.NewTrafficDigitalTwinRequestBody
import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty
import org.springframework.hateoas.RepresentationModel

data class NewTrafficDtRequestDto
    @JsonCreator
    constructor(
        @param:JsonProperty("roadId")
        val roadId: String,
        @param:JsonProperty("direction")
        val direction: Int,
        @param:JsonProperty("link")
        val link: String,
        @param:JsonProperty("numLanes")
        val numLanes: Int,
        @param:JsonProperty("numBlocks")
        val numBlocks: Int
    ) : RepresentationModel<NewTrafficDtRequestDto>()

fun NewTrafficDtRequestDto.toModel(): NewTrafficDigitalTwinRequestBody {
    return NewTrafficDigitalTwinRequestBody(
        this.roadId,
        this.direction,
        this.link,
        this.numLanes,
        this.numBlocks
    )
}

fun NewTrafficDigitalTwinRequestBody.toDto(): NewTrafficDtRequestDto {
    return NewTrafficDtRequestDto(
        this.roadId,
        this.direction,
        this.link,
        this.numLanes,
        this.numBlocks
    )
}
