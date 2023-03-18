package com.example.domain.characters

import com.example.domain.characters.model.Characters
import com.example.domain.characters.model.Response

interface CharactersInteractor {
    suspend fun getCharacters(searchName: String, filters: List<String>) : Response<List<Characters>>

    fun setStartPage()
}