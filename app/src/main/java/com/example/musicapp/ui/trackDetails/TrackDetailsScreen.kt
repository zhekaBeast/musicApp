package com.example.musicapp.ui.trackDetails

import android.R.attr.text
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import coil.compose.AsyncImage
import com.example.musicapp.R
import com.example.musicapp.data.datasource.dto.Playlist
import com.example.musicapp.domain.models.Track
import com.example.musicapp.ui.components.common.DisplayError
import kotlinx.coroutines.delay
import org.koin.compose.koinInject


@Composable
fun TrackDetailsScreen(trackId: Long) {
    val vm = koinInject<TrackDetailsViewModel>()
    val trackState by vm.trackState.collectAsState()
    val playlistsState by vm.playlistsState.collectAsState()

    TrackDetailsScreenContent(
        trackState,
        playlistsState,
        vm::loadTrack,
        vm::toggleFavorite,
        vm::addTrackToPlaylist,
        vm::loadPlaylists,
        vm::removeTrackFromPlaylist,
        trackId
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TrackDetailsScreenContent(
    trackDetailsState: TrackState,
    playlistsState: PlaylistsState,
    loadTrack: (id: Long) -> Unit,
    addTrackToFavorite: () -> Unit,
    addTrackToPlaylist: (playlistId: Long) -> Unit,
    loadPlaylists: () -> Unit,
    removeTrackToPlaylist: (playlistId: Long) -> Unit,
    trackId: Long
) {
    val sheetState = rememberModalBottomSheetState()
    var showBottomSheet by remember { mutableStateOf(false) }

    LaunchedEffect(text) {
        delay(500)
        loadTrack(trackId)
    }
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        when (val state = trackDetailsState) {
            is TrackState.Error -> {
                DisplayError.displayErrorScreen(state.error)
            }

            TrackState.Loading -> {
                CircularProgressIndicator()
            }

            is TrackState.Success -> {
                TrackDetails(
                    state.track,
                    addTrackToFavorite,
                    {
                        showBottomSheet = true
                        loadPlaylists.invoke()
                    },
                )
            }

            TrackState.NotFound -> {
                Text(stringResource(R.string.track_not_found))
            }

        }
        if (showBottomSheet) {
            ModalBottomSheet(
                onDismissRequest = { showBottomSheet = false }, sheetState = sheetState
            ) {
                when (val state = playlistsState) {
                    is PlaylistsState.Error -> {
                        DisplayError.displayErrorScreen(state.message)
                    }

                    PlaylistsState.Loading -> {
                        CircularProgressIndicator()
                    }

                    is PlaylistsState.Loaded -> {
                        val playlists by state.playlists.collectAsState(emptyList())
                        PlaylistsOption(
                            { showBottomSheet = false },
                            currentTrackId = trackId,
                            addTrackToPlaylist = addTrackToPlaylist,
                            deleteTrackFromPlaylist = removeTrackToPlaylist,
                            playlists = playlists
                        )
                    }
                }
            }
        }

    }
}


@Composable
private fun TrackDetails(
    track: Track, addTrackToFavorite: () -> Unit, showBottomSheet: () -> Unit
) {
    val iconColor = if (track.favorite) Color.Red else Color.Gray

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 32.dp, start = 16.dp, end = 16.dp),
    ) {
        Box(
            contentAlignment = Alignment.Center, modifier = Modifier
                .weight(3f)
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
            modifier = Modifier
                .weight(5f)
                .fillMaxWidth()
        ) {
            Text(track.trackName, fontSize = 22.sp)
            Text(track.artistName, fontSize = 14.sp)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 48.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                Button(
                    onClick = showBottomSheet,
                    shape = CircleShape,
                    modifier = Modifier.size(80.dp),
                    colors = ButtonColors(
                        containerColor = Color.Black,
                        contentColor = Color.White,
                        disabledContainerColor = Color.Black,
                        disabledContentColor = Color.Black
                    )
                ) {
                    Image(
                        modifier = Modifier.fillMaxSize(),
                        painter = painterResource(id = R.drawable.library_light),
                        contentDescription = stringResource(R.string.playlist_name),
                        colorFilter = ColorFilter.tint(Color.Gray)
                    )
                }
                Button(
                    onClick = { addTrackToFavorite() },
                    shape = CircleShape,
                    modifier = Modifier.size(80.dp),
                    colors = ButtonColors(
                        containerColor = Color.Black,
                        contentColor = Color.White,
                        disabledContainerColor = Color.Black,
                        disabledContentColor = Color.Black
                    )
                )

                {
                    Image(
                        modifier = Modifier.fillMaxSize(),
                        painter = painterResource(id = R.drawable.favorite_light),
                        contentDescription = stringResource(R.string.playlist_name),
                        colorFilter = ColorFilter.tint(iconColor)
                    )
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 48.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(stringResource(R.string.duration), color = Color.Gray)
                Text(track.trackTime)
            }

        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun PlaylistsOption(
    onDismissRequest: () -> Unit,
    currentTrackId: Long,
    addTrackToPlaylist: (playlistId: Long) -> Unit,
    deleteTrackFromPlaylist: (playlistId: Long) -> Unit,
    playlists: List<Playlist>,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        Box(Modifier.fillMaxWidth(), contentAlignment = Alignment.Center)
        {
            Text(
                stringResource(R.string.add_to_playlist),
                style = MaterialTheme.typography.headlineSmall
            )
        }
        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(playlists.size) { index ->
                PlaylistListItem(
                    playlist = playlists[index],
                    addTrackToPlaylist = addTrackToPlaylist,
                    deleteTrackFromPlaylist = deleteTrackFromPlaylist,
                    currentTrackId = currentTrackId
                )
                HorizontalDivider(thickness = 0.5.dp)
            }
        }

        TextButton(
            onClick = onDismissRequest, modifier = Modifier.fillMaxWidth()
        ) {
            Text("Закрыть")
        }
    }
}

@Composable
private fun PlaylistListItem(
    playlist: Playlist,
    addTrackToPlaylist: (playlistId: Long) -> Unit,
    deleteTrackFromPlaylist: (playlistId: Long) -> Unit,
    currentTrackId: Long
) {
    val isTrackInPlaylist = playlist.trackIds.contains(currentTrackId)
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = {
                if (isTrackInPlaylist) {
                    deleteTrackFromPlaylist(playlist.id)
                } else {
                    addTrackToPlaylist(playlist.id)
                }
            }),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Box(modifier = Modifier.size(205.dp)) {
            if (playlist.coverImageUri != null) {
                AsyncImage(
                    model = playlist.coverImageUri.toUri(),
                    contentDescription = stringResource(R.string.playlist_cover),
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            } else {
                Image(
                    modifier = Modifier.size(48.dp),
                    painter = painterResource(id = R.drawable.library_light),
                    contentDescription = playlist.name,
                    colorFilter = ColorFilter.tint(Color.Gray)
                )
            }
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
        Spacer(modifier = Modifier)

        if (isTrackInPlaylist) {
            Icon(
                imageVector = Icons.Default.Check,
                contentDescription = stringResource(R.string.track_added),
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .size(32.dp)
                    .padding(end = 8.dp)
            )
        } else {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = stringResource(R.string.add_to_playlist),
                tint = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier
                    .size(32.dp)
                    .padding(end = 8.dp)
            )
        }
    }
}
