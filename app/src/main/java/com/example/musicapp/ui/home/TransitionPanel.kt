package com.example.musicapp.ui.home

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.musicapp.navigation.AppScreen

@Composable
fun TransitionPanel(
    appScreen: AppScreen, navController: NavHostController, onNavigate: () -> Unit = {}
) {
    Button(
        onClick = {
            onNavigate()
            navController.navigate(appScreen.route)
        },
        shape = RectangleShape,
        colors = ButtonColors(
            containerColor = Color.White,
            contentColor = Color.Black,
            disabledContainerColor = Color.LightGray,
            disabledContentColor = Color.DarkGray
        )
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .height(66.dp)
        ) {
            Icon(
                tint = Color.Black,
                contentDescription = null,
                imageVector = appScreen.icon
            )
            Text(
                stringResource(appScreen.titleId),
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 8.dp),
                style = TextStyle(fontSize = 22.sp)
            )
            Icon(
                tint = Color.Black,
                contentDescription = null,
                imageVector = Icons.Default.KeyboardArrowRight
            )
        }
    }
}