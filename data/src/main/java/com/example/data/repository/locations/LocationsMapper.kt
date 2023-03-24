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

    fun mapLocationsToDb(locationsResponse: LocationsResponse): List<LocationsDb> {
        val newList = mutableListOf<LocationsDb>()
        for (i in locationsResponse.results.indices) {
            val newCharacters = LocationsDb(
                id = locationsResponse.results[i].id,
                name = locationsResponse.results[i].name,
                type = locationsResponse.results[i].type,
                dimension = locationsResponse.results[i].dimension,
                residents = locationsResponse.results[i].residents,
                url = locationsResponse.results[i].url,
                created = locationsResponse.results[i].created
            )
            newList.add(newCharacters)
        }
        return newList
    }

    fun mapLocationsFromDb(locationsDb: List<LocationsDb>): List<Locations> {
        val newList = mutableListOf<Locations>()
        for (i in locationsDb.indices) {
            val newCharacters = Locations(
                id = locationsDb[i].id,
                name = locationsDb[i].name,
                type = locationsDb[i].type,
                dimension = locationsDb[i].dimension,
                residents = locationsDb[i].residents,
                url = locationsDb[i].url,
                created = locationsDb[i].created
            )
            newList.add(newCharacters)
        }
        return newList
    }
}