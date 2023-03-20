package com.example.domain.place

import com.example.domain.characters.model.Characters
import com.example.domain.characters.model.Response
import com.example.domain.episodes.model.Episode

interface PlaceRepository {

    suspend fun getResidents(queryString: String): Response<List<Characters>>
}