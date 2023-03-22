package com.example.rickandmorty.ui.place

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.domain.characters.model.Characters
import com.example.domain.place.PlaceInteractor
import com.example.rickandmorty.common.BaseViewModel
import com.example.rickandmorty.common.IsErrorData
import com.example.rickandmorty.ui.characters.DEBOUNCE_MILS
import com.example.rickandmorty.ui.locations.model.LocationsUi
import com.example.rickandmorty.ui.personage.model.CharactersUi
import com.example.rickandmorty.ui.personage.model.CharactersUiMapper
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.delay

class PlaceViewModel @AssistedInject constructor(
    private val placeInteractor: PlaceInteractor,
    private val charactersUiMapper: CharactersUiMapper,
    @Assisted private val place: LocationsUi
) : BaseViewModel<PlaceState>(PlaceState()) {

    @AssistedFactory
    interface PlaceViewModelFactory {
        fun create(recordId: LocationsUi): PlaceViewModel
    }

    @Suppress("UNCHECKED_CAST")
    companion object {
        fun providesFactory(
            assistedFactory: PlaceViewModelFactory,
            place: LocationsUi
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return assistedFactory.create(place) as T
            }
        }
    }

    init {
        getResidents(place.residents)
    }

    fun refreshLoad(residents: List<String>) {
        updateState { copy(residents = emptyList()) }
        getResidents(residents)
    }

    fun mapCharactersToCharactersUI(item: Characters): CharactersUi {
        return charactersUiMapper.mapCharactersFromDomain(item)
    }

    private fun getResidents(residents: List<String>) {
        launch {
            updateState { copy(dataLoading = true) }
            val responseListResidents = placeInteractor.getResidents(residents)
            if (responseListResidents.errorText == null) {
                val listResidents = responseListResidents.data ?: emptyList()
                updateState { copy(residents = listResidents) }
            } else {
                if (responseListResidents.data != null) {
                    val newCharacters = responseListResidents.data!!
                    updateState { copy(residents = newCharacters) }
                }
                sideEffectSharedFlow.emit(IsErrorData(responseListResidents.errorText!!))
            }
            updateState { copy(dataLoading = false) }
        }
    }

    fun onClickSendRequest() {
        getResidents(place.residents)
    }
}