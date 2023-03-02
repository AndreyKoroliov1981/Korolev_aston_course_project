package com.example.data.network.characters

import com.example.data.network.characters.model.CharactersResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface CharactersRetrofitService {
 @GET("character")
 fun getAllCharacters(
 //@Query("pages") page: Int
 ): Call<CharactersResponse>
}