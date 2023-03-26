package com.example.rickandmorty.ui.series

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.domain.characters.model.Characters
import com.example.domain.series.SeriesInteractor
import com.example.rickandmorty.common.BaseViewModel
import com.example.rickandmorty.common.IsErrorData
import com.example.rickandmorty.ui.characters.DEBOUNCE_MILS
import com.example.rickandmorty.ui.episodes.model.EpisodesUi
import com.example.rickandmorty.ui.locations.model.LocationsUi
import com.example.rickandmorty.ui.personage.model.CharactersUi
import com.example.rickandmorty.ui.personage.model.CharactersUiMapper
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.delay

class SeriesViewModel @AssistedInject constructor(
    private val seriesInteractor: SeriesInteractor,
    private val charactersUiMapper: CharactersUiMapper,
    @Assisted private val series: EpisodesUi
) : BaseViewModel<SeriesState>(SeriesState()) {

    @AssistedFactory
    interface PlaceViewModelFactory {
        fun create(recordId: EpisodesUi): SeriesViewModel
    }

    @Suppress("UNCHECKED_CAST")
    companion object {
        fun providesFactory(
            assistedFactory: PlaceViewModelFactory,
            series: EpisodesUi
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return assistedFactory.create(series) as T
            }
        }
    }

    init {
        getResidents(series.characters)
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
            val responseListResidents = seriesInteractor.getResidents(residents)
            if (responseListResidents.errorText == null) {
                val listResidents = responseListResidents.data ?: emptyList()
                updateState { copy(residents = listResidents) }
            } else {
                sideEffectSharedFlow.emit(IsErrorData(responseListResidents.errorText!!))
            }
            updateState { copy(dataLoading = false) }
        }
    }

    fun onClickSendRequest() {
        getResidents(series.characters)
    }
}