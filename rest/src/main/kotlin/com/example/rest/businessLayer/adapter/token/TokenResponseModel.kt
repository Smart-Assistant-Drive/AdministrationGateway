package com.example.rest.businessLayer.adapter.token

import com.example.rest.domainLayer.Role

data class TokenResponseModel(
    val user: String,
    val role: Role,
)
