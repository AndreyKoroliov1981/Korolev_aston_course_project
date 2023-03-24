package com.example.data.network.episodes

import com.example.data.network.episodes.model.EpisodesResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface EpisodesRetrofitService {
 @GET("episode")
 fun getAllEpisodes(
 @Query("page") page: Int
 ): Call<EpisodesResponse>

}