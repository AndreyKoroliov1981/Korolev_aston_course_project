package com.example.rickandmorty.ui.locations.recycler

import com.example.domain.episodes.model.Episode
import com.example.domain.locations.model.Locations

interface RVOnClickLocationsListeners {
    fun onClicked(item: Locations)
}