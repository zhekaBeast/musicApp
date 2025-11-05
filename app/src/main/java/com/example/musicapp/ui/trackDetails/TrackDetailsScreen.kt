package com.example.musicapp.ui.trackDetails

import android.R.attr.text
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.musicapp.data.datasource.remote.dto.Track
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
    state: TrackDetailsState,
    loadTrack: (Long) -> Unit,
    trackId: Long
) {
    LaunchedEffect(text) {
        delay(500)
        loadTrack(trackId)
    }
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        when (state) {
            is TrackDetailsState.Error -> {
                val error = state.error
                Text("Ошибка: $error", color = Color.Red)
            }

            TrackDetailsState.Loading -> {
                CircularProgressIndicator()
            }

            TrackDetailsState.Initial -> {
            }

            is TrackDetailsState.Success -> {
                TrackDetails(state.foundTrack)
            }
        }
    }
}


@Composable
private fun TrackDetails(track: Track?) {
    if (track == null) {
        Text("TODO:написать текст")//TODO
    } else {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 32.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(track.trackName)
            Text(track.artistName)
            Text(track.trackTime)
        }
    }
}

@Preview
@Composable
fun TrackDetailsP(track: Track?) {
    TrackDetails(Track(0,"Upal", "Auktion", "12:23"))
}