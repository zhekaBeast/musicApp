package com.example.musicapp.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.musicapp.navigation.NavGraph
import com.example.musicapp.ui.components.navigation.Loading
import com.example.musicapp.ui.components.navigation.TopBar

@Composable
fun MainContainer() {
    val navController = rememberNavController()
    var isNavigating by remember { mutableStateOf(false) }
    val currentRoute by navController.currentBackStackEntryAsState()
    LaunchedEffect(currentRoute) {
        isNavigating = false
    }
    // TODO:корректно обработать нажатие кнопки назад
//    BackHandler() {
//        isNavigating=true
//        Log.d("BackHandler","bh")
//    }
    Scaffold(
        topBar = {
            TopBar(navController, onNavigate = { isNavigating = true })
        }
    ) { innerPadding ->
        if (isNavigating) {
            Loading()
        } else {
            NavGraph(
                modifier = Modifier.padding(innerPadding),
                navController,
                onNavigate = { isNavigating = true }
            )
        }
    }
}
