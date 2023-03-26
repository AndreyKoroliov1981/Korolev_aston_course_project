package com.example.domain.place

import com.example.domain.characters.model.Characters
import com.example.domain.characters.model.Response
import com.example.domain.episodes.model.Episode

interface PlaceInteractor {
    suspend fun getResidents(residents: List<String>): Response<List<Characters>>
}