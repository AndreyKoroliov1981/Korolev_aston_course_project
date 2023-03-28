package com.example.rickandmorty.ui.locations.model

import com.example.domain.locations.model.Locations
import javax.inject.Inject

class LocationsUiMapper
@Inject constructor() {
    fun mapLocationsFromDomain(location: Locations): LocationsUi {
        return with (location) {
            LocationsUi(
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