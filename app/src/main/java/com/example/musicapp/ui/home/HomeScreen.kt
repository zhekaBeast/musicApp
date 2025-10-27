package com.example.musicapp.ui.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.example.musicapp.navigation.AppScreen

@Composable
fun HomeScreen(navController: NavHostController, onNavigate: () -> Unit = {}) {
    Column(
        Modifier
            .fillMaxSize()
    ) {
    Column() {
            TransitionPanel(
                AppScreen.Search, navController, onNavigate
            )
            TransitionPanel(
                AppScreen.Playlists, navController, onNavigate
            )
            TransitionPanel(
                AppScreen.Favorite, navController, onNavigate
            )
            TransitionPanel(
                AppScreen.Settings, navController, onNavigate
            )
        }
    }
}


