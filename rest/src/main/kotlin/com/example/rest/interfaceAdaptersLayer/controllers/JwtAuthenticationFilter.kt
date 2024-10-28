package com.example.rest.interfaceAdaptersLayer.controllers

import com.example.rest.businessLayer.boundaries.UserInputBoundary
import com.example.rest.interfaceAdaptersLayer.controllers.dto.login.LoginRequestDto
import com.example.rest.interfaceAdaptersLayer.controllers.dto.token.TokenErrorDto
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import io.jsonwebtoken.io.IOException
import jakarta.servlet.FilterChain
import jakarta.servlet.ServletException
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.hateoas.mediatype.MessageResolver
import org.springframework.hateoas.mediatype.hal.CurieProvider
import org.springframework.hateoas.mediatype.hal.Jackson2HalModule
import org.springframework.hateoas.server.LinkRelationProvider
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo
import org.springframework.http.HttpStatus
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class JwtAuthenticationFilter(
    private val useCase: UserInputBoundary,
    private val linkRelationProvider: LinkRelationProvider,
    private val messageResolver: MessageResolver,
) : OncePerRequestFilter() {
    @Throws(ServletException::class, IOException::class)
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain,
    ) {
        val authHeader = request.getHeader("Authorization")

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response)
            return
        }

        try {
            val jwt = authHeader.substring(7)
            val check = useCase.checkUserToken(jwt)
            if (check.isFailure) {
                val links =
                    listOf(
                        linkTo(
                            WebMvcLinkBuilder
                                .methodOn(AccessController::class.java)
                                .login(LoginRequestDto("username", "password")),
                        ).withRel("login"),
                    )
                val error = TokenErrorDto(check.exceptionOrNull()?.message ?: "Invalid token").add(links)
                val mapper =
                    ObjectMapper()
                        .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                        .registerModule(Jackson2HalModule())
                mapper.setHandlerInstantiator(
                    Jackson2HalModule.HalHandlerInstantiator(
                        linkRelationProvider,
                        CurieProvider.NONE,
                        messageResolver,
                    ),
                )
                val json =
                    mapper
                        .writer()
                        .withDefaultPrettyPrinter()
                        .writeValueAsString(error)
                response.status = HttpStatus.UNAUTHORIZED.value()
                response.contentType = "application/hal+json"
                response.writer.write(json)
                return
            } else {
                val authentication =
                    UsernamePasswordAuthenticationToken(
                        check.getOrNull()?.user,
                        null,
                        emptyList(),
                    )
                request.setAttribute("user", check.getOrNull()?.user)
                request.setAttribute("role", check.getOrNull()?.role)
                SecurityContextHolder.getContext().authentication = authentication
                filterChain.doFilter(request, response)
            }
        } catch (exception: Exception) {
            response.status = 500
            response.writer.write("An error occurred")
        }
    }
}
