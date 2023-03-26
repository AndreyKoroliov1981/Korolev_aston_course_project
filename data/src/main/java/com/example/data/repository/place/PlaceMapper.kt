package com.example.data.repository.place

import com.example.data.database.characters.CharactersDb
import com.example.data.database.characters.LocationDb
import com.example.data.network.characters.model.CharactersResponse
import com.example.data.network.characters.model.LocationResponse
import com.example.data.network.characters.model.PersonResponse
import com.example.domain.characters.model.Characters
import com.example.domain.characters.model.Location
import com.example.domain.locations.model.Locations
import javax.inject.Inject

class PlaceMapper
@Inject constructor() {
    fun mapPlaceFromNetwork(placeResponse: List<PersonResponse>): List<Characters> {
        val newList = mutableListOf<Characters>()
        for (i in placeResponse.indices) {
            val newCharacters = with(placeResponse[i]) {
                Characters(
                    id = id,
                    name = name,
                    status = status,
                    species = species,
                    type = type,
                    gender = gender,
                    origin = mapLocationFromNetworks(origin),
                    location = mapLocationFromNetworks(location),
                    image = image,
                    episode = episode,
                    url = url,
                    created = created
                )
            }
            newList.add(newCharacters)
        }
        return newList
    }

    private fun mapLocationFromNetworks(locationResponse: LocationResponse) = Location(
        name = locationResponse.name,
        url = locationResponse.url
    )

    fun mapCharactersToDb(characters: List<Characters>): List<CharactersDb> {
        val newList = mutableListOf<CharactersDb>()
        for (i in characters.indices) {
            val newCharacters = with(characters[i]) {
                CharactersDb(
                    id = id,
                name = name,
                status = status,
                species = species,
                type = type,
                gender = gender,
                origin = mapLocationToDb(origin),
                location = mapLocationToDb(location),
                image = image,
                episode = episode,
                url = url,
                created = created
                )
            }
            newList.add(newCharacters)
        }
        return newList
    }

    private fun mapLocationToDb(location: Location) = LocationDb(
        name = location.name,
        url = location.url
    )

    fun mapCharactersFromDb(charactersDb: List<CharactersDb>): List<Characters> {
        val newList = mutableListOf<Characters>()
        for (i in charactersDb.indices) {
            val newEpisode = with (charactersDb[i]) {
                Characters(
                    id = id,
                    name = name,
                    status = status,
                    species = species,
                    type = type,
                    gender = gender,
                    origin = mapLocatFromDb(origin),
                    location = mapLocatFromDb(location),
                    image = image,
                    episode = episode,
                    url = url,
                    created = created
                )
            }
            newList.add(newEpisode)
        }
        return newList
    }

    private fun mapLocatFromDb(location: LocationDb) = Location(
        name = location.name,
        url = location.url
    )
}