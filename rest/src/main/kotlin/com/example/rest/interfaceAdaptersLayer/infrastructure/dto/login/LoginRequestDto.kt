package com.example.rest.interfaceAdaptersLayer.infrastructure.dto.login

import com.example.rest.businessLayer.adapter.login.LoginRequestModel
import com.example.rest.interfaceAdaptersLayer.controllers.dto.login.LoginRequestDto
import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty

class LoginRequestDto
    @JsonCreator
    constructor(
        @param:JsonProperty("username")
        val username: String,
        @param:JsonProperty("password")
        val password: String,
    )

fun LoginRequestDto.toModel() = LoginRequestModel(username, password)
