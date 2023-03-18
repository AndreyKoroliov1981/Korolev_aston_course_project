package com.example.rickandmorty.ui.episodes

import com.example.domain.episodes.model.Episode

data class EpisodesState(
    val episodes: List<Episode> = emptyList(),
    val searchName: String = "",
    val searchEpisodes: String = "",
    val dataLoading: Boolean = false
)
