package com.example.rest.interfaceAdaptersLayer.infrastructure

import com.example.rest.businessLayer.adapter.sign.SignModel
import com.example.rest.businessLayer.boundaries.SignsDataSourceGateway
import com.example.rest.interfaceAdaptersLayer.infrastructure.dto.createSign.SignResponseDto
import com.example.rest.interfaceAdaptersLayer.infrastructure.dto.createSign.toDto
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.web.client.RestClient

class TrafficSignRemoteDataSource(
    url: String,
) : SignsDataSourceGateway {
    private val restClient =
        RestClient
            .builder()
            .baseUrl(url)
            .build()

    override fun allSignsTypes(): Result<List<String>> =
        try {
            val response =
                restClient
                    .get()
                    .uri("/signs_types")
                    .accept(APPLICATION_JSON)
                    .retrieve()
                    .toEntity(object : ParameterizedTypeReference<List<String>>() {})
                    .body ?: emptyList()
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }

    override fun createSign(requestModel: SignModel): Result<SignModel> =
        try {
            val response =
                restClient
                    .post()
                    .uri("/create_sign")
                    .contentType(APPLICATION_JSON)
                    .body(requestModel.toDto(listOf()))
                    .accept(APPLICATION_JSON)
                    .retrieve()
                    .toEntity(object : ParameterizedTypeReference<SignResponseDto>() {})
                    .body!!
            Result.success(response.toModel())
        } catch (e: Exception) {
            Result.failure(e)
        }

    override fun getSignsNear(
        idRoad: String,
        direction: Int,
        latitude: Double,
        longitude: Double,
    ): Result<List<SignModel>> =
        try {
            val response =
                restClient
                    .get()
                    .uri(
                        "/signs/{idRoad}/{direction}/{latitude}/{longitude}",
                        idRoad,
                        direction,
                        latitude,
                        longitude,
                    ).accept(APPLICATION_JSON)
                    .retrieve()
                    .toEntity(object : ParameterizedTypeReference<List<SignResponseDto>>() {})
                    .body ?: emptyList()
            Result.success(response.map { it.toModel() })
        } catch (e: Exception) {
            Result.failure(e)
        }

    override fun getSigns(
        idRoad: String,
        direction: Int,
    ): Result<List<SignModel>> =
        try {
            val response =
                restClient
                    .get()
                    .uri(
                        "/signs/{idRoad}/{direction}",
                        idRoad,
                        direction,
                    ).accept(APPLICATION_JSON)
                    .retrieve()
                    .toEntity(object : ParameterizedTypeReference<List<SignResponseDto>>() {})
                    .body ?: emptyList()
            Result.success(response.map { it.toModel() })
        } catch (e: Exception) {
            Result.failure(e)
        }

    private fun SignResponseDto.toModel(): SignModel =
        SignModel(
            type = type,
            category = category,
            idRoad = idRoad,
            direction = direction,
            latitude = latitude,
            longitude = longitude,
            lanes = lanes,
            speedLimit = speedLimit,
            unit = unit,
        )
}
