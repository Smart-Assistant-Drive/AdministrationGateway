package com.example.rest.businessLayer.exception

class UserNotFound : Exception() {
    override val message: String
        get() = "User not found"
}
