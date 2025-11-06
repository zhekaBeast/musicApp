package com.example.musicapp.ui.components.navigation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.musicapp.R
import com.example.musicapp.ui.navigation.AppScreen

@Composable
fun TopBar(navController: NavHostController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    if (currentDestination?.route == AppScreen.Home.route) {
        EnterAnimation {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(64.dp)
                    .background(color = Color.Blue)
                    .padding(top = 16.dp)
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
        }
    } else {

        val appScreen = when (currentDestination?.route) {
            AppScreen.Settings.route -> AppScreen.Settings
            AppScreen.Search.route -> AppScreen.Search
            else -> AppScreen.Settings
        }

        EnterAnimation {

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp)
            ) {
                IconButton(
                    onClick = {
                        navController.popBackStack()
                    }) {
                    Icon(
                        tint = Color.Black,
                        contentDescription = stringResource(appScreen.titleId),
                        imageVector = Icons.Default.ArrowBack
                    )
                }
                Text(
                    stringResource(appScreen.titleId), style = TextStyle(fontSize = 22.sp),
                    modifier = Modifier
                        .padding(start = 12.dp)
                        .padding(vertical = 10.dp)
                )
            }
        }
    }
}


@Composable
private fun EnterAnimation(content: @Composable () -> Unit) {
    AnimatedVisibility(
        visibleState = MutableTransitionState(
            initialState = false
        ).apply { targetState = true },
        modifier = Modifier,
        enter = fadeIn(initialAlpha = 0.3f),
        exit = fadeOut(),
    ) {
        content()
    }
}