package com.example.domain.personage

import com.example.domain.characters.model.Response
import com.example.domain.episodes.model.Episode

interface PersonageRepository {

    suspend fun getEpisodes(queryString: String): Response<List<Episode>>
}