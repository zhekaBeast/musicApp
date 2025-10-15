package com.example.musicapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.musicapp.navigation.NavGraph
import com.example.musicapp.ui.components.navigation.Loading
import com.example.musicapp.ui.components.navigation.TopBar
import com.example.musicapp.ui.theme.MusicAppTheme
import kotlinx.coroutines.delay

// В Activity:
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MusicAppTheme {
                MainScreen()
            }
        }
    }
}

@Composable
fun MainScreen() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    var isLoading by remember { mutableStateOf(false) }

    LaunchedEffect(navBackStackEntry) {
        if(navBackStackEntry!=null){
            isLoading=true
            delay(300)
            isLoading=false
        }
    }
    Box(modifier = Modifier.background(color = Color.White).fillMaxSize())
    Scaffold(
        topBar = {
            TopBar(navController, onNavigate = {isLoading=true})
        }
    ) { innerPadding ->
        Box() {
            NavGraph( modifier = Modifier.padding(innerPadding),  navController)
            if(isLoading) {
                Loading()
            }
        }
    }
}

