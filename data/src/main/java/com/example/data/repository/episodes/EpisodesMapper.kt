package com.example.data.repository.episodes

import com.example.data.network.episodes.model.EpisodesResponse
import com.example.domain.episodes.model.Episode

class EpisodesMapper {
    fun mapEpisodesFromNetwork(episodesResponse: EpisodesResponse): List<Episode> {
        val newList = mutableListOf<Episode>()
        for (i in episodesResponse.results.indices) {
            val newEpisode = Episode(
                id = episodesResponse.results[i].id,
                name = episodesResponse.results[i].name,
                airDate = episodesResponse.results[i].airDate,
                episode = episodesResponse.results[i].episode,
                characters = episodesResponse.results[i].characters,
                url = episodesResponse.results[i].url,
                created = episodesResponse.results[i].created
            )
            newList.add(newEpisode)
        }
        return newList
    }
}