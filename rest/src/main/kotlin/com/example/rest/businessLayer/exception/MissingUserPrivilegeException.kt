package com.example.rest.businessLayer.exception

class MissingUserPrivilegeException : Exception() {
    override val message: String
        get() = "User does not have the required privilege"
}
