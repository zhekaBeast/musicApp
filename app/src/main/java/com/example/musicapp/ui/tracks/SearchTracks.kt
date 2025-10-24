package com.example.musicapp.ui.tracks

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.musicapp.data.network.Track
import com.example.musicapp.ui.search.SearchState
import com.example.musicapp.ui.search.SearchViewModel

@Composable
fun SearchTracks(modifier: Modifier=Modifier, viewModel: SearchViewModel, expression: String){
    val screenState by viewModel.allTracksScreenState.collectAsState()

    LaunchedEffect(key1=true) {
        viewModel.fetchData(expression)
    }

    when(val state = screenState) {
        is SearchState.Error -> {
            val error = state.error
            Box(modifier= modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("Ошибка: $error", color = Color.Red)
            }
        }
        SearchState.Loading -> {
            Box(modifier= modifier.fillMaxSize(), contentAlignment = Alignment.Center){
                CircularProgressIndicator()
            }
        }
        SearchState.Initial -> {

        }
        is SearchState.Success -> {
            val tracks = state.foundList
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(count=tracks.size){index->
                    TrackListItem(track=tracks[index])
                    HorizontalDivider(thickness = 0.5.dp)
                }
            }
        }
    }
}


@Composable
fun TrackListItem(track: Track){
    Row(modifier= Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically){
        IconButton(
            onClick = {},
            content = { Icon(
                imageVector = Icons.Default.PlayArrow,
                contentDescription ="",
            ) }
        )
        Column(){
            Text(track.trackName, fontWeight = FontWeight.Bold)
            Row(){
                Text(track.artistName)
                Text(modifier = Modifier.padding(start = 8.dp), text = track.trackTime)
            }
        }
    }
}

