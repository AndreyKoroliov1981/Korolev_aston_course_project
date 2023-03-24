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
            val newCharacters = Characters(
                id = placeResponse[i].id,
                name = placeResponse[i].name,
                status = placeResponse[i].status,
                species = placeResponse[i].species,
                type = placeResponse[i].type,
                gender = placeResponse[i].gender,
                origin = mapLocationFromNetworks(placeResponse[i].origin),
                location = mapLocationFromNetworks(placeResponse[i].location),
                image = placeResponse[i].image,
                episode = placeResponse[i].episode,
                url = placeResponse[i].url,
                created = placeResponse[i].created
            )
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
            val newCharacters = CharactersDb(
                id = characters[i].id,
                name = characters[i].name,
                status = characters[i].status,
                species = characters[i].species,
                type = characters[i].type,
                gender = characters[i].gender,
                origin = mapLocationToDb(characters[i].origin),
                location = mapLocationToDb(characters[i].location),
                image = characters[i].image,
                episode = characters[i].episode,
                url = characters[i].url,
                created = characters[i].created
            )
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