package com.example.musicapp.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LibraryMusic
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.musicapp.R


fun createTrackDetailsRoute(trackId: Long) = "trackDetails/$trackId"
fun createPlaylistRoute(playlistId: Long) = "playlist/$playlistId"
enum class AppScreen(
    val route: String,
    val icon: ImageVector,
    val titleId: Int,
    val showTitle: Boolean
) {

    Home(
        route = "home",
        icon = Icons.Default.Home,
        titleId = R.string.home,
        showTitle = true
    ),
    Playlists(
        route = "playlists",
        icon = Icons.Default.LibraryMusic,
        titleId = R.string.playlists,
        showTitle = true
    ),
    Settings(
        route = "settings",
        icon = Icons.Default.Settings,
        titleId = R.string.settings,
        showTitle = true

    ),
    Search(
        route = "search",
        icon = Icons.Default.Search,
        titleId = R.string.search,
        showTitle = true

    ),
    Favorite(
        route = "favorite",
        icon = Icons.Default.Favorite,
        titleId = R.string.favorite,
        showTitle = true
    ),
    NewPlaylist(
        route = "newPlaylist",
        icon = Icons.Default.Home,
        titleId = R.string.new_playlist,
        showTitle = true
    ),

    Playlist(
        route = "playlist/{playlistId}",
        icon = Icons.Default.Home,
        titleId = R.string.home,
        showTitle = false
    ),

    TrackDetails(
        route = "trackDetails/{trackId}",
        icon = Icons.Default.Home,
        titleId = R.string.home,
        showTitle = false
    ),

    Null(
        route = "",
        icon = Icons.Default.Home,
        titleId = R.string.home,
        showTitle = false
    )
}
