package com.example.rest.interfaceAdaptersLayer.infrastructure.dto.login

import com.example.rest.businessLayer.adapter.login.LoginResponseModel
import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty
import org.springframework.hateoas.RepresentationModel

class LoginResponseDto
    @JsonCreator
    constructor(
        @param:JsonProperty("name")
        val name: String,
        @param:JsonProperty("token")
        val token: String,
    ) : RepresentationModel<LoginResponseDto>()

fun LoginResponseDto.toModel() =
    LoginResponseModel(
        name = name,
        token = token,
    )
