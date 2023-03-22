package com.example.data.repository.personage

import com.example.data.database.episodes.EpisodesDb
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

    fun mapEpisodeToDb(episodes: List<Episode>): List<EpisodesDb> {
        val newList = mutableListOf<EpisodesDb>()
        for (i in episodes.indices) {
            val newEpisodeDb = EpisodesDb(
                id = episodes[i].id,
                name = episodes[i].name,
                airDate = episodes[i].airDate,
                episode = episodes[i].episode,
                characters = episodes[i].characters,
                url = episodes[i].url,
                created = episodes[i].created
            )
            newList.add(newEpisodeDb)
        }
        return newList
    }

    fun mapEpisodeFromDb(episodesDb: List<EpisodesDb>): List<Episode> {
        val newList = mutableListOf<Episode>()
        for (i in episodesDb.indices) {
            val newEpisode = Episode(
                id = episodesDb[i].id,
                name = episodesDb[i].name,
                airDate = episodesDb[i].airDate,
                episode = episodesDb[i].episode,
                characters = episodesDb[i].characters,
                url = episodesDb[i].url,
                created = episodesDb[i].created
            )
            newList.add(newEpisode)
        }
        return newList
    }

}