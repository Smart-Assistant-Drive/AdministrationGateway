package com.example.rest.interfaceAdaptersLayer.controllers.dto.token

import com.example.rest.businessLayer.adapter.token.TokenResponseModel
import com.example.rest.interfaceAdaptersLayer.infrastructure.dto.token.TokenResponseDto
import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty
import org.springframework.hateoas.Link
import org.springframework.hateoas.RepresentationModel

class TokenResponseDto
    @JsonCreator
    constructor(
        @param:JsonProperty("user")
        val user: String,
        @param:JsonProperty("role")
        val role: String,
    ) : RepresentationModel<TokenResponseDto>()

fun TokenResponseModel.toDto(links: List<Link>) = TokenResponseDto(user, role.name).add(links)
