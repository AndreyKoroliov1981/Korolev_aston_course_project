package com.example.data.repository.place

import com.example.data.network.characters.model.PersonResponse
import com.example.data.network.place.PlaceRetrofitService
import com.example.domain.characters.model.Characters
import com.example.domain.characters.model.Response
import com.example.domain.place.PlaceRepository
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

class PlaceRepositoryImpl(
    private val placeMapper: PlaceMapper,
    private var placeRetrofitService: PlaceRetrofitService
) : PlaceRepository {
    override suspend fun getResidents(queryString: String): Response<List<Characters>> {
        val answer = try {
            Response(
                placeMapper.mapPlaceFromNetwork(requestRXJavaRetrofit(queryString).blockingGet()),
                null
            )
        } catch (t: Throwable) {
            Response(emptyList(), t.message)
        }
        return answer
    }

    private fun requestRXJavaRetrofit(queryString: String): Single<List<PersonResponse>> {
        return placeRetrofitService.getCharacters(queryString)
            .subscribeOn(Schedulers.io())
            .first(emptyList())
    }

}
