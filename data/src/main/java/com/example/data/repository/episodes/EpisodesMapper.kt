package com.example.data.repository.episodes

import com.example.data.database.episodes.EpisodesDb
import com.example.data.network.episodes.model.EpisodesResponse
import com.example.domain.episodes.model.Episode
import javax.inject.Inject

class EpisodesMapper
@Inject constructor() {
    fun mapEpisodesFromNetwork(episodesResponse: EpisodesResponse): List<Episode> {
        val newList = mutableListOf<Episode>()
        for (i in episodesResponse.results.indices) {
            val newEpisode = with(episodesResponse.results[i]) {
                Episode(
                    id = id,
                    name = name,
                    airDate = airDate,
                    episode = episode,
                    characters = characters,
                    url = url,
                    created = created
                )
            }
            newList.add(newEpisode)
        }
        return newList
    }

    fun mapEpisodesToDb(episodesResponse: EpisodesResponse): List<EpisodesDb> {
        val newList = mutableListOf<EpisodesDb>()
        for (i in episodesResponse.results.indices) {
            val newEpisodes = with(episodesResponse.results[i]) {
                EpisodesDb(
                    id = id,
                    name = name,
                    airDate = airDate,
                    episode = episode,
                    characters = characters,
                    url = url,
                    created = created
                )
            }
            newList.add(newEpisodes)
        }
        return newList
    }

    fun mapEpisodesFromDb(episodesDb: List<EpisodesDb>): List<Episode> {
        val newList = mutableListOf<Episode>()
        for (i in episodesDb.indices) {
            val newEpisodes = with(episodesDb[i]) {
                Episode(
                    id = id,
                    name = name,
                    airDate = airDate,
                    episode = episode,
                    characters = characters,
                    url = url,
                    created = created
                )
            }
            newList.add(newEpisodes)
        }
        return newList

    }
}