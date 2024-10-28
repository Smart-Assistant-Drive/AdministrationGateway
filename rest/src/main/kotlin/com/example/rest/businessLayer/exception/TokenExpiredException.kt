package com.example.rest.businessLayer.exception

class TokenExpiredException : Exception() {
    override val message: String
        get() = "Token expired"
}
