package com.example.domain.personage

import com.example.domain.characters.model.Response
import com.example.domain.episodes.model.Episode
import com.example.domain.locations.model.Locations

interface PersonageRepository {

    suspend fun getEpisodes(queryString: String): Response<List<Episode>>

    suspend fun getLocations(queryString: String): Response<Locations>
}