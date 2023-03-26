package com.example.data.network.personage

import com.example.data.network.episodes.model.EpisodeResponse
import com.example.data.network.locations.model.LocationeResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path

interface PersonageRetrofitService {
    @GET("episode/{queryString}")
    fun getEpisodes(
        @Path("queryString") queryString:String
    ) : Observable<List<EpisodeResponse>>

    @GET("location/{queryString}")
    fun getLocations(
        @Path("queryString") queryString:String
    ) : Observable<LocationeResponse>
}