package com.example.musicapp.ui.playlists

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.musicapp.R
import com.example.musicapp.data.datasource.dto.Playlist
import com.example.musicapp.ui.navigation.AppScreen
import com.example.musicapp.ui.navigation.RouteCreator
import org.koin.compose.koinInject

@Composable
fun PlaylistsScreen(navController: NavController) {
    val vm: PlaylistsViewModel = koinInject()
    val state by vm.state.collectAsState()
    PlaylistsScreenContent(state, navController)
}

@Composable
private fun PlaylistsScreenContent(
    playlistsState: PlaylistsState,
    navController: NavController
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp), contentAlignment = Alignment.Center
    ) {

        when (val state = playlistsState) {
            is PlaylistsState.Error -> {
                Text("Ошибка: ${state.message}", color = Color.Red)
            }

            PlaylistsState.Loading -> {
                CircularProgressIndicator()
            }

            is PlaylistsState.Success -> {
                val playlists by state.playlists.collectAsState(emptyList())
                PlaylistList(playlists, navController)
            }
        }
        FloatingActionButton(
            modifier = Modifier
                .align(Alignment.BottomEnd),
            onClick = { navController.navigate(AppScreen.NewPlaylist.route) },
            containerColor = Color.Gray,
            contentColor = Color.White,
            shape = CircleShape
        ) {
            Icon(
                imageVector = Icons.Filled.Add,
                contentDescription = stringResource(R.string.add_playlist)
            )
        }
    }
}

@Composable
private fun PlaylistList(playlists: List<Playlist>, navController: NavController) {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(playlists.size) { index ->
            PlaylistListItem(playlist = playlists[index]) {
                navController.navigate(
                    RouteCreator.createPlaylistRoute(playlists[index].id)
                )
            }
            HorizontalDivider(thickness = 0.5.dp)
        }
    }
}

@Composable
private fun PlaylistListItem(playlist: Playlist, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = { onClick.invoke() }),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Image(
            modifier = Modifier.size(48.dp),
            painter = painterResource(id = R.drawable.library_light),
            contentDescription = playlist.name,
            colorFilter = ColorFilter.tint(Color.Gray)
        )
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(start = 16.dp),
            horizontalAlignment = Alignment.Start

        ) {
            Text(playlist.name, fontSize = 16.sp)
            val text = "${playlist.trackIds.size} tracks"
            Text(text, fontSize = 11.sp, color = Color.Gray)
        }
    }
}

