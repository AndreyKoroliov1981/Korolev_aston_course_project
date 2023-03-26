package com.example.data.repository.personage

import android.util.Log
import com.example.data.network.episodes.model.EpisodeResponse
import com.example.data.network.locations.model.LocationeResponse
import com.example.data.network.personage.PersonageRetrofitService
import com.example.domain.characters.model.Response
import com.example.domain.episodes.model.Episode
import com.example.domain.locations.model.Locations
import com.example.domain.personage.PersonageRepository
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

class PersonageRepositoryImpl(
    private val episodeMapper: EpisodeMapper,
    private val locationeMapper: LocationeMapper,
    private var personageRetrofitService: PersonageRetrofitService,
) : PersonageRepository {
    override suspend fun getEpisodes(queryString: String): Response<List<Episode>> {
        val answer = try {
            Response(
                episodeMapper.mapEpisodeFromNetwork(requestRXJavaRetrofit(queryString).blockingGet()),
                null
            )
        } catch (t: Throwable) {
            Response(emptyList(), t.message)
        }
        return answer
    }

    private fun requestRXJavaRetrofit(queryString: String): Single<List<EpisodeResponse>> {
        return personageRetrofitService.getEpisodes(queryString)
            .subscribeOn(Schedulers.io())
            .first(emptyList())
    }

    override suspend fun getLocations(queryString: String): Response<Locations> {
        val answer = try {
            Response(
                locationeMapper.mapLocationeFromNetwork(requestLocations(queryString).blockingGet()!!),
                null
            )
        } catch (t: Throwable) {
            val emptyLocations = Locations(
                id = 0,
                name = "",
                type = "",
                dimension = "",
                residents = emptyList(),
                url = "",
                created = ""
            )
            Response (emptyLocations, t.message)
        }
        return answer
    }

    private fun requestLocations(queryString: String): Single<LocationeResponse?> {
        val emptyLocationeResponse = LocationeResponse(
            id = 0,
            name = "",
            type = "",
            dimension = "",
            residents = emptyList(),
            url = "",
            created = ""
        )
        return personageRetrofitService.getLocations(queryString)
            .subscribeOn(Schedulers.io())
            .first(emptyLocationeResponse)
    }
}