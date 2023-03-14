package com.example.domain.characters

import com.example.domain.characters.model.Characters
import com.example.domain.characters.model.Response

class CharactersInteractorImpl(private val charactersRepository: CharactersRepository) :
    CharactersInteractor {
    private var currentSearchName = ""
    private var currentFilters = listOf("Alive", "Dead", "unknown")
    override suspend fun getCharacters(
        searchName: String,
        filters: List<String>
    ): Response<List<Characters>> {
        if (currentSearchName != searchName) {
            charactersRepository.setPageOnStart()
            currentSearchName = searchName
        }
        if (currentFilters != filters) {
            charactersRepository.setPageOnStart()
            currentFilters = filters
        }

        val answer = charactersRepository.getCharacters()
        val filterAnswer = mutableListOf<Characters>()
        if (answer.data != null)
            for (i in answer.data.indices) {
                if (answer.data[i].name.contains(searchName)) {
                    for (j in filters.indices) {
                        if (answer.data[i].status == filters[j]) {
                            filterAnswer.add(answer.data[i])
                            break
                        }
                    }
                }
            }
        return Response(filterAnswer, answer.errorText)
    }
}