package com.example.rest.interfaceAdaptersLayer.infrastructure.dto.createUser

import com.example.rest.businessLayer.adapter.user.UserRequestModel
import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty

data class UserRequestDto
    @JsonCreator
    constructor(
        @param:JsonProperty("name")
        val name: String,
        @param:JsonProperty("password")
        val password: String,
        @param:JsonProperty("role")
        val role: String?,
    )

fun UserRequestModel.toDto() = UserRequestDto(name, password, role.toString())
