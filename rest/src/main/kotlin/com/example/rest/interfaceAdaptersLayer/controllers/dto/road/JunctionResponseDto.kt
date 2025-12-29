package com.example.rest.interfaceAdaptersLayer.controllers.dto.road

import com.example.rest.businessLayer.adapter.road.junction.JunctionResponseModel
import com.example.rest.domainLayer.Coordinate
import com.example.rest.domainLayer.OutgoingRoad
import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty
import org.springframework.hateoas.Link
import org.springframework.hateoas.RepresentationModel

data class JunctionResponseDto
@JsonCreator
constructor(
    @param:JsonProperty("junctionId") val junctionId: String,
    @param:JsonProperty("outgoingRoads") val outgoingRoads: ArrayList<OutgoingRoad>,
    @param:JsonProperty("junctionType") val junctionType: Int,
    @param:JsonProperty("position") val position: Coordinate,
) : RepresentationModel<JunctionResponseDto>()

fun JunctionResponseModel.toDto(link: List<Link>) = JunctionResponseDto(
    this.junctionId,
    this.outgoingRoads,
    this.junctionType,
    this.position
)