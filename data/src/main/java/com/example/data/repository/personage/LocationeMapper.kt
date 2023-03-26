package com.example.data.repository.personage

import com.example.data.database.locations.LocationsDb
import com.example.data.network.locations.model.LocationeResponse
import com.example.domain.locations.model.Locations
import javax.inject.Inject

class LocationeMapper
@Inject constructor() {
    fun mapLocationeFromNetwork(locationeResponse: LocationeResponse): Locations {
        return with(locationeResponse) {
            Locations(
                id = id,
                name = name,
                type = type,
                dimension = dimension,
                residents = residents,
                url = url,
                created = created
            )
        }
    }

    fun mapLocationsToDb(locations: Locations): LocationsDb {
        return with(locations) {
            LocationsDb(
                id = id,
                name = name,
                type = type,
                dimension = dimension,
                residents = residents,
                url = url,
                created = created
            )
        }
    }

    fun mapLocationsFromDb(locationsDb: LocationsDb?): Locations? {
        if (locationsDb == null) return null
        return with(locationsDb) {
            Locations(
                id = id,
                name = name,
                type = type,
                dimension = dimension,
                residents = residents,
                url = url,
                created = created
            )
        }
    }
}