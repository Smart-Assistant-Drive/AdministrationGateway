package com.example.rest.interfaceAdaptersLayer.infrastructure.dto.createUser

import com.example.rest.businessLayer.adapter.user.UserResponseModel
import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty
import org.springframework.hateoas.RepresentationModel

class UserResponseDto
    @JsonCreator
    constructor(
        @param:JsonProperty("name") val name: String,
        @param:JsonProperty("token") val token: String,
        @param:JsonProperty("time") val time: String,
    ) : RepresentationModel<UserResponseDto>()

fun UserResponseDto.toModel() = UserResponseModel(name, token, time)
