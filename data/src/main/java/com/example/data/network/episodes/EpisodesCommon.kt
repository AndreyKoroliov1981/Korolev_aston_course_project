package com.example.data.network.episodes

import com.example.data.BuildConfig

object EpisodesCommon {
    val episodesRetrofitService: EpisodesRetrofitService
        get() = EpisodesRetrofitClient.getClient(BuildConfig.API_ENDPOINT)
            .create(EpisodesRetrofitService::class.java)
}