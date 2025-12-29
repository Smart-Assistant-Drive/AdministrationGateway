package com.example.rest.businessLayer.adapter.road.junction

import com.example.rest.domainLayer.OutgoingRoad

data class JunctionUpdateModel(
    val newOutgoingRoads: ArrayList<OutgoingRoad>,
    val newJunctionType: Int,
)
