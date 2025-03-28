package com.github.rllsh57.rickandmorty.domain.repository

import com.github.rllsh57.rickandmorty.domain.model.CharacterModel
import com.github.rllsh57.rickandmorty.domain.model.PagedListModel

interface CharacterRepository {

    suspend fun fetchCharacters(page: Int, limit: Int): PagedListModel<CharacterModel>
}