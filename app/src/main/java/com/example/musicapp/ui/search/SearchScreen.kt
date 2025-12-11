package com.example.musicapp.ui.search

import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.History
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
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
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
    val searchHistory by viewModel.searchHistory.collectAsState()
    SearchScreenContent(
        fetchSearchSong = viewModel::fetchData,
        searchState = searchState,
        resetSearchState = viewModel::resetState,
        navController = navController,
        searchHistory = searchHistory
    )
}
@Composable
internal fun SearchScreenContent(
    fetchSearchSong: (String) -> Unit, searchState: SearchState,
    resetSearchState: () -> Unit, navController: NavHostController,
    searchHistory: List<String>
) {
    var isFocused by remember { mutableStateOf(false) }
    val focusRequester = remember { FocusRequester() }
    var text by remember { mutableStateOf("") }
    LaunchedEffect(text) {
        delay(500)
        if (text.isBlank()){
            resetSearchState()
        }else {
            fetchSearchSong(text)
        }
    }
    Column(modifier = Modifier.padding(16.dp)) {
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .focusRequester(focusRequester)
                .onFocusChanged{focusState ->
                    isFocused = focusState.isFocused
                },
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

        if (isFocused && text.isEmpty() && searchHistory.isNotEmpty()) {
            HistoryRequests(
                historyList = searchHistory,
                onClick = { word ->
                    text = word
                }
            )
        }

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

            is SearchState.Initial -> {
            }

            is SearchState.Success -> {
                val tracks = state.foundList
                if (tracks.isEmpty()) {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text(stringResource(R.string.no_found))
                    }

                } else {
                    TrackList(tracks, navController)
                }
            }
        }
    }
}
@Composable
fun TrackList(tracks: List<Track>, navController: NavHostController){
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(count = tracks.size) { index ->
            TrackListItem(
                track = tracks[index],
            ){
                navController.navigate(RouteCreator.
                createTrackDetailsRoute(tracks[index].id))
            }
            HorizontalDivider(thickness = 0.5.dp)
        }
    }
}

@Composable
fun TrackListItem(track: Track, onClick: () -> Unit) {
    Row(modifier = Modifier.fillMaxWidth().clickable(
        interactionSource = remember { MutableInteractionSource()},
        indication = LocalIndication.current,
        onClick = onClick
    ), verticalAlignment = Alignment.CenterVertically) {
        IconButton(
            onClick = onClick,
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

@Composable
fun HistoryRequests(
    historyList: List<String>,
    onClick: (String) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(max = 200.dp)
            .border(
                width = 1.dp,
                color = Color.Gray,
                shape = RoundedCornerShape(4.dp)
            )
    ) {
        items(historyList.size) { index ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onClick(historyList[index]) }
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Filled.History,
                    contentDescription = null,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = historyList[index])
            }
            if (historyList[index] != historyList.last()) {
                HorizontalDivider(thickness = 0.5.dp)
            }
        }
    }
}
