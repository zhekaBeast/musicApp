package com.example.musicapp.ui.playlist

import android.R.attr.text
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.musicapp.R
import com.example.musicapp.ui.components.common.DisplayError
import com.example.musicapp.ui.components.common.TrackList
import com.example.musicapp.ui.components.common.UriPicture
import kotlinx.coroutines.delay
import org.koin.compose.koinInject

@Composable
fun PlaylistScreen(navController: NavController, playlistId: Long) {
    val vm = koinInject<PlaylistViewModel>()
    val playlistState by vm.playlistState.collectAsState()

    PlaylistScreenContent(
        playlistState,
        vm::loadPlaylistWithTracks,
        vm::deletePlaylist,
        playlistId,
        navController
    )
}

@Composable
private fun PlaylistScreenContent(
    playlistWithTracksState: PlaylistWithTracksState,
    loadPlaylistWithTracks: (id: Long) -> Unit,
    deletePlaylist: () -> Unit,
    playlistId: Long,
    navController: NavController
) {
    LaunchedEffect(text) {
        delay(500)
        loadPlaylistWithTracks(playlistId)
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp), contentAlignment = Alignment.Center
    ) {
        when (val state = playlistWithTracksState) {
            is PlaylistWithTracksState.Error -> {
                DisplayError.DisplayErrorScreen(state.message)
            }

            PlaylistWithTracksState.Loading -> {
                CircularProgressIndicator()
            }

            PlaylistWithTracksState.NotFound -> {
                Text(stringResource(R.string.no_found))
            }

            is PlaylistWithTracksState.Loaded -> {
                Column(modifier = Modifier.fillMaxSize()) {
                    Box(modifier = Modifier.weight(5f)) {
                        PlaylistDetails(
                            playlistWithTracks = state.playlistWithTracks,
                            onDelete = {
                                deletePlaylist()
                                navController.popBackStack()
                            }
                        )
                    }
                    Box(modifier = Modifier.weight(3f).fillMaxWidth(), Alignment.Center) {
                        if(state.playlistWithTracks.tracks.isEmpty()){
                            Text(stringResource(R.string.empty_playlist))
                        }else {
                            TrackList(state.playlistWithTracks.tracks, navController)
                        }
                    }
                }
            }
        }
    }
}


@Composable
private fun PlaylistDetails(
    playlistWithTracks: PlaylistWithTracks,
    onDelete: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        UriPicture(playlistWithTracks.playlist.coverImageUri)
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(playlistWithTracks.playlist.name, fontSize = 22.sp)
            Text(playlistWithTracks.playlist.description, fontSize = 14.sp)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = androidx.compose.foundation.layout.Arrangement.End
            ) {
                IconButton(onClick = onDelete) {
                    Icon(
                        imageVector = Icons.Filled.Delete,
                        contentDescription = stringResource(R.string.delete_playlist)
                    )
                }
            }
            Row {
                Text(
                    "${playlistWithTracks.totalDuration} " + stringResource(R.string.minutes),
                    fontSize = 14.sp
                )
                Spacer(Modifier.width(16.dp))
                Text(
                    "${playlistWithTracks.tracks.size} " + stringResource(R.string.tracks),
                    fontSize = 14.sp
                )
            }
        }
    }
}

