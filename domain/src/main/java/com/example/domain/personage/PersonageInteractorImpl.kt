package com.example.domain.personage

import com.example.domain.characters.model.Response
import com.example.domain.episodes.model.Episode
import com.example.domain.locations.model.Locations

class PersonageInteractorImpl(
    private val personageRepository: PersonageRepository
    ): PersonageInteractor {
    override suspend fun getEpisodes(episodes: List<String>): Response<List<Episode>> {
        var queryString = episodes[0].substringAfterLast('/')
        for (i in 1 until episodes.size) {
            queryString += "," + episodes[i].substringAfterLast('/')
        }
        if (episodes.size == 1) {
            queryString += ","
        }
        return personageRepository.getEpisodes(queryString)
    }

    override suspend fun getLocations(locations: String): Response<Locations> {
        val queryString = locations.substringAfterLast('/')
        return personageRepository.getLocations(queryString)
    }
}