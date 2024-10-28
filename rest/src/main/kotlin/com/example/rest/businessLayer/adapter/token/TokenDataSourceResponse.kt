package com.example.rest.businessLayer.adapter.token

import com.example.rest.domainLayer.Role

data class TokenDataSourceResponse(
    val user: String,
    val role: Role,
)
