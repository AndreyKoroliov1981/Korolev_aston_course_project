package com.example.rickandmorty.ui.characters

import com.example.domain.characters.model.Characters

data class CharactersState(
    val characters: List<Characters> = emptyList()
)
