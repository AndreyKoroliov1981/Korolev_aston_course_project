package com.example.data.repository.personage

import android.util.Log
import com.example.data.database.episodes.EpisodesDb
import com.example.data.network.episodes.model.EpisodeResponse
import com.example.data.network.locations.model.LocationeResponse
import com.example.data.network.personage.PersonageRetrofitService
import com.example.data.repository.cache.HistoryRepositoryEpisodes
import com.example.data.repository.cache.HistoryRepositoryLocations
import com.example.domain.characters.model.Response
import com.example.domain.episodes.model.Episode
import com.example.domain.locations.model.Locations
import com.example.domain.personage.PersonageRepository
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class PersonageRepositoryImpl
@Inject constructor(
    private val episodeMapper: EpisodeMapper,
    private val locationeMapper: LocationeMapper,
    private var personageRetrofitService: PersonageRetrofitService,
    private var historyRepositoryEpisodes: HistoryRepositoryEpisodes,
    private var historyRepositoryLocations: HistoryRepositoryLocations
) : PersonageRepository {
    override suspend fun getEpisodes(queryString: String): Response<List<Episode>> {
        val answer = try {
            val data =
                episodeMapper.mapEpisodeFromNetwork(requestRXJavaRetrofit(queryString).blockingGet())
            val historyData = episodeMapper.mapEpisodeToDb(data)
            for (i in historyData.indices) {
                historyRepositoryEpisodes.insertNoteEpisodes(historyData[i])
            }
            Response(
                data,
                null
            )
        } catch (t: Throwable) {
            val listId = queryString.split(",")
            val newList = mutableListOf<EpisodesDb>()
            for (i in listId.indices) {
                val newEpisodeDb = historyRepositoryEpisodes.getByIdEpisodes(listId[i].toLong())
                newEpisodeDb?.let { newList.add(newEpisodeDb) }
            }
            Response(episodeMapper.mapEpisodeFromDb(newList), t.message)
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
            val data =
                locationeMapper.mapLocationeFromNetwork(requestLocations(queryString).blockingGet()!!)
            val historyData = locationeMapper.mapLocationsToDb(data)
            historyRepositoryLocations.insertNoteLocations(historyData)
            Response(
                data,
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
            val locations =
                locationeMapper.mapLocationsFromDb(
                    historyRepositoryLocations.getByIdLocations(
                        queryString.toLong()
                    )
                ) ?: emptyLocations
            Response(locations, t.message)
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