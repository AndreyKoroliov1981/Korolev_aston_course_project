package com.example.rickandmorty.ui.characters.recycler

import com.example.domain.characters.model.Characters

interface RVOnClickCharactersListeners {
    fun onClicked(item: Characters)
}