package com.example.data.repository.episodes

import android.util.Log
import com.example.data.network.episodes.EpisodesRetrofitService
import com.example.data.network.episodes.model.EpisodesResponse
import com.example.domain.characters.model.Response
import com.example.domain.episodes.EpisodesRepository
import com.example.domain.episodes.model.Episode
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class EpisodesRepositoryImpl(
    private val episodesMapper: EpisodesMapper,
    private var episodesRetrofitService: EpisodesRetrofitService,
    //private var historyRepository: HistoryRepository
) : EpisodesRepository {
    override suspend fun getEpisodes(): Response<List<Episode>> =
        withContext(Dispatchers.IO) {
            currentPage++
            try {
                Log.d("my_tag", "start getEpisodes")
                val response = episodesRetrofitService.getAllEpisodes(currentPage).execute()
                val responseBody : EpisodesResponse?
                val responseError : String?
                if (response.isSuccessful) {
                    Log.d("my_tag", "response isSuccessful")
                    responseBody = response.body()
                    return@withContext Response(
                        data = episodesMapper.mapEpisodesFromNetwork(responseBody!!),
                        errorText = null)
                } else {
                    Log.d("my_tag", "response not Successful")
                    currentPage--
                    responseError = TEXT_NO_MORE_DATA
                    return@withContext Response(
                        data = null,
                        errorText = responseError)
                }

            } catch (e: java.lang.Exception) {
                currentPage--
                return@withContext Response(
                    data = null,
                    errorText = e.toString())
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

