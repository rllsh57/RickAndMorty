package com.github.rllsh57.rickandmorty.ui

import androidx.compose.material.*
import androidx.compose.runtime.Composable
import com.github.rllsh57.rickandmorty.ui.list.ListScreen
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    MaterialTheme {
        ListScreen()
    }
}