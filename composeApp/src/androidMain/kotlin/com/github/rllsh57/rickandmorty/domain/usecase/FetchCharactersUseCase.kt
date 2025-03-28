package com.github.rllsh57.rickandmorty.domain.usecase

import com.github.rllsh57.rickandmorty.domain.model.CharacterModel
import com.github.rllsh57.rickandmorty.domain.repository.CharacterRepository
import javax.inject.Inject

class FetchCharactersUseCase @Inject constructor(
    private val characterRepository: CharacterRepository
) {
    suspend fun execute(): List<CharacterModel> {
        return characterRepository.fetchCharacters()
    }
}