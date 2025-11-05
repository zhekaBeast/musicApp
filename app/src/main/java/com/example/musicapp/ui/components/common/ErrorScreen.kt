package com.example.musicapp.ui.components.common

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

object DisplayError{
    fun displayToastError(context: Context, error: String) {
        Toast.makeText(context, "Error: $error", Toast.LENGTH_LONG).show()
    }
    @Composable
    fun displayErrorScreen(error: String, onReload:(()->Unit)? = null){
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Column {
                Text("Ошибка: $error", color = Color.Red)
                if (onReload != null) {
                    Button(onClick = {onReload()}){
                        Text("Перезагрузить")
                    }
                }
            }
        }
    }

}

