package com.github.rllsh57.rickandmorty.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.github.rllsh57.rickandmorty.ui.list.ListScreen

@Composable
fun Navigation() {
    val navigator = rememberNavController()
    NavHost(
        navController = navigator,
        startDestination = "/characters"
    ) {
        composable(
            route = "/characters",
        ) {
            ListScreen()
        }
    }
}