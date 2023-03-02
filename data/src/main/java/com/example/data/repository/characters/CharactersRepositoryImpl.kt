package com.example.data.repository.characters

import android.util.Log
import com.example.data.network.characters.CharactersRetrofitService
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
    override suspend fun getAllCharacters(page: Int): Response<List<Characters>> =
        withContext(Dispatchers.IO) {
            try {
                val response = charactersRetrofitService.getAllCharacters().execute()
                if (response.isSuccessful) {
                    Log.d("my_tag", "response successful")
                } else {
                    Log.d("my_tag", "response un successful")
                }
                val responseBody = response.body()
                return@withContext Response(
                    data = charactersMapper.mapCharactersFromNetwork(responseBody!!),
                    errorText = null)
            } catch (e: java.lang.Exception) {
                return@withContext Response(
                    data = null,
                    errorText = e.toString())
            }
        }
}

