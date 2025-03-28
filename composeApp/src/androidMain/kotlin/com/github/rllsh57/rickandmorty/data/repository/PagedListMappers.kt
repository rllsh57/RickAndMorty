package com.github.rllsh57.rickandmorty.data.repository

import com.github.rllsh57.rickandmorty.data.network.dto.CharacterPageResponse
import com.github.rllsh57.rickandmorty.data.network.mapper.toModelList
import com.github.rllsh57.rickandmorty.domain.model.*

fun CharacterPageResponse.toPagedListModel(): PagedListModel<CharacterModel> {
    return PagedListModel(
        results.toModelList(),
        info.count
    )
}