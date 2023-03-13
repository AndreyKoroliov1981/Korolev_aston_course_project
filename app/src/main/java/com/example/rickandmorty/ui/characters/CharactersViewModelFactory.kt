package com.example.rickandmorty.ui.characters

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.domain.characters.CharactersInteractor

class CharactersViewModelFactory (val charactersInteractor: CharactersInteractor): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CharactersViewModel(charactersInteractor = charactersInteractor) as T
    }
}