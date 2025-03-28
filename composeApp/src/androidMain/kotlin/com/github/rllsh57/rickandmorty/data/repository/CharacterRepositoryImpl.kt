package com.github.rllsh57.rickandmorty.data.repository

import com.github.rllsh57.rickandmorty.data.network.api.CharacterApi
import com.github.rllsh57.rickandmorty.domain.model.*
import com.github.rllsh57.rickandmorty.domain.repository.CharacterRepository
import javax.inject.Inject

class CharacterRepositoryImpl @Inject constructor(
    private val api: CharacterApi
) : CharacterRepository {

    override suspend fun fetchCharacters(page: Int, limit: Int): PagedListModel<CharacterModel> {
        return fetchNetwork(page, limit)
    }

    /**
     * seems like api don't support @limit parameter, but could be supported
     */
    private suspend fun fetchNetwork(page: Int, limit: Int): PagedListModel<CharacterModel> {
        val response = api.getCharacters(page)
        return response.toPagedListModel()
    }
}