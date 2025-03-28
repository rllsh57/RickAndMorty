package com.github.rllsh57.rickandmorty.domain.repository

import com.github.rllsh57.rickandmorty.domain.model.CharacterModel

interface CharacterRepository {

    suspend fun fetchCharacters(): List<CharacterModel>
}