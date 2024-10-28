package com.example.rest.interfaceAdaptersLayer.controllers.dto.login

import com.example.rest.businessLayer.adapter.login.LoginResponseModel
import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty
import org.springframework.hateoas.Link
import org.springframework.hateoas.RepresentationModel

class LoginResponseDto
    @JsonCreator
    constructor(
        @param:JsonProperty("name")
        val name: String,
        @param:JsonProperty("token")
        val token: String,
    ) : RepresentationModel<LoginResponseDto>()

fun LoginResponseModel.toDto(links: List<Link>): LoginResponseDto = LoginResponseDto(name, token).add(links)
