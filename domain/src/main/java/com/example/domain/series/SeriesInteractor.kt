package com.example.domain.series

import com.example.domain.characters.model.Characters
import com.example.domain.characters.model.Response
import com.example.domain.episodes.model.Episode

interface SeriesInteractor {
    suspend fun getResidents(residents: List<String>): Response<List<Characters>>
}