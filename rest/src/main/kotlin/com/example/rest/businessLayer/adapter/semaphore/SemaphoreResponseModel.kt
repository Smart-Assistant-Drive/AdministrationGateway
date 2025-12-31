package com.example.rest.businessLayer.adapter.semaphore

import com.example.rest.domainLayer.Coordinate

data class SemaphoreResponseModel(
	val link: String,
	val status: String,
	val road: String,
	val direction: Int,
	val position: Coordinate,
    val id: String,
    val idIndex: Int
)