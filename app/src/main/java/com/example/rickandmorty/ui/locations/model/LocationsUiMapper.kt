package com.example.rickandmorty.ui.locations.model

import com.example.domain.locations.model.Locations

class LocationsUiMapper {
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