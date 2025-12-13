package com.example.musicapp.ui.components.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.musicapp.R
import com.example.musicapp.domain.models.Track
import com.example.musicapp.ui.navigation.RouteCreator

@Composable
fun TrackList(tracks: List<Track>, navController: NavController) {
    if (tracks.isEmpty()) {
        Box(
            modifier = Modifier.Companion.fillMaxSize(),
            contentAlignment = Alignment.Companion.Center
        ) {
            Text(stringResource(R.string.no_found))
        }

    } else {
        LazyColumn(
            modifier = Modifier.Companion.fillMaxSize(),
            contentPadding = PaddingValues(vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(count = tracks.size) { index ->
                TrackListItem(
                    track = tracks[index],
                ) {
                    navController.navigate(
                        RouteCreator.createTrackDetailsRoute(tracks[index].id)
                    )
                }
                HorizontalDivider(thickness = 0.5.dp)
            }
        }
    }

}