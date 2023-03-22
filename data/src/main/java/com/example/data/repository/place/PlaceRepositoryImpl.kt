package com.example.data.repository.place

import com.example.data.database.characters.CharactersDb
import com.example.data.network.characters.model.PersonResponse
import com.example.data.network.place.PlaceRetrofitService
import com.example.data.repository.cache.HistoryRepositoryCharacters
import com.example.domain.characters.model.Characters
import com.example.domain.characters.model.Response
import com.example.domain.place.PlaceRepository
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

class PlaceRepositoryImpl(
    private val placeMapper: PlaceMapper,
    private var placeRetrofitService: PlaceRetrofitService,
    private var historyRepositoryCharacters: HistoryRepositoryCharacters
) : PlaceRepository {
    override suspend fun getResidents(queryString: String): Response<List<Characters>> {
        val answer = try {
            val data  = placeMapper.mapPlaceFromNetwork(requestRXJavaRetrofit(queryString).blockingGet())
            val historyData = placeMapper.mapCharactersToDb(data)
            for (i in historyData.indices) {
                historyRepositoryCharacters.insertNote(historyData[i])
            }
            Response(
                data,
                null
            )
        } catch (t: Throwable) {
            val listId = queryString.split(",")
            val newList = mutableListOf<CharactersDb>()
            for (i in listId.indices) {
                val newEpisodeDb = historyRepositoryCharacters.getById(listId[i].toLong())
                newEpisodeDb?.let { newList.add(newEpisodeDb) }
            }
            Response(placeMapper.mapCharactersFromDb(newList), t.message)
        }
        return answer
    }

    private fun requestRXJavaRetrofit(queryString: String): Single<List<PersonResponse>> {
        return placeRetrofitService.getCharacters(queryString)
            .subscribeOn(Schedulers.io())
            .first(emptyList())
    }

}
