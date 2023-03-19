package com.example.rickandmorty.ui.locations

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.domain.locations.LocationsInteractor
import com.example.rickandmorty.ui.locations.model.LocationsUiMapper

class LocationsViewModelFactory(
    val locationsInteractor: LocationsInteractor,
    val locationsUiMapper: LocationsUiMapper,
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return LocationsViewModel(
            locationsInteractor = locationsInteractor,
            locationsUiMapper = locationsUiMapper
        ) as T
    }
}