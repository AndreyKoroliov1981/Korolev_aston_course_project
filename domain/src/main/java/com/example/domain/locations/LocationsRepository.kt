package com.example.domain.locations

import com.example.domain.characters.model.Response
import com.example.domain.episodes.model.Episode
import com.example.domain.locations.model.Locations

interface LocationsRepository {
    suspend fun getLocations() : Response<List<Locations>>

    fun setPageOnStart()
}