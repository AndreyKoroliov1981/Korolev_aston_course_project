package com.example.data.network.episodes.model

import com.example.data.network.characters.model.InfoResponse

data class EpisodesResponse(
    val info : InfoResponse,
    val results: List<EpisodeResponse>
)