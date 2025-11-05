package com.example.musicapp.ui.trackDetails

import android.R.attr.text
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.musicapp.R
import com.example.musicapp.data.datasource.dto.Track
import com.example.musicapp.ui.components.common.DisplayError
import kotlinx.coroutines.delay
import org.koin.compose.koinInject


@Composable
fun TrackDetailsScreen(trackId: Long?) {
    val vm = koinInject<TrackDetailsViewModel>()
    val state by vm.trackState.collectAsState()
    TrackDetailsScreenContent(state, vm::getTrackById, trackId ?: -1)
}

@Composable
private fun TrackDetailsScreenContent(
    trackDetailsState: TrackDetailsState,
    loadTrack: (id:Long) -> Unit,
    addTrackToFavorite: (id:Long) -> Unit,
    addTrackToPlaylist: (trackId:Long, playlistId:Long) -> Unit,
    trackId: Long
) {
    LaunchedEffect(text) {
        delay(500)
        loadTrack(trackId)
    }
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        when (val state = trackDetailsState) {
            is TrackDetailsState.Error -> {
                DisplayError.displayErrorScreen(state.error)
            }

            TrackDetailsState.Loading -> {
                CircularProgressIndicator()
            }

            TrackDetailsState.Initial -> {
            }

            is TrackDetailsState.Success -> {
                TrackDetails(state.foundTrack,
                    addTrackToFavorite,
                    addTrackToPlaylist)
            }

            TrackDetailsState.NotFound -> {
                Text(stringResource(R.string.track_not_found))
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TrackDetails(
    track: Track,
                         addTrackToFavorite: (id:Long) -> Unit,
                         addTrackToPlaylist: (trackId:Long, playlistId:Long) -> Unit
) {
    val sheetState = rememberModalBottomSheetState()
    var showBottomSheet by remember { mutableStateOf(false) }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 32.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(track.trackName)
        Text(track.artistName)
        Text(track.trackTime)
        Button(onClick = { addTrackToFavorite(track.id) }) { Text(stringResource(R.string.add_track_to_playlist)) }
        Button(onClick = { addTrackToFavorite(track.id) })
        { Text(stringResource(R.string.add_track_to_favorite)) }

    }
    if (showBottomSheet) {
        ModalBottomSheet(
            onDismissRequest = { showBottomSheet = false },
            sheetState = sheetState
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text("Заголовок", style = MaterialTheme.typography.headlineSmall)
                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = { /* действие */ },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Опция 1")
                }

                Spacer(modifier = Modifier.height(8.dp))

                Button(
                    onClick = { /* действие */ },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Опция 2")
                }

                Spacer(modifier = Modifier.height(16.dp))

                TextButton(
                    onClick = { showBottomSheet = false },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Закрыть")
                }
            }
        }
    }
}

//@Preview
//@Composable
//fun TrackDetailsP(track: Track?) {
//    TrackDetails(Track(0,"Upal", "Auktion", "12:23"))
//}