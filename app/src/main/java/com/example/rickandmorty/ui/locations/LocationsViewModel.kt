package com.example.rickandmorty.ui.locations

import com.example.domain.locations.LocationsInteractor
import com.example.domain.locations.model.Locations
import com.example.rickandmorty.common.BaseViewModel
import com.example.rickandmorty.common.IsErrorData
import com.example.rickandmorty.ui.characters.DEBOUNCE_MILS
import com.example.rickandmorty.ui.locations.model.LocationsUi
import com.example.rickandmorty.ui.locations.model.LocationsUiMapper
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay

class LocationsViewModel (
    private val locationsInteractor: LocationsInteractor,
    private val locationsUiMapper: LocationsUiMapper,
) : BaseViewModel<LocationsState>(LocationsState()) {

    private var job: Job? = null

    init {
        refreshLoad()
    }

    fun refreshLoad() {
        updateState { copy(locations = emptyList()) }
        locationsInteractor.setStartPage()
        getLocations()
    }

    fun mapLocationToEpisodesUi(item: Locations): LocationsUi {
        return locationsUiMapper.mapLocationsFromDomain(item)
    }

    fun getLocations() {
        job?.cancel()
        job = launch {
            delay(DEBOUNCE_MILS)
            updateState { copy(dataLoading = true) }
            var isCheckedEndLoadFromApi = true
            while (isCheckedEndLoadFromApi) {
                val responseListEpisodes =
                    locationsInteractor.getLocations(
                        searchName = state.searchName,
                        searchEpisodes = state.searchType
                    )
                if (responseListEpisodes.errorText == null) {
                    val listEpisodes = responseListEpisodes.data ?: emptyList()
                    isCheckedEndLoadFromApi = listEpisodes.isEmpty()
                    val newEpisodes = state.locations + listEpisodes
                    updateState { copy(locations = newEpisodes) }
                } else {
                    isCheckedEndLoadFromApi = false
                    sideEffectSharedFlow.emit(IsErrorData(responseListEpisodes.errorText!!))
                }
                updateState { copy(dataLoading = false) }
            }
        }
    }

    fun changeSearchViewQueryName(query: String) {
        if (query != state.searchName) {
            updateState { copy(locations = emptyList(), searchName = query) }
            getLocations()
        }
    }

    fun changeSearchViewQueryEpisode(query: String) {
        if (query != state.searchType) {
            updateState { copy(locations = emptyList(), searchType = query) }
            getLocations()
        }
    }

    fun onClickSendRequest() {
        getLocations()
    }

}