package com.example.data.repository.characters

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
}