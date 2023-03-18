package com.example.rickandmorty.ui.characters

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.domain.characters.CharactersInteractor
import com.example.rickandmorty.ui.personage.model.CharactersUiMapper

class CharactersViewModelFactory(
    val charactersInteractor: CharactersInteractor,
    val charactersUiMapper: CharactersUiMapper,
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CharactersViewModel(
            charactersInteractor = charactersInteractor,
            charactersUiMapper = charactersUiMapper
        ) as T
    }
}