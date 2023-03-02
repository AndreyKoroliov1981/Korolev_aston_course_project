package com.example.rickandmorty.ui.characters

import android.util.Log
import com.example.domain.characters.CharactersInteractor
import com.example.rickandmorty.common.BaseViewModel

class CharactersViewModel(private val charactersInteractor: CharactersInteractor) :
    BaseViewModel<CharactersState>(CharactersState()) {

    init {
        getCharacters()
    }

    private fun getCharacters() {
        launch {
            Log.d("my_tag", "getCharacters start")
            val responseListCharacters = charactersInteractor.getAllCharacters()
            if (responseListCharacters.errorText == null) {
                val listCharacters = responseListCharacters.data ?: emptyList()
                updateState { copy(characters = listCharacters) }
                Log.d("my_tag", "listCharacters = ${listCharacters.joinToString()}")
            } else {
                Log.d("my_tag", "error read listCharacters = ${responseListCharacters.errorText}")
            }

        }
    }

}