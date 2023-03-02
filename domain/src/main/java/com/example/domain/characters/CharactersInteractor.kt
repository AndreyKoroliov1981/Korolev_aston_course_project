package com.example.domain.characters

import com.example.domain.characters.model.Characters
import com.example.domain.characters.model.Response

interface CharactersInteractor {
    suspend fun getAllCharacters() : Response<List<Characters>>
}