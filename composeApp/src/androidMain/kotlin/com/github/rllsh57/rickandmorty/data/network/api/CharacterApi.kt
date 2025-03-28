package com.github.rllsh57.rickandmorty.data.network.api

import com.github.rllsh57.rickandmorty.data.network.dto.CharacterPageResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface CharacterApi {

    @GET("character")
    suspend fun getCharacters(@Query("page") page: Int): CharacterPageResponse
}