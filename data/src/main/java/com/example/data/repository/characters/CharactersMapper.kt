package com.example.data.repository.characters

import com.example.data.database.characters.CharactersDb
import com.example.data.database.characters.LocationDb
import com.example.data.network.characters.model.CharactersResponse
import com.example.data.network.characters.model.LocationResponse
import com.example.domain.characters.model.Characters
import com.example.domain.characters.model.Location

class CharactersMapper {
    fun mapCharactersFromNetwork(charactersResponse: CharactersResponse): List<Characters> {
        val newList = mutableListOf<Characters>()
        for (i in charactersResponse.results.indices) {
            val newCharacters = Characters(
                id = charactersResponse.results[i].id,
                name = charactersResponse.results[i].name,
                status = charactersResponse.results[i].status,
                species = charactersResponse.results[i].species,
                type = charactersResponse.results[i].type,
                gender = charactersResponse.results[i].gender,
                origin = mapLocationFromNetworks(charactersResponse.results[i].origin),
                location = mapLocationFromNetworks(charactersResponse.results[i].location),
                image = charactersResponse.results[i].image,
                episode = charactersResponse.results[i].episode,
                url = charactersResponse.results[i].url,
                created = charactersResponse.results[i].created
            )
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
            val newCharacters = CharactersDb(
                id = charactersResponse.results[i].id,
                name = charactersResponse.results[i].name,
                status = charactersResponse.results[i].status,
                species = charactersResponse.results[i].species,
                type = charactersResponse.results[i].type,
                gender = charactersResponse.results[i].gender,
                origin = mapLocationToDb(charactersResponse.results[i].origin),
                location = mapLocationToDb(charactersResponse.results[i].location),
                image = charactersResponse.results[i].image,
                episode = charactersResponse.results[i].episode,
                url = charactersResponse.results[i].url,
                created = charactersResponse.results[i].created
            )
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
            val newEpisode = Characters(
                id = charactersDb[i].id,
                name = charactersDb[i].name,
                status = charactersDb[i].status,
                species = charactersDb[i].species,
                type = charactersDb[i].type,
                gender = charactersDb[i].gender,
                origin = mapLocatFromDb(charactersDb[i].origin),
                location = mapLocatFromDb(charactersDb[i].location),
                image = charactersDb[i].image,
                episode = charactersDb[i].episode,
                url = charactersDb[i].url,
                created = charactersDb[i].created
            )
            newList.add(newEpisode)
        }
        return newList
    }

    private fun mapLocatFromDb(location: LocationDb) = Location(
        name = location.name,
        url = location.url
    )
}