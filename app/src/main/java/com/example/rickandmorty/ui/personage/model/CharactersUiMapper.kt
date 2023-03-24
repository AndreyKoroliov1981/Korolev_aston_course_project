package com.example.rickandmorty.ui.personage.model

import com.example.domain.characters.model.Characters
import com.example.domain.characters.model.Location
import javax.inject.Inject

class CharactersUiMapper
@Inject constructor(){
    fun mapCharactersFromDomain(characters: Characters): CharactersUi {
        return CharactersUi(
            id = characters.id,
            name = characters.name,
            status = characters.status,
            species = characters.species,
            type = characters.type,
            gender = characters.gender,
            origin = mapLocationFromDomain(characters.origin),
            location = mapLocationFromDomain(characters.location),
            image = characters.image,
            episode = characters.episode,
            url = characters.url,
            created = characters.created
        )
    }

    private fun mapLocationFromDomain(location: Location) = LocationUi(
        name = location.name,
        url = location.url
    )
}