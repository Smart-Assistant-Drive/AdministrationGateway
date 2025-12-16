package com.example.rest.businessLayer

import com.example.rest.businessLayer.adapter.sign.SignModel
import com.example.rest.businessLayer.boundaries.SignsDataSourceGateway
import com.example.rest.businessLayer.boundaries.SignsInputBoundary

class SignsUseCase(
    private val signsDataSourceGateway: SignsDataSourceGateway,
) : SignsInputBoundary {
    override fun allSignsTypes(): Result<List<String>> = signsDataSourceGateway.allSignsTypes()

    override fun createSign(requestModel: SignModel): Result<SignModel> = signsDataSourceGateway.createSign(requestModel)

    override fun getSignsNear(
        idRoad: Int,
        direction: Int,
        latitude: Double,
        longitude: Double,
    ): Result<List<SignModel>> = signsDataSourceGateway.getSignsNear(idRoad, direction, latitude, longitude)

    override fun getSigns(
        idRoad: Int,
        direction: Int,
    ): Result<List<SignModel>> = signsDataSourceGateway.getSigns(idRoad, direction)
}
