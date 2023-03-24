package com.example.data.network.locations.model

import com.example.data.network.characters.model.InfoResponse

data class LocationsResponse(
    val info : InfoResponse,
    val results: List<LocationeResponse>
)