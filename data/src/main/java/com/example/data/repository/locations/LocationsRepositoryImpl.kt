package com.example.data.repository.locations

import com.example.data.network.locations.LocationsRetrofitService
import com.example.data.network.locations.model.LocationsResponse
import com.example.data.repository.cache.HistoryRepositoryLocations
import com.example.domain.characters.model.Response
import com.example.domain.locations.LocationsRepository
import com.example.domain.locations.model.Locations
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject


class LocationsRepositoryImpl
@Inject constructor(
    private val locationsMapper: LocationsMapper,
    private var locationsRetrofitService: LocationsRetrofitService,
    private var historyRepository: HistoryRepositoryLocations
) : LocationsRepository {
    override suspend fun getLocations(): Response<List<Locations>> =
        withContext(Dispatchers.IO) {
            currentPage++
            try {
                val response = locationsRetrofitService.getAllLocations(currentPage).execute()
                val responseBody: LocationsResponse?
                val responseError: String?
                if (response.isSuccessful) {
                    responseBody = response.body()
                    val historyData = locationsMapper.mapLocationsToDb(responseBody!!)
                    for (i in historyData.indices) {
                        historyRepository.insertNoteLocations(historyData[i])
                    }
                    return@withContext Response(
                        data = locationsMapper.mapLocationsFromNetwork(responseBody!!),
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
                    locationsMapper.mapLocationsFromDb(historyRepository.allHistoryLocations())
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

