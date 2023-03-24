package com.example.domain.locations

import com.example.domain.characters.model.Response
import com.example.domain.locations.model.Locations

interface LocationsInteractor {
    suspend fun getLocations(searchName: String, searchType: String,) : Response<List<Locations>>

    fun setStartPage()
}