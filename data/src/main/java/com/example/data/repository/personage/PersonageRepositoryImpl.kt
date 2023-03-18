package com.example.data.repository.personage

import com.example.data.network.episode.model.EpisodeResponse
import com.example.data.network.personage.PersonageRetrofitService
import com.example.domain.characters.model.Response
import com.example.domain.episodes.model.Episode
import com.example.domain.personage.PersonageRepository
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

class PersonageRepositoryImpl(
    private val episodeMapper: EpisodeMapper,
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
}