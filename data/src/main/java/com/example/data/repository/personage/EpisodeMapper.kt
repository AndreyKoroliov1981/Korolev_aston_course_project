package com.example.data.repository.personage

import com.example.data.database.episodes.EpisodesDb
import com.example.data.network.episodes.model.EpisodeResponse
import com.example.domain.episodes.model.Episode
import javax.inject.Inject

class EpisodeMapper
@Inject constructor() {
    fun mapEpisodeFromNetwork(episodeResponse: List<EpisodeResponse>): List<Episode> {
        val newList = mutableListOf<Episode>()
        for (i in episodeResponse.indices) {
            val newEpisode = with(episodeResponse[i]) {
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

    fun mapEpisodeToDb(episodes: List<Episode>): List<EpisodesDb> {
        val newList = mutableListOf<EpisodesDb>()
        for (i in episodes.indices) {
            val newEpisodeDb = with(episodes[i]) {
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
            newList.add(newEpisodeDb)
        }
        return newList
    }

    fun mapEpisodeFromDb(episodesDb: List<EpisodesDb>): List<Episode> {
        val newList = mutableListOf<Episode>()
        for (i in episodesDb.indices) {
            val newEpisode = with(episodesDb[i]) {
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

}