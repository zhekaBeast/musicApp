package com.example.musicapp.playingAround

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.google.gson.Gson


@Composable
fun BottomNavigationBar(navController: NavHostController, gson: Gson, movies: List<Movie>) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    NavigationBar {  // ← В Material 3 это NavigationBar, а не BottomAppBar
        NavigationBarItem(
            icon = { Icon(Icons.Default.List, contentDescription = "Movies") },
            label = { Text("Movies") },
            selected = currentDestination?.route == "movies",
            onClick = { navController.navigate("movies") }
        )
        val movieJson = gson.toJson(movies[2])
        NavigationBarItem(
            icon = { Icon(Icons.Default.Info, contentDescription = "Details") },
            label = { Text("Details") },
            selected = currentDestination?.route?.startsWith("details") == true,
            onClick = { navController.navigate("details/$movieJson") }
        )
    }
}