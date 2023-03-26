package com.example.data.repository.characters

import com.example.data.database.characters.CharactersDb
import com.example.data.database.characters.LocationDb
import com.example.data.network.characters.model.CharactersResponse
import com.example.data.network.characters.model.LocationResponse
import com.example.domain.characters.model.Characters
import com.example.domain.characters.model.Location
import javax.inject.Inject

class CharactersMapper
@Inject constructor() {
    fun mapCharactersFromNetwork(charactersResponse: CharactersResponse): List<Characters> {
        val newList = mutableListOf<Characters>()
        for (i in charactersResponse.results.indices) {
            val newCharacters = with(charactersResponse.results[i]) {
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

    fun mapCharactersToDb(charactersResponse: CharactersResponse): List<CharactersDb> {
        val newList = mutableListOf<CharactersDb>()
        for (i in charactersResponse.results.indices) {
            val newCharacters = with(charactersResponse.results[i]) {
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

    private fun mapLocationToDb(locationResponse: LocationResponse) = LocationDb(
        name = locationResponse.name,
        url = locationResponse.url
    )

    fun mapCharactersFromDb(charactersDb: List<CharactersDb>): List<Characters> {
        val newList = mutableListOf<Characters>()
        for (i in charactersDb.indices) {
            val newEpisode = with(charactersDb[i]) {
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