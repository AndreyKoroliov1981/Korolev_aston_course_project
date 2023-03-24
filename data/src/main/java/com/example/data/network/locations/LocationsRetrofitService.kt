package com.example.data.network.locations

import com.example.data.network.locations.model.LocationsResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface LocationsRetrofitService {
 @GET("location")
 fun getAllLocations(
 @Query("page") page: Int
 ): Call<LocationsResponse>

}