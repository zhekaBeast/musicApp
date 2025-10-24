package com.example.musicapp.playingAround

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class TrackActivity : ComponentActivity() {

    private val viewModel: TrackViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Выставим просто пустой Compose-экран
        setContent {
            EmptyScreen()
        }
        lifecycleScope.launch {
            viewModel.getLoadingStateFlow()
                .collect { isLoading -> changeProgressBarVisibility(isLoading) }
        }
    }


    // Метод для отображения прогресса
    private fun changeProgressBarVisibility(visible: Boolean) {
        Log.d("TEST", "changeProgressBarVisibility: visible-$visible")
        // Пока просто пишем лог
    }
}

@Composable
fun EmptyScreen() {
    Box(modifier = Modifier.fillMaxSize()) {
        // Пока пусто
    }
}