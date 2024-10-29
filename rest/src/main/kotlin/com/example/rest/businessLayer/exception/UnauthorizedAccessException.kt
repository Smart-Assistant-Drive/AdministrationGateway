package com.example.rest.businessLayer.exception

class UnauthorizedAccessException(
    override val message: String,
) : Exception()
