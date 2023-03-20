package com.example.rickandmorty.ui.series

import com.example.domain.characters.model.Characters

data class SeriesState(
    val dataLoading: Boolean = false,
    val residents: List<Characters> = emptyList()
)