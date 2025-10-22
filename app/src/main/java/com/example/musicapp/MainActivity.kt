package com.example.musicapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Stop
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.material3.rememberSliderState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.example.musicapp.navigation.NavGraph
import com.example.musicapp.ui.components.navigation.Loading
import com.example.musicapp.ui.components.navigation.TopBar
import com.example.musicapp.ui.theme.MusicAppTheme
import com.example.musicapp.ui.track.PlayerStatusState
import com.example.musicapp.ui.track.TrackScreenState
import com.example.musicapp.ui.track.TrackViewModel
import kotlinx.coroutines.delay

// В Activity:
class MainActivity : ComponentActivity() {
    private val viewModel by viewModels<TrackViewModel> { TrackViewModel.getViewModelFactory("123")}

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


@Composable
fun TrackScreen(viewModel: TrackViewModel) {
    val screenState by viewModel.trackScreenState.collectAsState()

    Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
        when (val state = screenState) {
            is TrackScreenState.Content -> {
                TrackScreenContent(viewModel, state) //1
            }

            is TrackScreenState.Loading -> {
                CircularProgressIndicator()
            }
        }
    }
}


@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun TrackScreenContent(viewModel: TrackViewModel, screenState: TrackScreenState.Content) {
    val playerStatus by viewModel.playerStatusState.collectAsState() //2
    when (val state = playerStatus){
        is PlayerStatusState.Error -> TODO()
        PlayerStatusState.Initial -> TODO()
        PlayerStatusState.Loading -> TODO()
        is PlayerStatusState.PlaybackState -> {
            val sliderState = rememberSliderState(state.progress)
            Column {
                AsyncImage(
                    model = screenState.trackModel.pictureUrl,
                    contentDescription = null
                )
                Text(screenState.trackModel.author)
                Text(screenState.trackModel.name)

                val buttonIcon = if (state.isPlaying)  Icons.Default.Stop else Icons.Default.PlayArrow
                Button(content = { Icon(
                    buttonIcon,
                    contentDescription = ""
                ) }, onClick = { viewModel.play() })   // 4

                Slider(
                    state = sliderState
                )
            }
        }
    }
    
}