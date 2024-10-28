package com.example.rest.businessLayer.exception

class UserAlreadyPresentException : Exception() {
    override val message: String
        get() = "User already present"
}
