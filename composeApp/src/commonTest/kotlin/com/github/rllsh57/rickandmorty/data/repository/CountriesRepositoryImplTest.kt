package com.github.rllsh57.rickandmorty.data.repository

import com.github.rllsh57.rickandmorty.data.database.dao.CharacterDao
import com.github.rllsh57.rickandmorty.data.database.entity.CharacterEntity
import com.github.rllsh57.rickandmorty.data.network.api.CharacterApi
import com.github.rllsh57.rickandmorty.data.network.dto.CharacterResponse
import com.github.rllsh57.rickandmorty.domain.model.CharacterModel
import kotlinx.coroutines.test.runTest
import org.mockito.Mockito
import org.mockito.kotlin.*
import kotlin.test.Test

class CountriesRepositoryImplTest {

    private val api = Mockito.mock<CharacterApi>()
    private val dao = Mockito.mock<CharacterDao>()

    private val repository = CharacterRepositoryImpl(api, dao)

    @Test
    fun `when fetchCountries() - given api error - then fetch data using dao`() = runTest {
        whenever(api.getCharacters(1)).thenThrow(RuntimeException())
        whenever(dao.getCharacters()).thenReturn(emptyList())

        repository.fetchCharacters(1, 20)

        verify(dao, times(1)).getCharacters()
    }

    private fun createResponseList() = listOf(
        CharacterResponse(0, "Rick", ("http://www.rick.com"))
    )
    private fun createModelList() = listOf(
        CharacterModel(0, "Rick", ("http://www.rick.com"))
    )
    private fun createEntityList() = listOf(
        CharacterEntity(0, "Rick", ("http://www.rick.com"))
    )
}