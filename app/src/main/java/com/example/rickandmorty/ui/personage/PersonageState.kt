package com.example.rickandmorty.ui.personage

import com.example.domain.episodes.model.Episode

data class PersonageState (
    val dataLoading: Boolean = false,
    val episodes: List<Episode> = emptyList(),
)