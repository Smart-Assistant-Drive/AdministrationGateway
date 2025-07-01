package com.example.rest

import com.example.rest.businessLayer.SignsUseCase
import com.example.rest.businessLayer.UserRegisterUseCase
import com.example.rest.businessLayer.boundaries.SignsInputBoundary
import com.example.rest.businessLayer.boundaries.UserInputBoundary
import com.example.rest.interfaceAdaptersLayer.infrastructure.TrafficSignRemoteDataSource
import com.example.rest.interfaceAdaptersLayer.infrastructure.UserRegisterRemoteDataSource
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.env.Environment
import org.springframework.hateoas.mediatype.MessageResolver
import org.springframework.hateoas.server.LinkRelationProvider
/*import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.annotation.web.invoke*/

// @EnableMethodSecurity(prePostEnabled = true)
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
    fun signsInput(environment: Environment): SignsInputBoundary {
        val remote = environment.getProperty("sign.service.host") ?: "http://localhost:5000"
        val signsDataSourceGateway = TrafficSignRemoteDataSource(remote)
        return SignsUseCase(signsDataSourceGateway)
    }
}
