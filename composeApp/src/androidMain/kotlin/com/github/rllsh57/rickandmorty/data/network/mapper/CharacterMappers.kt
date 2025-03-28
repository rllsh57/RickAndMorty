package com.github.rllsh57.rickandmorty.data.network.mapper

import com.github.rllsh57.rickandmorty.data.network.dto.CharacterResponse
import com.github.rllsh57.rickandmorty.domain.model.CharacterModel

fun List<CharacterResponse>.toModelList(): List<CharacterModel> {
    return map { it.toModel() }
}

fun CharacterResponse.toModel(): CharacterModel {
    return CharacterModel(
        id = id,
        name = name,
        image = image
    )
}