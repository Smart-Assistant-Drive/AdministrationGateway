package com.example.rest.businessLayer.adapter.semaphore

data class SemaphoreResponseModel(
	val link: String,
	val status: String,
	val road: String,
	val direction: Int,
	val position: Pair<Float, Float>
)