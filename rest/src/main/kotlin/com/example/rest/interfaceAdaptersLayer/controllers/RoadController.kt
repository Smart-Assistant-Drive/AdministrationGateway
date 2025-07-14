package com.example.rest.interfaceAdaptersLayer.controllers

import com.example.rest.businessLayer.boundaries.RoadInputBoundary
import com.example.rest.interfaceAdaptersLayer.controllers.dto.road.RoadRequestDto
import com.example.rest.interfaceAdaptersLayer.controllers.dto.road.toDto
import com.example.rest.interfaceAdaptersLayer.controllers.dto.road.toModel
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.enums.ParameterIn
import io.swagger.v3.oas.annotations.media.ArraySchema
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder
import org.springframework.http.HttpEntity
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class RoadController(
	val roadInputBoundary: RoadInputBoundary
) {
	@GetMapping("/road/{id}")
	@Operation(
		summary = "Get existing road",
		description = "Get existing road with a specific id",
		parameters = [
			Parameter(
				name = "id",
				description = "Road id to be obtained",
				`in` = ParameterIn.PATH
			)
		],
		responses = [
			ApiResponse(
				responseCode = "200",
				description = "Signs types retrieved successfully",
				content =
				[
					Content(
						mediaType = "application/json",
						array =
						ArraySchema(
							schema = Schema(implementation = String::class),
						),
					),
				],
			),
			ApiResponse(
				responseCode = "500",
				description = "Internal server error",
				content = [Content(mediaType = "application/json")],
			),
		],
	)
	fun getRoad(@PathVariable id: String): HttpEntity<Any> {
		val result = roadInputBoundary.getRoad(id)
		return if (result.isSuccess) {
			ResponseEntity(result.getOrNull()!!, HttpStatus.OK)
		} else {
			ResponseEntity.internalServerError().build()
		}
	}

	@PostMapping("/road")
	@Operation(
		summary = "Add new road",
		description = "Add new road with a specific id, an alphanumeric number, a name and a category",
		requestBody = io.swagger.v3.oas.annotations.parameters.RequestBody(
			content = [
				Content(
					mediaType = "application/json",
					schema = Schema(implementation = RoadRequestDto::class)
				)
			],
			required = true
		),
		responses = [
			ApiResponse(
				responseCode = "201",
				description = "Road created successfully",
				content = [Content(mediaType = "application/json", schema = Schema(implementation = RoadRequestDto::class))],
			),
			ApiResponse(
				responseCode = "400",
				description = "Bad request",
				content = [Content(mediaType = "application/json")],
			),
			ApiResponse(
				responseCode = "409",
				description = "Conflict",
				content = [Content(mediaType = "application/json")],
			),
		],
	)
	fun addRoad(
		@RequestBody requestModel: RoadRequestDto,
	): HttpEntity<Any> {
		val result = roadInputBoundary.addRoad(requestModel.toModel())
		return if (result.isSuccess) {
			val links =
				listOf(
					WebMvcLinkBuilder.linkTo(
						WebMvcLinkBuilder.methodOn(RoadController::class.java).addRoad(requestModel)
					)
						.withSelfRel(),
				)
			ResponseEntity(result.getOrNull()!!.toDto(links), HttpStatus.CREATED)
		} else {
			when (val exception = result.exceptionOrNull()) {
				else -> ResponseEntity.internalServerError().build()
			}
		}
	}

	@PutMapping("/road/{id}")
	@Operation(
		summary = "Change existing road",
		description = "Change existing road with a specific id",
		requestBody = io.swagger.v3.oas.annotations.parameters.RequestBody(
			content = [
				Content(
					mediaType = "application/json",
					schema = Schema(implementation = RoadRequestDto::class)
				)
			],
			required = true
		),
		parameters = [
			Parameter(
				name = "id",
				description = "Road id to be obtained",
				`in` = ParameterIn.PATH
			)
		],
		responses = [
			ApiResponse(
				responseCode = "200",
				description = "Road changed successfully",
				content = [
					Content(
						mediaType = "application/json",
						schema = Schema(implementation = RoadRequestDto::class)
					)
				]
			),
			ApiResponse(
				responseCode = "400",
				description = "Invalid road, cannot update a non-existing road",
				content = [Content()]
			),
			ApiResponse(
				responseCode = "404",
				description = "Valid road not found",
				content = [Content()]
			),
			ApiResponse(
				responseCode = "500",
				description = "Internal server error",
				content = [Content()]
			)
		]
	)
	fun updateRoad(@PathVariable id: String, @RequestBody roadUpdateModel: RoadRequestDto): HttpEntity<Any> {
		val result = roadInputBoundary.updateRoad(id, roadUpdateModel.toModel())
		return if (result.isSuccess) {
			val links =
				listOf(
					WebMvcLinkBuilder.linkTo(
						WebMvcLinkBuilder.methodOn(RoadController::class.java).addRoad(roadUpdateModel)
					)
						.withSelfRel(),
				)
			ResponseEntity(result.getOrNull()!!.toDto(links), HttpStatus.CREATED)
		} else {
			when (val exception = result.exceptionOrNull()) {
				else -> ResponseEntity.internalServerError().build()
			}
		}
	}
}