package com.github.rllsh57.rickandmorty.ui

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import com.github.rllsh57.rickandmorty.ui.navigation.Navigation
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    MaterialTheme {
        Navigation()
    }
}