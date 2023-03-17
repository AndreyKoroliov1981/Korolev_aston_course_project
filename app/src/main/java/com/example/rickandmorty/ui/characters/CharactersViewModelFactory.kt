package com.example.rickandmorty.ui.characters

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.domain.characters.CharactersInteractor
import com.example.rickandmorty.ui.personage.model.CharactersUIMapper

class CharactersViewModelFactory(
    val charactersInteractor: CharactersInteractor,
    val charactersUIMapper: CharactersUIMapper,
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CharactersViewModel(
            charactersInteractor = charactersInteractor,
            charactersUIMapper = charactersUIMapper
        ) as T
    }
}