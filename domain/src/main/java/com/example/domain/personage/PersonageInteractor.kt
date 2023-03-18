package com.example.domain.personage

import com.example.domain.characters.model.Response
import com.example.domain.episodes.model.Episode

interface PersonageInteractor {
    suspend fun getEpisodes(episodes: List<String>): Response<List<Episode>>
}