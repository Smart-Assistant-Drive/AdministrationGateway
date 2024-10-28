package com.example.rest.interfaceAdaptersLayer.infrastructure.dto.token

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty
import org.springframework.hateoas.RepresentationModel

class TokenErrorDto
    @JsonCreator
    constructor(
        @param:JsonProperty("message")
        val message: String,
    ) : RepresentationModel<TokenErrorDto>()
