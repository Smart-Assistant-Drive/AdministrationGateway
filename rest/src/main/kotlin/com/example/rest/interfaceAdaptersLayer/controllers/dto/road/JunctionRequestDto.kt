package com.example.rest.interfaceAdaptersLayer.controllers.dto.road

import com.example.rest.businessLayer.adapter.road.junction.JunctionModel
import com.example.rest.domainLayer.Coordinate
import com.example.rest.domainLayer.OutgoingRoad
import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty
import org.springframework.hateoas.RepresentationModel

data class JunctionRequestDto
	@JsonCreator constructor(
        @param:JsonProperty("outgoingRoads") val outgoingRoads: ArrayList<OutgoingRoad>,
        @param:JsonProperty("junctionType") val junctionType: Int,
        @param:JsonProperty("position") val position: Coordinate,
	) : RepresentationModel<JunctionRequestDto>()

fun JunctionRequestDto.toModel(): JunctionModel = JunctionModel(
    this.outgoingRoads,
    this.junctionType,
    this.position
)
