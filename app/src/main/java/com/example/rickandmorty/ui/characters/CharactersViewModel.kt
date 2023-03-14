package com.example.rickandmorty.ui.characters

import android.util.Log
import com.example.domain.characters.CharactersInteractor
import com.example.domain.characters.model.Characters
import com.example.rickandmorty.common.BaseViewModel
import com.example.rickandmorty.common.IsEmptyFilter
import com.example.rickandmorty.common.IsErrorData

class CharactersViewModel(private val charactersInteractor: CharactersInteractor) :
    BaseViewModel<CharactersState>(CharactersState()) {

    var isLoad = false

    init {
        getCharacters()
    }

    fun getCharacters() {
        launch {
            val newFilters = mutableListOf<String>()
            if (state.chipFilterAliveInstalled) newFilters.add("Alive")
            if (state.chipFilterDeadInstalled) newFilters.add("Dead")
            if (state.chipFilterUnknownInstalled) newFilters.add("unknown")
            if (newFilters.isNotEmpty()) {
                if (!isLoad) {
                    isLoad = true


                    var isCheckedEndLoadFromApi = true
                    while (isCheckedEndLoadFromApi) {
                        val responseListCharacters =
                            charactersInteractor.getCharacters(
                                searchName = state.searchName,
                                filters = newFilters
                            )
                        if (responseListCharacters.errorText == null) {
                            val listCharacters = responseListCharacters.data ?: emptyList()
                            isCheckedEndLoadFromApi = listCharacters.isEmpty()
                            val newCharacters = state.characters + listCharacters
                            updateState { copy(characters = newCharacters) }
                        } else {
                            isCheckedEndLoadFromApi = false
                            sideEffectSharedFlow.emit(IsErrorData(responseListCharacters.errorText!!))
                        }
                    }
                    isLoad = false
                }
            } else {
                sideEffectSharedFlow.emit(IsEmptyFilter())
            }
        }
    }

    fun clickSearchViewQuery(query: String) {
        Log.d("my_tag", "query = $query")
        updateState { copy(characters = emptyList(), searchName = query) }
        getCharacters()
    }

    fun changeSearchViewQuery(query: String) {
        Log.d("my_tag", "query = $query")
        updateState { copy(characters = emptyList(), searchName = query) }
        getCharacters()
    }

    fun clickChipFilterAlive() {
        val newState = !state.chipFilterAliveInstalled
        Log.d("my_tag", "chipFilterAliveInstalled = $newState")
        updateState { copy(characters = emptyList(), chipFilterAliveInstalled = newState) }
        getCharacters()
    }

    fun clickChipFilterDead() {
        val newState = !state.chipFilterDeadInstalled
        Log.d("my_tag", "chipFilterDeadInstalled = $newState")
        updateState { copy(characters = emptyList(), chipFilterDeadInstalled = newState) }
        getCharacters()
    }

    fun clickChipFilterUnknown() {
        val newState = !state.chipFilterUnknownInstalled
        Log.d("my_tag", "chipFilterUnknownInstalled = $newState")
        updateState { copy(characters = emptyList(), chipFilterUnknownInstalled = newState) }
        getCharacters()
    }

    fun onClickSendRequest() {
        getCharacters()
    }

}