package com.example.rest.interfaceAdaptersLayer.controllers

import com.example.rest.businessLayer.adapter.user.UserRequestModel
import com.example.rest.businessLayer.adapter.user.UserRequestingModel
import com.example.rest.businessLayer.boundaries.UserInputBoundary
import com.example.rest.businessLayer.exception.ErrorInUserCreationException
import com.example.rest.businessLayer.exception.MissingUserPrivilegeException
import com.example.rest.businessLayer.exception.UnauthorizedAccessException
import com.example.rest.businessLayer.exception.UserAlreadyPresentException
import com.example.rest.domainLayer.Role
import com.example.rest.interfaceAdaptersLayer.controllers.dto.createUser.UserRequestDto
import com.example.rest.interfaceAdaptersLayer.controllers.dto.createUser.UserResponseDto
import com.example.rest.interfaceAdaptersLayer.controllers.dto.createUser.toDto
import com.example.rest.interfaceAdaptersLayer.controllers.dto.createUser.toModel
import com.example.rest.interfaceAdaptersLayer.controllers.dto.login.LoginRequestDto
import com.example.rest.interfaceAdaptersLayer.controllers.dto.login.toDto
import com.example.rest.interfaceAdaptersLayer.controllers.dto.login.toModel
import com.example.rest.interfaceAdaptersLayer.controllers.dto.token.TokenErrorDto
import com.example.rest.interfaceAdaptersLayer.controllers.dto.token.TokenResponseDto
import com.example.rest.interfaceAdaptersLayer.controllers.dto.token.toDto
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo
import org.springframework.http.HttpEntity
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestAttribute
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import io.swagger.v3.oas.annotations.parameters.RequestBody as SwaggerRequestBody

@RestController
class AccessController(
    val userInput: UserInputBoundary,
) {
    @Operation(
        summary = "Create user",
        description = "Create user",
        requestBody =
            SwaggerRequestBody(
                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(implementation = UserRequestModel::class),
                    ),
                ],
                required = true,
            ),
        responses = [
            ApiResponse(
                responseCode = "201",
                description = "Created user",
                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(implementation = UserResponseDto::class),
                    ),
                ],
            ),
            ApiResponse(
                responseCode = "400",
                description = "Invalid user",
                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(implementation = String::class),
                    ),
                ],
            ),
            ApiResponse(
                responseCode = "401",
                description = "Unauthorized",
                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(implementation = String::class),
                    ),
                ],
            ),
            ApiResponse(
                responseCode = "409",
                description = "User already exists",
                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(implementation = String::class),
                    ),
                ],
            ),
            ApiResponse(
                responseCode = "500",
                description = "Internal server error",
                content = [Content()],
            ),
        ],
    )
    @PostMapping("/create_user")
    fun create(
        @RequestBody requestModel: UserRequestDto,
        @RequestAttribute("user") user: String? = null,
        @RequestAttribute("role") role: String? = null,
    ): HttpEntity<Any> {
        val result =
            userInput.createUser(
                requestModel
                    .toModel(),
                UserRequestingModel(user!!, Role.valueOf(role!!)),
            )
        return if (result.isSuccess) {
            val links =
                listOf(
                    linkTo(WebMvcLinkBuilder.methodOn(AccessController::class.java).create(requestModel))
                        .withSelfRel(),
                )
            ResponseEntity(
                result
                    .getOrNull()!!
                    .toDto(links),
                HttpStatus.CREATED,
            )
        } else {
            when (val exception = result.exceptionOrNull()) {
                is UserAlreadyPresentException ->
                    ResponseEntity
                        .status(HttpStatus.CONFLICT)
                        .body(exception.message)

                is ErrorInUserCreationException -> ResponseEntity.badRequest().body(exception.message)
                is MissingUserPrivilegeException ->
                    ResponseEntity
                        .status(HttpStatus.UNAUTHORIZED)
                        .body(exception.message)

                else -> ResponseEntity.internalServerError().build()
            }
        }
    }

    @Operation(
        summary = "Login user",
        description = "Login user",
        requestBody =
            SwaggerRequestBody(
                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(implementation = UserRequestModel::class),
                    ),
                ],
                required = true,
            ),
        responses = [
            ApiResponse(
                responseCode = "200",
                description = "Login user",
                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(implementation = UserResponseDto::class),
                    ),
                ],
            ),
            ApiResponse(
                responseCode = "401",
                description = "Unauthorized",
                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(implementation = String::class),
                    ),
                ],
            ),
            ApiResponse(
                responseCode = "500",
                description = "Internal server error",
                content = [Content()],
            ),
        ],
    )
    @PostMapping("/login")
    fun login(
        @RequestBody requestModel: LoginRequestDto,
    ): HttpEntity<Any> {
        val result =
            userInput.login(
                requestModel.toModel(),
            )
        return if (result.isSuccess) {
            val response = result.getOrNull()!!
            val links =
                listOf(
                    linkTo(
                        WebMvcLinkBuilder
                            .methodOn(AccessController::class.java)
                            .login(requestModel),
                    ).withSelfRel(),
                    linkTo(
                        WebMvcLinkBuilder
                            .methodOn(AccessController::class.java)
                            .checkToken("token"),
                    ).withRel("check_token"),
                )
            ResponseEntity(
                response.toDto(links),
                HttpStatus.CREATED,
            )
        } else {
            when (val exception = result.exceptionOrNull()) {
                is UnauthorizedAccessException ->
                    ResponseEntity
                        .status(HttpStatus.UNAUTHORIZED)
                        .body(exception.message)

                else -> ResponseEntity.internalServerError().build()
            }
        }
    }

    @Operation(
        summary = "Check user token",
        description = "Check user token",
        requestBody =
            SwaggerRequestBody(
                content = [Content()],
                required = true,
            ),
        responses = [
            ApiResponse(
                responseCode = "200",
                description = "Token is valid",
                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(implementation = TokenResponseDto::class),
                    ),
                ],
            ),
            ApiResponse(
                responseCode = "401",
                description = "Unauthorized",
                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(implementation = TokenErrorDto::class),
                    ),
                ],
            ),
            ApiResponse(
                responseCode = "500",
                description = "Internal server error",
                content = [Content()],
            ),
        ],
    )
    @GetMapping("/check_token/{token}")
    fun checkToken(
        @PathVariable token: String,
    ): HttpEntity<Any> {
        val result = userInput.checkUserToken(token)
        return if (result.isSuccess) {
            val links =
                listOf(
                    linkTo(WebMvcLinkBuilder.methodOn(AccessController::class.java).checkToken(token))
                        .withSelfRel(),
                )
            ResponseEntity(result.getOrNull()!!.toDto(links), HttpStatus.OK)
        } else {
            when (val exception = result.exceptionOrNull()) {
                is UnauthorizedAccessException -> {
                    val links =
                        listOf(
                            linkTo(
                                WebMvcLinkBuilder
                                    .methodOn(AccessController::class.java)
                                    .login(LoginRequestDto("username", "password")),
                            ).withRel("login"),
                        )
                    ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(TokenErrorDto(exception.message).add(links))
                }

                else -> ResponseEntity.internalServerError().build()
            }
        }
    }
}
