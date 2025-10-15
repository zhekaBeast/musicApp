package com.example.musicapp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.musicapp.features.favorite.FavoriteScreen
import com.example.musicapp.features.home.HomeScreen
import com.example.musicapp.features.search.SearchScreen
import com.example.musicapp.features.settings.SettingsScreen

@Composable
fun NavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    startDestination: String = AppScreen.Home.route
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        composable(
            route = AppScreen.Home.route
        ) {
            HomeScreen(navController)
        }
        composable(
            route = AppScreen.Settings.route
        ) {
            SettingsScreen(navController)
        }
        composable(
            route = AppScreen.Favorite.route
        ) {
            FavoriteScreen(navController)
        }
        composable(
            route = AppScreen.Search.route
        ) {
            SearchScreen(navController)
        }

    }
}