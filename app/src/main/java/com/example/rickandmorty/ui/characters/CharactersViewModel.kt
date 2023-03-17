package com.example.rickandmorty.ui.characters

import android.util.Log
import com.example.domain.characters.CharactersInteractor
import com.example.domain.characters.model.Characters
import com.example.rickandmorty.common.BaseViewModel
import com.example.rickandmorty.common.IsEmptyFilter
import com.example.rickandmorty.common.IsErrorData
import com.example.rickandmorty.ui.personage.model.CharactersUI
import com.example.rickandmorty.ui.personage.model.CharactersUIMapper
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay

class CharactersViewModel(
    private val charactersInteractor: CharactersInteractor,
    private val charactersUIMapper: CharactersUIMapper,
) :
    BaseViewModel<CharactersState>(CharactersState()) {

    private var job: Job? = null
    private var isLoad = false

    init {
        Log.d("my_tag","start init")
        charactersInteractor.setStartPage()
        getCharacters()
    }

    fun mapCharactersToCharactersUI(item: Characters): CharactersUI {
        return charactersUIMapper.mapCharactersFromDomain(item)
    }

    fun getCharacters() {
        Log.d("my_tag","getCharacters")
        job?.cancel()
        job = launch {
            delay(300)
            val newFilters = mutableListOf<String>()
            if (state.chipFilterAliveInstalled) newFilters.add("Alive")
            if (state.chipFilterDeadInstalled) newFilters.add("Dead")
            if (state.chipFilterUnknownInstalled) newFilters.add("unknown")
            if (newFilters.isNotEmpty()) {
                updateState { copy(dataLoading = true) }
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
                updateState { copy(dataLoading = false) }
            } else {
                sideEffectSharedFlow.emit(IsEmptyFilter())
            }
        }
    }

    fun changeSearchViewQuery(query: String) {
        if (query != state.searchName ) {
            updateState { copy(characters = emptyList(), searchName = query) }
            getCharacters()
        }
    }

    fun clickChipFilterAlive() {
        val newState = !state.chipFilterAliveInstalled
        updateState { copy(characters = emptyList(), chipFilterAliveInstalled = newState) }
        getCharacters()
    }

    fun clickChipFilterDead() {
        val newState = !state.chipFilterDeadInstalled
        updateState { copy(characters = emptyList(), chipFilterDeadInstalled = newState) }
        getCharacters()
    }

    fun clickChipFilterUnknown() {
        val newState = !state.chipFilterUnknownInstalled
        updateState { copy(characters = emptyList(), chipFilterUnknownInstalled = newState) }
        getCharacters()
    }

    fun onClickSendRequest() {
        getCharacters()
    }

}