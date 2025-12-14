package com.example.musicapp.ui.playlists

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.musicapp.R
import com.example.musicapp.domain.models.Playlist
import com.example.musicapp.ui.components.common.ConfirmDialog
import com.example.musicapp.ui.components.common.UriPicture
import com.example.musicapp.ui.navigation.AppScreen
import com.example.musicapp.ui.navigation.RouteCreator
import org.koin.compose.koinInject

@Composable
fun PlaylistsScreen(navController: NavController) {
    val vm: PlaylistsViewModel = koinInject()
    val state by vm.state.collectAsState()
    PlaylistsScreenContent(
        playlistsState = state,
        navController = navController,
        onDeletePlaylist = vm::deletePlaylist
    )
}

@Composable
private fun PlaylistsScreenContent(
    playlistsState: PlaylistsState,
    navController: NavController,
    onDeletePlaylist: (Long) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
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
                PlaylistList(
                    playlists = playlists,
                    navController = navController,
                    onDeletePlaylist = onDeletePlaylist
                )
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
private fun PlaylistList(
    playlists: List<Playlist>,
    navController: NavController,
    onDeletePlaylist: (Long) -> Unit
) {
    var playlistToDelete by remember { mutableStateOf<Playlist?>(null) }

    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(playlists.size) { index ->
            val playlist = playlists[index]
            PlaylistListItem(
                playlist = playlist,
                onClick = {
                    navController.navigate(
                        RouteCreator.createPlaylistRoute(playlist.id)
                    )
                },
                onLongClick = { playlistToDelete = playlist }
            )
            HorizontalDivider(thickness = 0.5.dp)
        }
    }

    playlistToDelete?.let { playlist ->
        ConfirmDialog(
            title = stringResource(R.string.delete_playlist),
            text = stringResource(R.string.confirm_delete_playlist),
            confirmButtonText = stringResource(R.string.delete),
            dismissButtonText = stringResource(R.string.cancel),
            onConfirm = {
                onDeletePlaylist(playlist.id)
                playlistToDelete = null
            },
            onDismiss = { playlistToDelete = null }
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun PlaylistListItem(
    playlist: Playlist,
    onClick: () -> Unit,
    onLongClick: () -> Unit = {}
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .combinedClickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = LocalIndication.current,
                onClick = onClick,
                onLongClick = onLongClick
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Box(Modifier.size(48.dp).padding(4.dp)) {
            UriPicture(playlist.coverImageUri)
        }
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

