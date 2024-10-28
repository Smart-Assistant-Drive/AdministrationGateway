package com.example.rest.businessLayer.boundaries

import com.example.rest.businessLayer.adapter.login.LoginRequestModel
import com.example.rest.businessLayer.adapter.login.LoginResponseModel
import com.example.rest.businessLayer.adapter.token.TokenResponseModel
import com.example.rest.businessLayer.adapter.user.UserRequestModel
import com.example.rest.businessLayer.adapter.user.UserResponseModel

interface UserRegisterDataSourceGateway {
    fun login(requestModel: LoginRequestModel): Result<LoginResponseModel>

    fun getUserFromToken(token: String): Result<TokenResponseModel>

    fun createUser(requestModel: UserRequestModel): Result<UserResponseModel>
}
