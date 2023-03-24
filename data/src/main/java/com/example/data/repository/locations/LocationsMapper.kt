package com.example.data.repository.locations

import com.example.data.network.locations.model.LocationsResponse
import com.example.domain.locations.model.Locations

class LocationsMapper {
    fun mapLocationsFromNetwork(locationsResponse: LocationsResponse): List<Locations> {
        val newList = mutableListOf<Locations>()
        for (i in locationsResponse.results.indices) {
            val newEpisode = Locations(
                id = locationsResponse.results[i].id,
                name = locationsResponse.results[i].name,
                type = locationsResponse.results[i].type,
                dimension = locationsResponse.results[i].dimension,
                residents = locationsResponse.results[i].residents,
                url = locationsResponse.results[i].url,
                created = locationsResponse.results[i].created
            )
            newList.add(newEpisode)
        }
        return newList
    }
}