package com.example.domain.episodes

import com.example.domain.characters.model.Response
import com.example.domain.episodes.model.Episode

interface EpisodesInteractor {
    suspend fun getEpisodes(searchName: String, searchEpisodes: String,) : Response<List<Episode>>

    fun setStartPage()
}