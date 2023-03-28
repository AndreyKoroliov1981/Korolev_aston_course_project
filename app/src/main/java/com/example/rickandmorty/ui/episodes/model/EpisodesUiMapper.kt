package com.example.rickandmorty.ui.episodes.model

import com.example.domain.episodes.model.Episode
import javax.inject.Inject

class EpisodesUiMapper
@Inject constructor() {
    fun mapEpisodesFromDomain(episod: Episode): EpisodesUi {
        return with(episod) {
            EpisodesUi(
                id = id,
                name = name,
                airDate = airDate,
                episode = episode,
                characters = characters,
                url = url,
                created = created
            )
        }
    }
}