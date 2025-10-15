package com.example.musicapp.playingAround

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


@Composable
fun DetailsScreen(movie: Movie) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Text(text = movie.id)
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = movie.title)
    }
}