package com.example.musicapp.features.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import com.example.musicapp.navigation.AppScreen
import com.example.musicapp.ui.components.navigation.Loading

@Composable
fun HomeScreen(navController: NavHostController) {
    var isLoading by remember { mutableStateOf(false) }
    if(isLoading){
        Loading()
    }
        else {
        Column(
            Modifier
                .fillMaxSize()
                .background(color = Color.White)
        ) {
            val onNavigate = { isLoading = true }
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
}


