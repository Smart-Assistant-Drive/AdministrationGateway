package com.example.rest.businessLayer.boundaries

import com.example.rest.businessLayer.adapter.login.LoginRequestModel
import com.example.rest.businessLayer.adapter.login.LoginResponseModel
import com.example.rest.businessLayer.adapter.token.TokenResponseModel
import com.example.rest.businessLayer.adapter.user.UserRequestModel
import com.example.rest.businessLayer.adapter.user.UserRequestingModel
import com.example.rest.businessLayer.adapter.user.UserResponseModel

interface UserInputBoundary {
    fun createUser(
        requestModel: UserRequestModel,
        user: UserRequestingModel,
    ): Result<UserResponseModel>

    fun login(requestModel: LoginRequestModel): Result<LoginResponseModel>

    fun checkUserToken(token: String): Result<TokenResponseModel>

    /*
    fun changePassword(requestModel: UserRequestModel): Result<UserResponseModel>

    fun deleteUser(requestModel: UserRequestModel): Result<UserResponseModel>

    fun changeRole(requestModel: UserRequestModel): Result<UserResponseModel>

     */
}
