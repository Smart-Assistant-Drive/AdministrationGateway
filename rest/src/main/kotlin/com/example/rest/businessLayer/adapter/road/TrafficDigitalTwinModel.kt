package com.example.rest.businessLayer.adapter.road

data class TrafficDigitalTwinModel(
	val roadId: String,
	val direction: Int,
	val link: String,
	val numLanes: Int,
	val numBlocks: Int,
	val idDt: String
)