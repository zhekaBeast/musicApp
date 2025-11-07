package com.example.musicapp.ui.search

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.musicapp.R
import com.example.musicapp.domain.models.Track
import com.example.musicapp.ui.navigation.RouteCreator
import kotlinx.coroutines.delay
import org.koin.compose.koinInject

@Composable
fun SearchScreen(navController: NavHostController) {
    val viewModel: SearchViewModel = koinInject()
    val searchState by viewModel.allTracksScreenState.collectAsState()
    SearchScreenContent(
        fetchSearchSong = viewModel::fetchData,
        searchState = searchState,
        resetSearchState = viewModel::resetState,
        navController = navController
    )
}
@Composable
internal fun SearchScreenContent(fetchSearchSong: (String) -> Unit, searchState: SearchState, resetSearchState: () -> Unit, navController: NavHostController ) {
    var text by remember { mutableStateOf("") }
    LaunchedEffect(text) {
        delay(500)
        if (text.isBlank()){
            resetSearchState()
        }else {
            fetchSearchSong(text)
        }
    }
    Column {
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(all = 16.dp),
            singleLine = true,
            placeholder = {
                Text(
                    stringResource(R.string.search),
                    modifier = Modifier.alpha(0.7f)
                )
            },
            //label = stringResource(R.string.search),
            shape = RoundedCornerShape(8.dp),
            value = text,
            onValueChange = {
                text = it
                resetSearchState()
            },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Filled.Search,
                    contentDescription = stringResource(R.string.search),
                    modifier = Modifier.alpha(0.7f)
                )
            },
            trailingIcon = {
                if (text != "")
                    IconButton(onClick = {
                        text = ""
                    }) {

                        Icon(
                            imageVector = Icons.Filled.Close,
                            contentDescription = stringResource(R.string.clear_search),
                            modifier = Modifier.alpha(0.7f)
                        )
                    }
            }
        )
        when (val state = searchState) {
            is SearchState.Error -> {
                val error = state.error
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("Ошибка: $error", color = Color.Red)
                }
            }

            SearchState.Loading -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }

            SearchState.Initial -> {
            }

            is SearchState.Success -> {
                val tracks = state.foundList
                if (tracks.isEmpty()) {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text(stringResource(R.string.no_found))
                    }

                } else {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(count = tracks.size) { index ->
                            TrackListItem(track = tracks[index], navController)
                            HorizontalDivider(thickness = 0.5.dp)
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun TrackListItem(track: Track, navController: NavHostController) {
    Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
        IconButton(
            onClick = {navController.navigate(RouteCreator.createTrackDetailsRoute(track.id))},
            content = {
                Icon(
                    imageVector = Icons.Default.PlayArrow,
                    contentDescription = "",
                )
            }
        )
        Column {
            Text(track.trackName, fontWeight = FontWeight.Bold)
            Row {
                Text(track.artistName)
                Text(modifier = Modifier.padding(start = 8.dp), text = track.trackTime)
            }
        }
    }
}

