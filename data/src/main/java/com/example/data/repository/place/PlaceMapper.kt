package com.example.data.repository.place

import com.example.data.network.characters.model.LocationResponse
import com.example.data.network.characters.model.PersonResponse
import com.example.domain.characters.model.Characters
import com.example.domain.characters.model.Location

class PlaceMapper {
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
}