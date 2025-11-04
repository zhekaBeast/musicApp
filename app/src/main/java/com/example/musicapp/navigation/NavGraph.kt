package com.example.musicapp.navigation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.musicapp.ui.favorite.FavoriteScreen
import com.example.musicapp.ui.home.HomeScreen
import com.example.musicapp.ui.newPlaylist.NewPlaylistScreen
import com.example.musicapp.ui.playlist.PlaylistScreen
import com.example.musicapp.ui.playlists.PlaylistsScreen
import com.example.musicapp.ui.search.SearchScreen
import com.example.musicapp.ui.settings.SettingsScreen


@Composable
fun EnterAnimation(content: @Composable () -> Unit) {
    AnimatedVisibility(
        visibleState = MutableTransitionState(
            initialState = false
        ).apply { targetState = true },
        modifier = Modifier,
        enter = slideInVertically(
            initialOffsetY = { -40 }
        ) + expandVertically(
            expandFrom = Alignment.Top
        ) + fadeIn(initialAlpha = 0.3f),
        exit = slideOutVertically() + shrinkVertically() + fadeOut(),
    ) {
        content()
    }
}
@Composable
fun NavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    onNavigate: ()->Unit = {},
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
            EnterAnimation {
                HomeScreen(navController, onNavigate)
            }
        }
        composable(
            route = AppScreen.Settings.route,
        ) {
            EnterAnimation {
                SettingsScreen(navController)
            }
        }
        composable(
            route = AppScreen.Playlists.route,
        ) {
            EnterAnimation {
                PlaylistsScreen(navController)
            }
        }
        composable(
            route = AppScreen.Favorite.route,

        ) {
            EnterAnimation {
                FavoriteScreen(navController)
            }
        }

        composable(
            route = AppScreen.Search.route
        ) {
            EnterAnimation {
                SearchScreen(navController)
            }
        }

        composable(
            route = AppScreen.NewPlaylist.route
        ) {
            EnterAnimation {
                NewPlaylistScreen()
            }
        }

        composable(
            route = AppScreen.NewPlaylist.route
        ) {
            EnterAnimation {
                NewPlaylistScreen()
            }
        }

        composable(
            route = AppScreen.Playlist.route
        ) { navBackStackEntry  ->
            val playlistId = navBackStackEntry.arguments?.getString("playlistId")
            EnterAnimation {
                PlaylistScreen(playlistId = playlistId?.toLongOrNull())
            }
        }

    }
}