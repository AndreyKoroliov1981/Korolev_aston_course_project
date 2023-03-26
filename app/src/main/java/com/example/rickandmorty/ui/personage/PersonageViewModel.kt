package com.example.rickandmorty.ui.personage

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.domain.episodes.model.Episode
import com.example.domain.locations.model.Locations
import com.example.domain.personage.PersonageInteractor
import com.example.rickandmorty.common.BaseViewModel
import com.example.rickandmorty.common.IsErrorData
import com.example.rickandmorty.ui.episodes.model.EpisodesUi
import com.example.rickandmorty.ui.episodes.model.EpisodesUiMapper
import com.example.rickandmorty.ui.locations.model.LocationsUi
import com.example.rickandmorty.ui.locations.model.LocationsUiMapper
import com.example.rickandmorty.ui.personage.model.CharactersUi
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject

class PersonageViewModel @AssistedInject constructor(
    private val personageInteractor: PersonageInteractor,
    private val episodesUiMapper: EpisodesUiMapper,
    private val locationsUiMapper: LocationsUiMapper,
    @Assisted private val personage: CharactersUi
) : BaseViewModel<PersonageState>(PersonageState()) {

    @AssistedFactory
    interface PersonageViewModelFactory {
        fun create(recordId: CharactersUi): PersonageViewModel
    }

    @Suppress("UNCHECKED_CAST")
    companion object {
        fun providesFactory(
            assistedFactory: PersonageViewModelFactory,
            personage: CharactersUi
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return assistedFactory.create(personage) as T
            }
        }
    }

    init {
        refreshLoad()
    }

    fun refreshLoad() {
        getEpisodes(personage.episode)
        getLocations()
    }

    private fun getLocations() {
        launch {
            updateState { copy(dataLoading = true) }
            val responseLocations = personageInteractor.getLocations(personage.location.url)
            if (responseLocations.errorText == null) {
                val locations = responseLocations.data
                updateState { copy(location = locations) }
            } else {
                Log.d("my_tag", "error ${responseLocations.errorText!!}")
                updateState { copy(location = null) }
                sideEffectSharedFlow.emit(IsErrorData(responseLocations.errorText!!))
            }

            val responseOrigins = personageInteractor.getLocations(personage.origin.url)
            if (responseOrigins.errorText == null) {
                val origins = responseOrigins.data
                updateState { copy(origin = origins) }
            } else {
                Log.d("my_tag", "error ${responseOrigins.errorText!!}")
                updateState { copy(origin = null) }
                sideEffectSharedFlow.emit(IsErrorData(responseOrigins.errorText!!))
            }
            updateState { copy(dataLoading = false) }
        }
    }

    fun getEpisodes(episodes: List<String>) {
        launch {
            updateState { copy(dataLoading = true) }
            val responseListEpisode = personageInteractor.getEpisodes(episodes)
            if (responseListEpisode.errorText == null) {
                val listEpisodes = responseListEpisode.data ?: emptyList()
                updateState { copy(episodes = listEpisodes) }
            } else {
                sideEffectSharedFlow.emit(IsErrorData(responseListEpisode.errorText!!))
            }
            updateState { copy(dataLoading = false) }
        }
    }

    fun onClickEpisode(item: Episode): EpisodesUi {
        return episodesUiMapper.mapEpisodesFromDomain(item)
    }

    fun onClickLocations(): LocationsUi? {
        return state.location?.let { locationsUiMapper.mapLocationsFromDomain(it) }
    }

    fun onClickOrigins(): LocationsUi? {
        return state.origin?.let { locationsUiMapper.mapLocationsFromDomain(it) }
    }

    fun onClickSendRequest() {
        refreshLoad()
    }

}