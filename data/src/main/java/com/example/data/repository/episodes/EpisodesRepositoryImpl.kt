package com.example.data.repository.episodes

import com.example.data.network.episodes.EpisodesRetrofitService
import com.example.data.network.episodes.model.EpisodesResponse
import com.example.data.repository.cache.EpisodesHistoryRepository
import com.example.domain.characters.model.Response
import com.example.domain.episodes.EpisodesRepository
import com.example.domain.episodes.model.Episode
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject


class EpisodesRepositoryImpl
@Inject constructor(
    private val episodesMapper: EpisodesMapper,
    private var episodesRetrofitService: EpisodesRetrofitService,
    private var episodesHistoryRepository: EpisodesHistoryRepository
) : EpisodesRepository {
    override suspend fun getEpisodes(): Response<List<Episode>> =
        withContext(Dispatchers.IO) {
            currentPage++
            try {
                val response = episodesRetrofitService.getAllEpisodes(currentPage).execute()
                val responseBody: EpisodesResponse?
                val responseError: String?
                if (response.isSuccessful) {
                    responseBody = response.body()
                    val historyData = episodesMapper.mapEpisodesToDb(responseBody!!)
                    for (i in historyData.indices) {
                        episodesHistoryRepository.insertNoteEpisodes(historyData[i])
                    }
                    return@withContext Response(
                        data = episodesMapper.mapEpisodesFromNetwork(responseBody),
                        errorText = null
                    )
                } else {
                    currentPage--
                    responseError = TEXT_NO_MORE_DATA
                    return@withContext Response(
                        data = null,
                        errorText = responseError
                    )
                }

            } catch (e: java.lang.Exception) {
                currentPage--
                val historyData =
                    episodesMapper.mapEpisodesFromDb(episodesHistoryRepository.allHistoryEpisodes())
                return@withContext Response(
                    data = historyData,
                    errorText = e.toString()
                )
            }
        }

    override fun setPageOnStart() {
        currentPage = 0
    }

    companion object {
        var currentPage = 0
        const val TEXT_NO_MORE_DATA = "No more data"
    }

}

