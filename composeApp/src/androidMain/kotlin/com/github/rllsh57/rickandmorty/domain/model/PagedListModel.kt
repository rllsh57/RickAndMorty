package com.github.rllsh57.rickandmorty.domain.model

data class PagedListModel<T>(
    val items: List<T> = listOf(),
    val totalSize: Int = Int.MAX_VALUE
)