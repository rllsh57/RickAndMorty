package com.github.rllsh57.rickandmorty.domain.usecase

import com.github.rllsh57.rickandmorty.domain.model.CharacterModel
import com.github.rllsh57.rickandmorty.domain.model.PagedListModel
import com.github.rllsh57.rickandmorty.domain.repository.CharacterRepository
import io.github.aakira.napier.Napier
import javax.inject.Inject

class FetchCharactersUseCase @Inject constructor(
    private val characterRepository: CharacterRepository
) {
    suspend fun execute(pagedList: PagedListModel<CharacterModel>): PagedListModel<CharacterModel> {
        val itemsSize = pagedList.items.size
        val totalSize = pagedList.totalSize
        Napier.d("execute: itemsSize = $itemsSize, totalSize = $totalSize")
        if (itemsSize < totalSize) {
            val nextPage = itemsSize / PAGE_SIZE
            val newItems = characterRepository.fetchCharacters(nextPage, PAGE_SIZE)
            return PagedListModel(
                items = pagedList.items + newItems.items,
                totalSize = newItems.totalSize
            )
        } else {
            return pagedList
        }
    }

    companion object {
        const val PAGE_SIZE = 20
    }
}