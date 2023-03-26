package com.example.rickandmorty.ui.place

import com.example.domain.characters.model.Characters

data class PlaceState(
    val dataLoading: Boolean = false,
    val residents: List<Characters> = emptyList()
)