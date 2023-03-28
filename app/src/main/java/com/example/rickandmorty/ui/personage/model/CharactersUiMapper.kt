package com.example.rickandmorty.ui.personage.model

import com.example.domain.characters.model.Characters
import com.example.domain.characters.model.Location
import javax.inject.Inject

class CharactersUiMapper
@Inject constructor(){
    fun mapCharactersFromDomain(characters: Characters): CharactersUi {
        return with(characters) {
            CharactersUi(
                id = id,
                name = name,
                status = status,
                species = species,
                type = type,
                gender = gender,
                origin = mapLocationFromDomain(origin),
                location = mapLocationFromDomain(location),
                image = image,
                episode = episode,
                url = url,
                created = created
            )
        }
    }

    private fun mapLocationFromDomain(location: Location) = LocationUi(
        name = location.name,
        url = location.url
    )
}