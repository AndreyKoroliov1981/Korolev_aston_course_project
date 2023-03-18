package com.example.domain.episodes

import com.example.domain.characters.model.Response
import com.example.domain.episodes.model.Episode

interface EpisodesRepository {
    suspend fun getEpisodes() : Response<List<Episode>>

    fun setPageOnStart()
}