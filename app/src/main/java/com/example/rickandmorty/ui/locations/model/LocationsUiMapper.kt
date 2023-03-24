package com.example.rickandmorty.ui.locations.model

import com.example.domain.locations.model.Locations
import javax.inject.Inject

class LocationsUiMapper
@Inject constructor() {
    fun mapLocationsFromDomain(location: Locations): LocationsUi {
        return LocationsUi(
            id = location.id,
            name = location.name,
            type = location.type,
            dimension = location.dimension,
            residents = location.residents,
            url = location.url,
            created = location.created
        )
    }
}