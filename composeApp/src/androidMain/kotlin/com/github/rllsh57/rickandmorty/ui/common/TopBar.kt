package com.github.rllsh57.rickandmorty.ui.common

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.runtime.Composable

@Composable
fun TopBar(
    onBackClicked: (() -> Unit)? = null,
    actions: @Composable (RowScope.() -> Unit)? = null
) {
    TopAppBar(
        title = {
            Text("RickAndMorty")
        },
        windowInsets = ScaffoldDefaults.contentWindowInsets.only(WindowInsetsSides.Top),
        navigationIcon = if (onBackClicked != null) {
            { BackButton(onBackClicked) }
        } else {
            null
        },
        actions = { actions?.let { it() } }
    )
}

@Composable
fun BackButton(
    onBackClicked: () -> Unit
) {
    IconButton(
        onClick = { onBackClicked() }
    ) {
        Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
            contentDescription = null
        )
    }
}