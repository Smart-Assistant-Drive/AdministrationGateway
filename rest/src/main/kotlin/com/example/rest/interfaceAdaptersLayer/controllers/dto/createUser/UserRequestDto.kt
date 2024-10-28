package com.example.rest.interfaceAdaptersLayer.controllers.dto.createUser

import com.example.rest.businessLayer.adapter.user.UserRequestModel
import com.example.rest.domainLayer.Role
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

fun UserRequestDto.toModel() = UserRequestModel(name, password, Role.valueOf(role?.uppercase() ?: "USER"))
