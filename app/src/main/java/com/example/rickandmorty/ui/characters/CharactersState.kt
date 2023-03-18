package com.example.rickandmorty.ui.characters

import com.example.domain.characters.model.Characters

data class CharactersState(
    val characters: List<Characters> = emptyList(),
    val chipFilterAliveInstalled: Boolean = true,
    val chipFilterDeadInstalled: Boolean = true,
    val chipFilterUnknownInstalled: Boolean = true,
    val searchName: String = "",
    val dataLoading: Boolean = false
)
