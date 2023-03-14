package com.example.data.repository.characters

import android.util.Log
import com.example.data.network.characters.CharactersRetrofitService
import com.example.data.network.characters.model.CharactersResponse
import com.example.domain.characters.CharactersRepository
import com.example.domain.characters.model.Characters
import com.example.domain.characters.model.Response
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class CharactersRepositoryImpl(
    private val charactersMapper: CharactersMapper,
    private var charactersRetrofitService: CharactersRetrofitService,
    //private var historyRepository: HistoryRepository
) : CharactersRepository {
    override suspend fun getCharacters(): Response<List<Characters>> =
        withContext(Dispatchers.IO) {
            currentPage++
            try {
                val response = charactersRetrofitService.getAllCharacters(currentPage).execute()
                val responseBody : CharactersResponse?
                val responseError : String?
                if (response.isSuccessful) {
                    responseBody = response.body()
                    return@withContext Response(
                        data = charactersMapper.mapCharactersFromNetwork(responseBody!!),
                        errorText = null)
                } else {
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

