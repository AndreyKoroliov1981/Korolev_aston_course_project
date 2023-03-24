package com.example.data.repository.personage

import com.example.data.database.locations.LocationsDb
import com.example.data.network.locations.model.LocationeResponse
import com.example.domain.locations.model.Locations
import javax.inject.Inject

class LocationeMapper
@Inject constructor(){
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

    fun mapLocationsToDb(locations: Locations): LocationsDb {
        return LocationsDb(
            id = locations.id,
            name = locations.name,
            type = locations.type,
            dimension = locations.dimension,
            residents = locations.residents,
            url = locations.url,
            created = locations.created
        )
    }

    fun mapLocationsFromDb(locationsDb: LocationsDb?): Locations? {
        if (locationsDb == null) return null
        return Locations(
            id = locationsDb.id,
            name = locationsDb.name,
            type = locationsDb.type,
            dimension = locationsDb.dimension,
            residents = locationsDb.residents,
            url = locationsDb.url,
            created = locationsDb.created
        )
    }
}