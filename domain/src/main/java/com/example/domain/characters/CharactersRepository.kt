package com.example.domain.characters

import com.example.domain.characters.model.Characters
import com.example.domain.characters.model.Response

interface CharactersRepository {
    suspend fun getCharacters() : Response<List<Characters>>
}