package com.example.musicapp.playingAround

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.google.gson.Gson

data class Movie(
    val id: String,
    val title: String,
)
@Composable
fun MoviesScreen(navController: NavController, gson: Gson, movies: List<Movie>) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        for (movie in movies) {
            Text(
                text = movie.title,
                modifier = Modifier
                    .clickable {
                        val movieJson = gson.toJson(movie)
                        navController.navigate("details/$movieJson")
                    }
            )
        }
    }
}