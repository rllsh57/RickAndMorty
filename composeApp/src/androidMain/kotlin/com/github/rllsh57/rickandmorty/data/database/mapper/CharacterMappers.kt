package com.github.rllsh57.rickandmorty.data.database.mapper

import com.github.rllsh57.rickandmorty.data.database.entity.CharacterEntity
import com.github.rllsh57.rickandmorty.domain.model.CharacterModel

fun CharacterModel.toEntity(): CharacterEntity {
    return CharacterEntity(
        id = id,
        name = name,
        image = image
    )
}

fun CharacterEntity.toModel(): CharacterModel {
    return CharacterModel(
        id = id,
        name = name,
        image = image
    )
}

fun List<CharacterEntity>.toModelList(): List<CharacterModel> {
    return map { it.toModel() }
}

fun List<CharacterModel>.toEntityList(): List<CharacterEntity> {
    return map { it.toEntity() }
}
