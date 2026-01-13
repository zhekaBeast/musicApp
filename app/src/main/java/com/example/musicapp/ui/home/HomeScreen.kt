package com.example.musicapp.ui.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.musicapp.ui.navigation.AppScreen

@Composable
fun HomeScreen(navController: NavController) {
    Column(
        Modifier
            .fillMaxSize()
    ) {
        Column {
            TransitionPanel(
                AppScreen.Search, navController
            )
            TransitionPanel(
                AppScreen.Playlists, navController
            )
            TransitionPanel(
                AppScreen.Favorite, navController,
            )
            TransitionPanel(
                AppScreen.Settings, navController
            )
        }
    }
}


