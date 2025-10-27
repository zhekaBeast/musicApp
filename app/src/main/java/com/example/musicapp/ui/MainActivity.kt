package com.example.musicapp.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.musicapp.ui.theme.MusicAppTheme

class MainActivity : ComponentActivity() {
    //private val viewModel: SearchViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MusicAppTheme {
                //AllTracksScreen(viewModel=viewModel)
                MainContainer()
            }
        }
    }
}




