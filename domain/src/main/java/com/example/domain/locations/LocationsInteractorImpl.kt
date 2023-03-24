package com.example.domain.locations

import com.example.domain.characters.model.Response
import com.example.domain.locations.model.Locations

class LocationsInteractorImpl(private val locationsRepository: LocationsRepository) :
    LocationsInteractor {
    private var currentSearchName = ""
    private var currentSearchType = ""
    override suspend fun getLocations(
        searchName: String,
        searchType: String,
    ): Response<List<Locations>> {
        if (currentSearchName != searchName) {
            locationsRepository.setPageOnStart()
            currentSearchName = searchName
        }
        if (currentSearchType != searchType) {
            locationsRepository.setPageOnStart()
            currentSearchType = searchType
        }

        val answer = locationsRepository.getLocations()
        val filterAnswer = mutableListOf<Locations>()
        if (answer.data != null)
            for (i in answer.data.indices) {
                if (answer.data[i].name.contains(searchName)) {
                    if (answer.data[i].type.contains(searchType)) {
                        filterAnswer.add(answer.data[i])
                    }

                }
            }
        return Response(filterAnswer, answer.errorText)
    }

    override fun setStartPage() {
        locationsRepository.setPageOnStart()
    }
}