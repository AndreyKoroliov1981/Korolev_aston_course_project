package com.example.domain.series

import com.example.domain.characters.model.Characters
import com.example.domain.characters.model.Response
import com.example.domain.episodes.model.Episode
import com.example.domain.place.PlaceRepository
import javax.inject.Inject

class SeriesInteractorImpl
@Inject constructor(
    private val placeRepository: PlaceRepository
) : SeriesInteractor {
    override suspend fun getResidents(residents: List<String>): Response<List<Characters>> {
        var queryString = residents[0].substringAfterLast('/')
        for (i in 1 until residents.size) {
            queryString += "," + residents[i].substringAfterLast('/')
        }
        if (residents.size == 1) {
            queryString += ","
        }
        return placeRepository.getResidents(queryString)
    }
}