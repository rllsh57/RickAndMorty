package com.github.rllsh57.rickandmorty.data.repository

import com.github.rllsh57.rickandmorty.data.database.dao.CharacterDao
import com.github.rllsh57.rickandmorty.data.database.entity.CharacterEntity
import com.github.rllsh57.rickandmorty.data.network.api.CharacterApi
import com.github.rllsh57.rickandmorty.data.network.dto.CharacterPageResponse
import com.github.rllsh57.rickandmorty.data.network.dto.CharacterResponse
import com.github.rllsh57.rickandmorty.data.network.dto.InfoResponse
import com.github.rllsh57.rickandmorty.domain.model.CharacterModel
import kotlinx.coroutines.test.runTest
import org.mockito.Mockito
import org.mockito.kotlin.*
import kotlin.test.Test
import kotlin.test.assertEquals

class CharacterRepositoryImplTest {

    private val api = Mockito.mock<CharacterApi>()
    private val dao = Mockito.mock<CharacterDao>()

    private val repository = CharacterRepositoryImpl(api, dao)

    @Test
    fun `when fetchCharacters() - given api error - then fetch data using dao`() = runTest {
        val entities = createEntityList()

        whenever(api.getCharacters(1)).thenThrow(RuntimeException())
        whenever(dao.getCount()).thenReturn(1)
        whenever(dao.getCharacters(0, 20)).thenReturn(entities)

        val result = repository.fetchCharacters(0, 20)

        val models = createModelList()
        verify(dao, times(1)).getCharacters(0, 20)
        assertEquals(1, result.totalSize)
        assertEquals(models, result.items)
    }

    @Test
    fun `when fetchCharacters() - given api success - then update data using dao`() = runTest {
        val response = createResponse()
        whenever(api.getCharacters(1)).thenReturn(response)

        val result = repository.fetchCharacters(0, 20)

        verify(dao, never()).getCharacters(any(), any())
        val entity = createEntityList()
        verify(dao, times(1)).updateCharacters(entity)
        val models = createModelList()
        assertEquals(1, result.totalSize)
        assertEquals(models, result.items)
    }

    private fun createResponse() = CharacterPageResponse(
        info = InfoResponse(1, 1),
        results = listOf(CharacterResponse(0, "Rick", ("http://www.rick.com")))
    )
    private fun createModelList() = listOf(
        CharacterModel(0, "Rick", ("http://www.rick.com"))
    )
    private fun createEntityList() = listOf(
        CharacterEntity(0, "Rick", ("http://www.rick.com"))
    )
}