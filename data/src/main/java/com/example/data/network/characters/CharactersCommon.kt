package com.example.data.network.characters

import com.example.data.BuildConfig

object CharactersCommon {
    val charactersRetrofitService: CharactersRetrofitService
        get() = CharactersRetrofitClient.getClient(BuildConfig.API_ENDPOINT)
            .create(CharactersRetrofitService::class.java)
}