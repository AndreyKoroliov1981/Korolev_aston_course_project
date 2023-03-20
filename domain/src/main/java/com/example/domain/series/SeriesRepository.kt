package com.example.domain.series

import com.example.domain.characters.model.Characters
import com.example.domain.characters.model.Response
import com.example.domain.episodes.model.Episode

interface SeriesRepository {

    suspend fun getResidents(queryString: String): Response<List<Characters>>
}