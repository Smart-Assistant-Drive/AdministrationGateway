package com.example.rest.interfaceAdaptersLayer.infrastructure.dto.road

import com.example.rest.domainLayer.OutgoingRoad
import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty
import org.springframework.hateoas.RepresentationModel

data class JunctionRequestDto
	@JsonCreator constructor(
        @param:JsonProperty("outgoingRoads") val outgoingRoads: ArrayList<OutgoingRoad>,
        @param:JsonProperty("junctionType") val junctionType: Int,
	) : RepresentationModel<JunctionRequestDto>()
