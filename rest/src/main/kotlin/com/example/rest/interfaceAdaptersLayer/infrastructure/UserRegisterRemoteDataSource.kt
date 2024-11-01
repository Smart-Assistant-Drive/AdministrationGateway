package com.example.rest.interfaceAdaptersLayer.infrastructure

import com.example.rest.businessLayer.adapter.login.LoginRequestModel
import com.example.rest.businessLayer.adapter.login.LoginResponseModel
import com.example.rest.businessLayer.adapter.token.TokenResponseModel
import com.example.rest.businessLayer.adapter.user.UserRequestModel
import com.example.rest.businessLayer.adapter.user.UserResponseModel
import com.example.rest.businessLayer.boundaries.UserRegisterDataSourceGateway
import com.example.rest.businessLayer.exception.ErrorInUserCreationException
import com.example.rest.businessLayer.exception.InvalidTokenException
import com.example.rest.businessLayer.exception.ServiceException
import com.example.rest.businessLayer.exception.UnauthorizedAccessException
import com.example.rest.businessLayer.exception.UserAlreadyPresentException
import com.example.rest.domainLayer.Role
import com.example.rest.interfaceAdaptersLayer.infrastructure.dto.createUser.UserResponseDto
import com.example.rest.interfaceAdaptersLayer.infrastructure.dto.createUser.toDto
import com.example.rest.interfaceAdaptersLayer.infrastructure.dto.createUser.toModel
import com.example.rest.interfaceAdaptersLayer.infrastructure.dto.login.LoginResponseDto
import com.example.rest.interfaceAdaptersLayer.infrastructure.dto.login.toDto
import com.example.rest.interfaceAdaptersLayer.infrastructure.dto.login.toModel
import com.example.rest.interfaceAdaptersLayer.infrastructure.dto.token.TokenResponseDto
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.web.client.RestClient
import org.springframework.web.client.RestClientException

class UserRegisterRemoteDataSource(
    host: String,
) : UserRegisterDataSourceGateway {
    private val restClient =
        RestClient
            .builder()
            .baseUrl(host)
            .build()

    override fun login(requestModel: LoginRequestModel): Result<LoginResponseModel> {
        try {
            val user =
                restClient
                    .post()
                    .uri("/login_user")
                    .body(requestModel.toDto())
                    .accept(APPLICATION_JSON)
                    .retrieve()
                    .onStatus({ it.is4xxClientError }) { _, response ->
                        throw UnauthorizedAccessException(String(response.body.readAllBytes()))
                    }.onStatus({ it.is5xxServerError }) { _, response ->
                        throw ServiceException(String(response.body.readAllBytes()))
                    }.toEntity(LoginResponseDto::class.java)
            return Result.success(user.body!!.toModel())
        } catch (e: Exception) {
            return Result.failure(e)
        }
    }

    override fun getUserFromToken(token: String): Result<TokenResponseModel> {
        try {
            val user =
                restClient
                    .get()
                    .uri("/check_token/{token}", token)
                    .accept(APPLICATION_JSON)
                    .retrieve()
                    .onStatus({ it.is4xxClientError }) { _, response ->
                        throw UnauthorizedAccessException(String(response.body.readAllBytes()))
                    }.onStatus({ it.is5xxServerError }) { _, response ->
                        throw ServiceException(String(response.body.readAllBytes()))
                    }.toEntity(TokenResponseDto::class.java)
            return Result.success(TokenResponseModel(user.body!!.user, Role.valueOf(user.body!!.role)))
        } catch (e: InvalidTokenException) {
            return Result.failure(e)
        } catch (e: Exception) {
            return Result.failure(e)
        }
    }

    override fun createUser(requestModel: UserRequestModel): Result<UserResponseModel> {
        try {
            val user =
                restClient
                    .post()
                    .uri("/create_user")
                    .body(requestModel.toDto())
                    .accept(APPLICATION_JSON)
                    .retrieve()
                    .onStatus({ it.isSameCodeAs(HttpStatus.BAD_REQUEST) }) { _, response ->
                        throw ErrorInUserCreationException(String(response.body.readAllBytes()))
                    }.onStatus({ it.isSameCodeAs(HttpStatus.CONFLICT) }) { _, _ ->
                        throw UserAlreadyPresentException()
                    }.onStatus({ it.is5xxServerError }) { _, response ->
                        throw ServiceException(String(response.body.readAllBytes()))
                    }.toEntity(UserResponseDto::class.java)
            return Result.success(user.body!!.toModel())
        } catch (e: RestClientException) {
            return Result.failure(e)
        }
    }
}
