package com.github.rllsh57.rickandmorty.data.repository

import com.github.rllsh57.rickandmorty.data.network.api.CharacterApi
import com.github.rllsh57.rickandmorty.data.network.mapper.toModelList
import com.github.rllsh57.rickandmorty.domain.model.CharacterModel
import com.github.rllsh57.rickandmorty.domain.repository.CharacterRepository
import javax.inject.Inject

class CharacterRepositoryImpl @Inject constructor(
    private val api: CharacterApi
) : CharacterRepository {

    override suspend fun fetchCharacters(): List<CharacterModel> {
        return fetchNetwork()
    }

    private suspend fun fetchNetwork(): List<CharacterModel> {
        val response = api.getCharacters()
        return response.results.toModelList()
    }
}