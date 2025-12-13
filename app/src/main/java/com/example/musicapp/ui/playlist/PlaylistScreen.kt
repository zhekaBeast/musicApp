package com.example.musicapp.ui.playlist

import android.R.attr.text
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import com.example.musicapp.ui.components.common.DisplayError
import com.example.musicapp.ui.components.common.TrackList
import kotlinx.coroutines.delay
import org.koin.compose.koinInject

@Composable
fun PlaylistScreen(navController: NavController, playlistId: Long) {
    val vm = koinInject<PlaylistViewModel>()
    val playlistState by vm.playlistState.collectAsState()

    PlaylistScreenContent(
        playlistState,
        vm::loadPlaylistWithTracks,
        playlistId,
        navController
    )
}

@Composable
private fun PlaylistScreenContent(
    playlistWithTracksState: PlaylistWithTracksState,
    loadPlaylistWithTracks: (id: Long) -> Unit,
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
                DisplayError.displayErrorScreen(state.message)
            }

            PlaylistWithTracksState.Loading -> {
                CircularProgressIndicator()
            }

            PlaylistWithTracksState.NotFound -> {
                Text(stringResource(R.string.no_found))
            }

            is PlaylistWithTracksState.Loaded -> {
                Column(modifier = Modifier.fillMaxSize()) {
                    Box(modifier = Modifier.weight(4f)) {
                        PlaylistDetails(state.playlistWithTracks)
                    }
                    Box(modifier = Modifier.weight(3f), Alignment.Center) {
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
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        ) {
            Image(
                modifier = Modifier.size(48.dp),
                painter = painterResource(id = R.drawable.library_light),
                contentDescription = stringResource(R.string.playlist_name),
                colorFilter = ColorFilter.tint(Color.Gray)
            )
        }
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(playlistWithTracks.playlist.name, fontSize = 22.sp)
            Text(playlistWithTracks.playlist.description, fontSize = 14.sp)
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