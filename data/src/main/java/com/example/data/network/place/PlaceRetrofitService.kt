package com.example.data.network.place

import com.example.data.network.characters.model.PersonResponse
import com.example.data.network.episodes.model.EpisodeResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path

interface PlaceRetrofitService {
    @GET("character/{queryString}")
    fun getCharacters(
        @Path("queryString") queryString: String
    ): Observable<List<PersonResponse>>
}