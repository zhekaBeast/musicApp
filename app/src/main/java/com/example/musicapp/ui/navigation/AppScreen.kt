package com.example.musicapp.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LibraryMusic
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.musicapp.R

enum class AppScreen(
    val route: String,
    val icon: ImageVector,
    val titleId: Int
) {
    Home(
        route = "home",
        icon = Icons.Default.Home,
        titleId = R.string.home
    ),
    Playlists(
        route = "Playlists",
        icon = Icons.Default.LibraryMusic,
        titleId = R.string.playlists
    ),
    Settings(
        route = "settings",
        icon = Icons.Default.Settings,
        titleId = R.string.settings

    ),
    Search(
        route = "search",
        icon = Icons.Default.Search,
        titleId = R.string.search

    ),
    Favorite(
        route = "favorite",
        icon = Icons.Default.Favorite,
        titleId = R.string.favorite

    )

}