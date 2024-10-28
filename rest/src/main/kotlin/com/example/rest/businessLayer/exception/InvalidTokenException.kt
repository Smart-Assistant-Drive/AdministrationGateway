package com.example.rest.businessLayer.exception

class InvalidTokenException : Exception() {
    override val message: String
        get() = "Invalid token"
}
