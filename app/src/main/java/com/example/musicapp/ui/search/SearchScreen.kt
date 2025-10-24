package com.example.musicapp.ui.search

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.musicapp.R
import com.example.musicapp.ui.tracks.SearchTracks

@Composable
fun SearchScreen(navController: NavHostController, viewModel: SearchViewModel) {
    var text by remember { mutableStateOf("") }
    Column() {
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
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
        SearchTracks(viewModel=viewModel, expression = text)
    }
}