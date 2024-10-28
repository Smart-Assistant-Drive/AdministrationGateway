package com.example.rest

import com.example.rest.businessLayer.UserRegisterUseCase
import com.example.rest.businessLayer.boundaries.UserInputBoundary
import com.example.rest.interfaceAdaptersLayer.controllers.JwtAuthenticationFilter
import com.example.rest.interfaceAdaptersLayer.infrastructure.UserRegisterRemoteDataSource
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.env.Environment
import org.springframework.hateoas.mediatype.MessageResolver
import org.springframework.hateoas.server.LinkRelationProvider
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.invoke
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter

@EnableMethodSecurity(prePostEnabled = true)
@Configuration
class AppConfig {
    @Autowired
    private lateinit var linkRelationProvider: LinkRelationProvider

    @Autowired
    private lateinit var messageResolver: MessageResolver

    @Bean
    fun userInput(environment: Environment): UserInputBoundary {
        val host = environment.getProperty("access.service.host") ?: "http://localhost:4000"
        val userRegisterDataSourceGateway = UserRegisterRemoteDataSource(host)
        val userRegisterUseCase = UserRegisterUseCase(userRegisterDataSourceGateway)
        return userRegisterUseCase
    }

    @Bean
    @Throws(java.lang.Exception::class)
    fun filterChain(
        http: HttpSecurity,
        environment: Environment,
    ): SecurityFilterChain {
        http {
            csrf { disable() }
            addFilterBefore<BasicAuthenticationFilter>(
                JwtAuthenticationFilter(userInput(environment), linkRelationProvider, messageResolver),
            )
            sessionManagement { sessionCreationPolicy = SessionCreationPolicy.STATELESS }
            authorizeRequests {
                authorize("/login", permitAll)
                authorize(anyRequest, authenticated)
            }
        }
        return http.build()
    }
}
