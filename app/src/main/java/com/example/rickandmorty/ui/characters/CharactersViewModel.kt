package com.example.rickandmorty.ui.characters

import com.example.domain.characters.CharactersInteractor
import com.example.domain.characters.model.Characters
import com.example.rickandmorty.common.BaseViewModel
import com.example.rickandmorty.common.IsEmptyFilter
import com.example.rickandmorty.common.IsErrorData
import com.example.rickandmorty.ui.personage.model.CharactersUi
import com.example.rickandmorty.ui.personage.model.CharactersUiMapper
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import javax.inject.Inject

const val STATUS_ALIVE = "Alive"
const val STATUS_DEAD = "Dead"
const val STATUS_UNKNOWN = "unknown"
const val DEBOUNCE_MILS = 300L

class CharactersViewModel
@Inject constructor(
    private val charactersInteractor: CharactersInteractor,
    private val charactersUiMapper: CharactersUiMapper,
) : BaseViewModel<CharactersState>(CharactersState()) {

    private var job: Job? = null

    init {
        refreshLoad()
    }

    fun refreshLoad() {
        updateState { copy(characters = emptyList()) }
        charactersInteractor.setStartPage()
        getCharacters()
    }

    fun mapCharactersToCharactersUI(item: Characters): CharactersUi {
        return charactersUiMapper.mapCharactersFromDomain(item)
    }

    fun getCharacters() {
        job?.cancel()
        job = launch {
            delay(DEBOUNCE_MILS)
            val newFilters = filters()
            if (newFilters.isEmpty()) {
                sideEffectSharedFlow.emit(IsEmptyFilter())
                return@launch
            }
            updateState { copy(dataLoading = true) }
            loadData(newFilters)
            updateState { copy(dataLoading = false) }
        }
    }

    private suspend fun loadData(filters: List<String>) {
        var isCheckedEndLoadFromApi = true
        while (isCheckedEndLoadFromApi) {
            val responseListCharacters =
                charactersInteractor.getCharacters(
                    searchName = state.searchName,
                    filters = filters
                )
            if (responseListCharacters.errorText == null) {
                val listCharacters = responseListCharacters.data ?: emptyList()
                val newCharacters = state.characters + listCharacters
                updateState { copy(characters = newCharacters) }
                isCheckedEndLoadFromApi = listCharacters.isEmpty()
            } else {
                isCheckedEndLoadFromApi = false
                if (responseListCharacters.data != null) {
                    val newCharacters = responseListCharacters.data!!
                    updateState { copy(characters = newCharacters) }
                }
                sideEffectSharedFlow.emit(IsErrorData(responseListCharacters.errorText!!))
            }
        }
    }

    private fun filters(): List<String> {
        val newFilters = mutableListOf<String>()
        if (state.chipFilterAliveInstalled) newFilters.add(STATUS_ALIVE)
        if (state.chipFilterDeadInstalled) newFilters.add(STATUS_DEAD)
        if (state.chipFilterUnknownInstalled) newFilters.add(STATUS_UNKNOWN)
        return newFilters
    }

    fun changeSearchViewQuery(query: String) {
        if (query != state.searchName) {
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