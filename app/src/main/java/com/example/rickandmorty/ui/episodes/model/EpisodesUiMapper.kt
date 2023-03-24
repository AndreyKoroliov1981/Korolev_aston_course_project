package com.example.rickandmorty.ui.episodes.model

import com.example.domain.episodes.model.Episode

class EpisodesUiMapper {
    fun mapEpisodesFromDomain(episode: Episode): EpisodesUi {
        return EpisodesUi(
            id = episode.id,
            name = episode.name,
            airDate = episode.airDate,
            episode = episode.episode,
            characters = episode.characters,
            url = episode.url,
            created = episode.created
        )
    }
}