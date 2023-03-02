package com.example.domain.characters

import com.example.domain.characters.model.Characters
import com.example.domain.characters.model.Response

class CharactersInteractorImpl(private val charactersRepository: CharactersRepository): CharactersInteractor {
    override suspend fun getAllCharacters(): Response<List<Characters>> {
        return charactersRepository.getAllCharacters(0)
    }
}