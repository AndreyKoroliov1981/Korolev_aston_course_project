package com.example.data.network.locations

import com.example.data.BuildConfig

object LocationsCommon {
    val locationsRetrofitService: LocationsRetrofitService
        get() = LocationsRetrofitClient.getClient(BuildConfig.API_ENDPOINT)
            .create(LocationsRetrofitService::class.java)
}