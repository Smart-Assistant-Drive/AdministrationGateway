package com.example.rest.interfaceAdaptersLayer.controllers.dto.road

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty
import org.springframework.hateoas.Link
import org.springframework.hateoas.RepresentationModel

data class StringResponseDto
@JsonCreator
constructor(
    @param:JsonProperty("response") val roadId: String
) : RepresentationModel<StringResponseDto>()

fun String.toDto(link: List<Link>): StringResponseDto {
    return StringResponseDto(
        this
    ).add(
        link
    )
}