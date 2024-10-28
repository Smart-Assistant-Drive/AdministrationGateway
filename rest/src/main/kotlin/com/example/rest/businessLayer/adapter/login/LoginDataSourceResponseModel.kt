package com.example.rest.businessLayer.adapter.login

import com.example.rest.domainLayer.Role

data class LoginDataSourceResponseModel(
    val name: String,
    val password: String,
    val role: Role,
)
