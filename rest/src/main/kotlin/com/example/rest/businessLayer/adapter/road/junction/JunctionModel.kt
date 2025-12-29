package com.example.rest.businessLayer.adapter.road.junction

import com.example.rest.domainLayer.Coordinate
import com.example.rest.domainLayer.OutgoingRoad

data class JunctionModel(
    val outgoingRoads: ArrayList<OutgoingRoad>,
    val junctionType: Int,
    val position: Coordinate,
)
