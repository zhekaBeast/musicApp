package com.example.musicapp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.musicapp.ui.favorite.FavoriteScreen
import com.example.musicapp.ui.home.HomeScreen
import com.example.musicapp.ui.search.SearchScreen
import com.example.musicapp.ui.search.SearchViewModel
import com.example.musicapp.ui.settings.SettingsScreen

private object ViewModelProvider {
    fun getSearchViewModel(): SearchViewModel {
        return org.koin.core.context.GlobalContext.get().get<SearchViewModel>()
    }
}
@Composable
fun NavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    startDestination: String = AppScreen.Home.route,
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
            val searchViewModel: SearchViewModel = ViewModelProvider.getSearchViewModel()
            SearchScreen(
                navController,
                searchViewModel
            )
        }

    }
}