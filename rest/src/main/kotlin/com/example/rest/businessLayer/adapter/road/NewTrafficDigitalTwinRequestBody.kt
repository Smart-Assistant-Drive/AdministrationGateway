package com.example.rest.businessLayer.adapter.road

data class NewTrafficDigitalTwinRequestBody(
    val roadId: String,
    val direction: Int,
    val link: String,
    val numLanes: Int,
    val numBlocks: Int
)
