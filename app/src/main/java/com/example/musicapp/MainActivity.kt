package com.example.musicapp

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.input.indirect.onIndirectTouchEvent
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.musicapp.ui.theme.MusicAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MusicAppTheme {
                Screen()
            }
        }
    }
}

@Composable
fun Screen() {
    Column (Modifier.fillMaxSize().background(color = Color.White)){
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(64.dp)
                .background(color = Color.Blue)
        ) {
            Text(
                stringResource(R.string.AppName), modifier = Modifier
                    .padding(start = 24.dp)
                    .align(Alignment.CenterVertically),
                style = TextStyle(
                    fontSize = 22.sp,
                    color = Color.White
                    )

            )
        }
        Column(
            modifier = Modifier
        ) {
            TransitionPanel(
                R.string.search, R.drawable.search_dark, SearchActivity::class.java
            )
            TransitionPanel(
                R.string.playlists, R.drawable.library_light, PlaylistsActivity::class.java
            )
            TransitionPanel(
                R.string.favorite, R.drawable.favorite_light, FavoriteActivity::class.java
            )
            TransitionPanel(
                R.string.settings, R.drawable.settings_light, SettingsActivity::class.java
            )
        }
    }
}

@Composable
fun <T : Any> TransitionPanel(
    string_id: Int, image_id: Int, cls: Class<T>, modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val intent = Intent(context, cls)
    Button(
        onClick = { context.startActivity(intent) },
        shape = RectangleShape,
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.White,
            contentColor = Color.Black,
            disabledContainerColor = Color.LightGray,
            disabledContentColor = Color.Gray
        )
    ) {
        Row(Modifier
            .fillMaxWidth()
            .height(66.dp)
        ) {
            Image(
                painter = painterResource(image_id), contentDescription = null,
                modifier = modifier
                    .align(Alignment.CenterVertically)
                    .weight(1f)
            )
            Text(
                stringResource(string_id),
                modifier = modifier
                    .align(Alignment.CenterVertically)
                    .weight(10f)
                    .padding(start = 8.dp),
                style = TextStyle(fontSize = 22.sp)
            )
            Image(
                painter = painterResource(R.drawable.arrow_forward_light),
                contentDescription = null,
                modifier = modifier
                    .align(Alignment.CenterVertically)
                    .weight(1f)
            )
        }
    }
}
