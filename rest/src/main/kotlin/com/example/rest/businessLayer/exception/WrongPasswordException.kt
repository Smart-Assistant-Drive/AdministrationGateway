package com.example.rest.businessLayer.exception

class WrongPasswordException : Exception() {
    override val message: String
        get() = "Wrong password"
}
