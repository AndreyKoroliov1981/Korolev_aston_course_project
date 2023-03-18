package com.example.data.network.personage

import com.example.data.BuildConfig

object PersonageCommon {
    val personageRetrofitService: PersonageRetrofitService
        get() = PersonageRetrofitClient.getClient(BuildConfig.API_ENDPOINT)
            .create(PersonageRetrofitService::class.java)
}