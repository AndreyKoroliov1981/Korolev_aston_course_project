package com.example.rickandmorty.ui.characters

import android.util.Log
import com.example.domain.characters.CharactersInteractor
import com.example.rickandmorty.common.BaseViewModel

class CharactersViewModel(private val charactersInteractor: CharactersInteractor) :
    BaseViewModel<CharactersState>(CharactersState()) {

    var isLoad = false

    init {
        getCharacters()
    }

    fun getCharacters() {
        launch {
            if (!isLoad) {
                isLoad = true
                val responseListCharacters = charactersInteractor.getCharacters()
                if (responseListCharacters.errorText == null) {
                    val listCharacters = responseListCharacters.data ?: emptyList()
                    val newCharacters = state.characters + listCharacters
                    updateState { copy(characters = newCharacters) }
                    isLoad = false
                } else {
                    Log.d(
                        "my_tag",
                        "error read listCharacters = ${responseListCharacters.errorText}"
                    )
                    isLoad = false
                }

            }
        }
    }

}