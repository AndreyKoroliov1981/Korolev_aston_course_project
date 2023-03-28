package com.example.data.repository.locations

import com.example.data.database.characters.CharactersDb
import com.example.data.database.locations.LocationsDb
import com.example.data.network.locations.model.LocationsResponse
import com.example.domain.locations.model.Locations
import java.lang.annotation.Inherited
import javax.inject.Inject

class LocationsMapper
@Inject constructor() {
    fun mapLocationsFromNetwork(locationsResponse: LocationsResponse): List<Locations> {
        val newList = mutableListOf<Locations>()
        for (i in locationsResponse.results.indices) {
            val newEpisode = with(locationsResponse.results[i]) {
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
            newList.add(newEpisode)
        }
        return newList
    }

    fun mapLocationsToDb(locationsResponse: LocationsResponse): List<LocationsDb> {
        val newList = mutableListOf<LocationsDb>()
        for (i in locationsResponse.results.indices) {
            val newCharacters = with(locationsResponse.results[i]) {
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
            newList.add(newCharacters)
        }
        return newList
    }

    fun mapLocationsFromDb(locationsDb: List<LocationsDb>): List<Locations> {
        val newList = mutableListOf<Locations>()
        for (i in locationsDb.indices) {
            val newCharacters = with(locationsDb[i]) {
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
            newList.add(newCharacters)
        }
        return newList
    }
}