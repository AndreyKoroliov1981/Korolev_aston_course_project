package com.example.data.repository.personage

import com.example.data.network.episodes.model.EpisodeResponse
import com.example.domain.episodes.model.Episode

class EpisodeMapper {
    fun mapEpisodeFromNetwork(episodeResponse: List<EpisodeResponse>): List<Episode> {
        val newList = mutableListOf<Episode>()
        for (i in episodeResponse.indices) {
            val newEpisode = Episode(
                id = episodeResponse[i].id,
                name = episodeResponse[i].name,
                airDate = episodeResponse[i].airDate,
                episode = episodeResponse[i].episode,
                characters = episodeResponse[i].characters,
                url = episodeResponse[i].url,
                created = episodeResponse[i].created
            )
            newList.add(newEpisode)
        }
        return newList
    }
}