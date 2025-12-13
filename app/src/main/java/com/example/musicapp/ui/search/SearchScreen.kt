package com.example.musicapp.ui.search

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.musicapp.R
import com.example.musicapp.ui.components.common.TrackList
import kotlinx.coroutines.delay
import org.koin.compose.koinInject

@Composable
fun SearchScreen(navController: NavController) {
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
private  fun SearchScreenContent(
    fetchSearchSong: (String) -> Unit, searchState: SearchState,
    resetSearchState: () -> Unit, navController: NavController,
    searchHistory: List<String>
) {
    Column() {
        SearchTextField(
            fetchSearchSong,
            resetSearchState,
            searchHistory
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

            is SearchState.Initial -> {
            }

            is SearchState.Success -> {
                TrackList(state.foundList, navController)
            }
        }
    }
}

@Composable
private  fun SearchTextField(
    fetchSearchSong: (String) -> Unit,
    resetSearchState: () -> Unit,
    searchHistory: List<String>
) {
    var isFocused by remember { mutableStateOf(false) }
    val focusRequester = remember { FocusRequester() }
    var text by remember { mutableStateOf("") }
    LaunchedEffect(text) {
        delay(500)
        if (text.isBlank()) {
            resetSearchState()
        } else {
            fetchSearchSong(text)
        }
    }
    Column(modifier = Modifier.padding(16.dp)) {
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .focusRequester(focusRequester)
                .onFocusChanged { focusState ->
                    isFocused = focusState.isFocused
                },
            singleLine = true,
            placeholder = {
                Text(
                    stringResource(R.string.search),
                    modifier = Modifier.alpha(0.7f)
                )
            },
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
    }
}

@Composable
private  fun HistoryRequests(
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
