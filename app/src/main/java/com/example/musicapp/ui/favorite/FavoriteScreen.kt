package com.example.musicapp.ui.favorite

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.musicapp.ui.components.common.TrackList
import org.koin.compose.koinInject

@Composable
fun FavoriteScreen(navController: NavController, modifier: Modifier = Modifier) {
    val vm = koinInject<FavoriteViewModel>()
    val favoritesState by vm.state.collectAsState()
    Box(
        contentAlignment = Alignment.Center, modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        when (val state = favoritesState) {
            is FavoritesState.Error -> {
                Text(state.message)
            }

            FavoritesState.Loading -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }

            is FavoritesState.Success -> {
                TrackList(state.tracks, navController)
            }
        }
    }
}

