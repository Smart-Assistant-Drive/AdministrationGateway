package com.example.rest.businessLayer.adapter.road

data class NewTrafficDigitalTwinRequest(
    val roadId: String,
    val direction: Int,
    val numLanes: Int,
    val numBlocks: Int
)

fun NewTrafficDigitalTwinRequest.toRequestBody(link: String): NewTrafficDigitalTwinRequestBody =
    NewTrafficDigitalTwinRequestBody(
        roadId,
        direction,
        link,
        numLanes,
        numBlocks
    )
