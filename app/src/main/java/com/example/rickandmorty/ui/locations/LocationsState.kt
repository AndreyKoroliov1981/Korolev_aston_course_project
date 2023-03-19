package com.example.rickandmorty.ui.locations

import com.example.domain.locations.model.Locations

data class LocationsState(
    val locations: List<Locations> = emptyList(),
    val searchName: String = "",
    val searchType: String = "",
    val dataLoading: Boolean = false
)
