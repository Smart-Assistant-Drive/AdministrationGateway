package com.example.rest.businessLayer

import com.example.rest.businessLayer.adapter.login.LoginRequestModel
import com.example.rest.businessLayer.adapter.login.LoginResponseModel
import com.example.rest.businessLayer.adapter.token.TokenResponseModel
import com.example.rest.businessLayer.adapter.user.UserRequestModel
import com.example.rest.businessLayer.adapter.user.UserRequestingModel
import com.example.rest.businessLayer.adapter.user.UserResponseModel
import com.example.rest.businessLayer.boundaries.UserInputBoundary
import com.example.rest.businessLayer.boundaries.UserRegisterDataSourceGateway
import com.example.rest.businessLayer.exception.MissingUserPrivilegeException
import com.example.rest.domainLayer.Role

class UserRegisterUseCase(
    private val userDataSourceGateway: UserRegisterDataSourceGateway,
) : UserInputBoundary {
    override fun createUser(
        requestModel: UserRequestModel,
        user: UserRequestingModel,
    ): Result<UserResponseModel> {
        if (user.role == Role.ADMIN) {
            return Result.failure(MissingUserPrivilegeException())
        }
        return userDataSourceGateway.createUser(requestModel)
    }

    override fun login(requestModel: LoginRequestModel): Result<LoginResponseModel> = userDataSourceGateway.login(requestModel)

    override fun checkUserToken(token: String): Result<TokenResponseModel> = userDataSourceGateway.getUserFromToken(token)
}
