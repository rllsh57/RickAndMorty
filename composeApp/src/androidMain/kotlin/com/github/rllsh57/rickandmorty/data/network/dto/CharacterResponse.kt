package com.github.rllsh57.rickandmorty.data.network.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CharacterPageResponse(
    @SerialName("info") val info: InfoResponse,
    @SerialName("results") val results: List<CharacterResponse>
)

@Serializable
data class CharacterResponse(
    @SerialName("id") val id: Int,
    @SerialName("name") val name: String
)

@Serializable
data class InfoResponse(
    @SerialName("count") val count: Int,
    @SerialName("pages") val pages: Int
)

