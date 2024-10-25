package com.example.rest

import com.example.rest.businessLayer.UserRegisterUseCase
import com.example.rest.businessLayer.boundaries.UserInputBoundary
import com.example.rest.interfaceAdaptersLayer.persistence.UserRegisterDataSourceGatewayImpl
import com.example.rest.interfaceAdaptersLayer.security.UserSecurityImpl
import com.mongodb.ConnectionString
import com.mongodb.MongoClientSettings
import com.mongodb.MongoCredential
import com.mongodb.client.MongoClient
import com.mongodb.client.MongoClients
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.env.Environment
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.invoke
import org.springframework.security.web.SecurityFilterChain

@Configuration
class AppConfig {

    @Bean
    fun userInput(environment: Environment): UserInputBoundary {
        val userRegisterDataSourceGateway = UserRegisterDataSourceGatewayImpl(mongoTemplate(environment))
        val key = environment.getProperty("security.jwt.secret-key")
        assert(key != null)
        val security = UserSecurityImpl(key!!)
        val userRegisterUseCase = UserRegisterUseCase(userRegisterDataSourceGateway, security)
        return userRegisterUseCase
    }

    @Bean
    fun mongo(environment: Environment): MongoClient {
        val host = environment.getProperty("spring.data.mongodb.host") ?: "localhost"
        val port = environment.getProperty("spring.data.mongodb.port") ?: "27017"
        val user = environment.getProperty("spring.data.mongodb.username") ?: "name"
        val password = environment.getProperty("spring.data.mongodb.password") ?: "pwd"
        val connectionString = ConnectionString("mongodb://$host:$port")
        val mongoClientSettings = MongoClientSettings.builder()
            .credential(MongoCredential.createCredential(user, "admin", password.toCharArray()))
            .applyConnectionString(connectionString)
            .build()

        return MongoClients.create(mongoClientSettings)
    }

    @Bean
    @Throws(Exception::class)
    fun mongoTemplate(environment: Environment): MongoTemplate {
        val database = environment.getProperty("spring.data.mongodb.database") ?: "test"
        return MongoTemplate(mongo(environment), database)
    }

    @Bean
    @Throws(java.lang.Exception::class)
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        http {
            csrf { disable() }
            authorizeRequests {
                authorize(anyRequest, permitAll)
            }
        }
        return http.build()
    }
}
