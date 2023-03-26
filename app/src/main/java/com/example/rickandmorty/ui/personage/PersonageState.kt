package com.example.rickandmorty.ui.personage

import com.example.domain.episodes.model.Episode
import com.example.domain.locations.model.Locations

data class PersonageState (
    val dataLoading: Boolean = false,
    val episodes: List<Episode> = emptyList(),
    val location: Locations? = null,
    val origin: Locations? = null,
)