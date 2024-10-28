package com.example.rest.interfaceAdaptersLayer.infrastructure.dto.token

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty
import org.springframework.hateoas.RepresentationModel

class TokenResponseDto
    @JsonCreator
    constructor(
        @param:JsonProperty("user")
        val user: String,
        @param:JsonProperty("role")
        val role: String,
    ) : RepresentationModel<TokenResponseDto>()
