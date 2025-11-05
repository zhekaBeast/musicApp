package com.example.musicapp.ui.newPlaylist

import android.content.Context
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.musicapp.R
import com.example.musicapp.ui.components.common.DisplayError
import org.koin.compose.koinInject


@Composable
fun NewPlaylistScreen(navController: NavController) {
    val vm: NewPlaylistViewModel = koinInject()
    val state by vm.newPlaylistState.collectAsState()
    NewPlaylistContent(
        createPlaylist = vm::createNewPlayList,
        navController,
        state,
        LocalContext.current
    )
}

@Composable
fun NewPlaylistContent(
    createPlaylist: (playlistName: String, playlistDescription: String) -> Unit,
    navController: NavController,
    newPlaylistState: NewPlaylistState,
    context: Context
) {
    var playlistName by remember { mutableStateOf("") }
    var playlistDescription by remember { mutableStateOf("") }
    when(val state = newPlaylistState) {
        is NewPlaylistState.Error -> DisplayError.displayToastError(context, state.error)
        NewPlaylistState.Initial,
        NewPlaylistState.Loading ->  Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Column {
                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    singleLine = true,
                    placeholder = {
                        Text(
                            stringResource(R.string.playlist_name),
                            modifier = Modifier.alpha(0.7f)
                        )
                    },
                    shape = RoundedCornerShape(8.dp),
                    value = playlistName,
                    onValueChange = { playlistName = it }
                )
                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    singleLine = true,
                    placeholder = {
                        Text(
                            stringResource(R.string.playlist_name),
                            modifier = Modifier.alpha(0.7f)
                        )
                    },
                    shape = RoundedCornerShape(8.dp),
                    value = playlistDescription,
                    onValueChange = { playlistDescription = it }
                )
                Button(
                    onClick = { createPlaylist(playlistName, playlistDescription) }
                ) {
                    Text("Создать")
                }
            }
        }
        NewPlaylistState.Success -> navController.popBackStack()
    }

}