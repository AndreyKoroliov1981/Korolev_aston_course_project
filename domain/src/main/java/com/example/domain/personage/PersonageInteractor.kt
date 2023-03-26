package com.example.domain.personage

import com.example.domain.characters.model.Response
import com.example.domain.episodes.model.Episode
import com.example.domain.locations.model.Locations

interface PersonageInteractor {
    suspend fun getEpisodes(episodes: List<String>): Response<List<Episode>>

    suspend fun getLocations(locations: String): Response<Locations>
}