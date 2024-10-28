package com.example.rest.businessLayer.adapter.user

import com.example.rest.domainLayer.Role

data class UserRequestingModel(
    val name: String,
    val role: Role,
)
