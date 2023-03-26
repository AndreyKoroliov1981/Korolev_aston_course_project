package com.example.data.network.place

import com.example.data.BuildConfig

object PlaceCommon {
    val placeRetrofitService: PlaceRetrofitService
        get() = PlaceRetrofitClient.getClient(BuildConfig.API_ENDPOINT)
            .create(PlaceRetrofitService::class.java)
}