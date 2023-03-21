package com.example.data.repository.episodes

import com.example.data.database.episodes.EpisodesDb
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

    fun mapEpisodesToDb(episodesResponse: EpisodesResponse): List<EpisodesDb> {
        val newList = mutableListOf<EpisodesDb>()
        for (i in episodesResponse.results.indices) {
            val newEpisodes = EpisodesDb(
                id = episodesResponse.results[i].id,
                name = episodesResponse.results[i].name,
                airDate = episodesResponse.results[i].airDate,
                episode = episodesResponse.results[i].episode,
                characters = episodesResponse.results[i].characters,
                url = episodesResponse.results[i].url,
                created = episodesResponse.results[i].created
            )
            newList.add(newEpisodes)
        }
        return newList
    }

    fun mapEpisodesFromDb(episodesDb: List<EpisodesDb>): List<Episode> {
        val newList = mutableListOf<Episode>()
        for (i in episodesDb.indices) {
            val newEpisodes = Episode(
                id = episodesDb[i].id,
                name = episodesDb[i].name,
                airDate = episodesDb[i].airDate,
                episode = episodesDb[i].episode,
                characters = episodesDb[i].characters,
                url = episodesDb[i].url,
                created = episodesDb[i].created
            )
            newList.add(newEpisodes)
        }
        return newList

    }
}