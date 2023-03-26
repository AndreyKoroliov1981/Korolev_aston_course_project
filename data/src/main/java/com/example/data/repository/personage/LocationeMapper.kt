package com.example.data.repository.personage

import com.example.data.network.locations.model.LocationeResponse
import com.example.domain.locations.model.Locations

class LocationeMapper {
    fun mapLocationeFromNetwork(locationeResponse: LocationeResponse): Locations {
        return Locations(
            id = locationeResponse.id,
            name = locationeResponse.name,
            type = locationeResponse.type,
            dimension = locationeResponse.dimension,
            residents = locationeResponse.residents,
            url = locationeResponse.url,
            created = locationeResponse.created
        )
    }
}