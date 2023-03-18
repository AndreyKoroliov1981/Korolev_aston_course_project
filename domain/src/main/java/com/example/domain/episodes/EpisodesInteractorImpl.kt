package com.example.domain.episodes

import com.example.domain.characters.model.Characters
import com.example.domain.characters.model.Response
import com.example.domain.episodes.model.Episode

class EpisodesInteractorImpl(private val episodesRepository: EpisodesRepository) :
    EpisodesInteractor {
    private var currentSearchName = ""
    private var currentSearchEpisodes = ""
    override suspend fun getEpisodes(
        searchName: String,
        searchEpisodes: String,
    ): Response<List<Episode>> {
        if (currentSearchName != searchName) {
            episodesRepository.setPageOnStart()
            currentSearchName = searchName
        }
        if (currentSearchEpisodes != searchEpisodes) {
            episodesRepository.setPageOnStart()
            currentSearchEpisodes = searchEpisodes
        }

        val answer = episodesRepository.getEpisodes()
        val filterAnswer = mutableListOf<Episode>()
        if (answer.data != null)
            for (i in answer.data.indices) {
                if (answer.data[i].name.contains(searchName)) {
                    if (answer.data[i].episode.contains(searchEpisodes)) {
                        filterAnswer.add(answer.data[i])
                        break
                    }

                }
            }
        return Response(filterAnswer, answer.errorText)
    }

    override fun setStartPage() {
        episodesRepository.setPageOnStart()
    }
}