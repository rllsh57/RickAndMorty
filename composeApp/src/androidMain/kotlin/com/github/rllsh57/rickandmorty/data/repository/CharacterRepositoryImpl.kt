package com.github.rllsh57.rickandmorty.data.repository

import com.github.rllsh57.rickandmorty.data.database.dao.CharacterDao
import com.github.rllsh57.rickandmorty.data.database.mapper.*
import com.github.rllsh57.rickandmorty.data.network.api.CharacterApi
import com.github.rllsh57.rickandmorty.domain.model.*
import com.github.rllsh57.rickandmorty.domain.repository.CharacterRepository
import io.github.aakira.napier.Napier
import javax.inject.Inject

class CharacterRepositoryImpl @Inject constructor(
    private val api: CharacterApi,
    private val dao: CharacterDao,
) : CharacterRepository {

    override suspend fun fetchCharacters(page: Int, limit: Int): PagedListModel<CharacterModel> {
        return try {
            val items = fetchNetwork(page, limit)
            try {
                updateDatabase(items.items)
            } catch (e: Exception) {
                Napier.e("updateDatabase failed", e)
            }
            items
        } catch (e: Exception) {
            Napier.e("fetchCharacters failed", e)
            fetchDatabase(page, limit)
        }
    }

    /**
     * seems like api don't support @limit parameter, but could be supported
     */
    private suspend fun fetchNetwork(page: Int, limit: Int): PagedListModel<CharacterModel> {
        val response = api.getCharacters(page + 1)
        return response.toPagedListModel()
    }

    private suspend fun fetchDatabase(page: Int, limit: Int): PagedListModel<CharacterModel> {
        val count = dao.getCount()
        val result = dao.getCharacters(page * limit, limit)
        return PagedListModel(
            result.toModelList(),
            count
        )
    }

    private suspend fun updateDatabase(items: List<CharacterModel>) {
        val items = items.toEntityList()
        dao.updateCharacters(items)
    }
}