package com.example.rest

import com.example.rest.businessLayer.RoadUseCase
import com.example.rest.businessLayer.boundaries.RoadInputBoundary
import com.example.rest.interfaceAdaptersLayer.infrastructure.RoadRemoteDataSource
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.env.Environment

@Configuration
class RoadConfig {
	@Bean
	fun roadInputBoundary(environment: Environment): RoadInputBoundary {
		val remote = environment.getProperty("road.service.host") ?: "http://localhost:6000"
		val signsDataSourceGateway = RoadRemoteDataSource(remote)
		return RoadUseCase(signsDataSourceGateway)
	}
}