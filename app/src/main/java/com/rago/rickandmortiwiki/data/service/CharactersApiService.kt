package com.rago.rickandmortiwiki.data.service

import com.rago.rickandmortiwiki.data.model.CharactersResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface CharactersApiService {

    @GET("character/")
    fun getCharacters(
        @Query("page") page: Int
    ): Call<CharactersResponse>
}