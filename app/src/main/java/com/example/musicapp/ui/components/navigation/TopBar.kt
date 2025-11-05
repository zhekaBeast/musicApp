package com.example.musicapp.ui.components.navigation

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
import com.example.musicapp.navigation.AppScreen

@Composable
fun TopBar(navController: NavHostController, onNavigate: () -> Unit = {}) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    when(currentDestination?.route){
        AppScreen.Home.route->{
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
        else -> {
            val appScreen = when (currentDestination?.route) {
                AppScreen.Search.route -> AppScreen.Search
                AppScreen.Settings.route -> AppScreen.Settings
                AppScreen.Playlists.route -> AppScreen.Playlists
                else -> AppScreen.Null
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp)
                    .background(color = Color.Gray)
            ) {
                IconButton(
                    onClick = {
                        onNavigate()
                        navController.popBackStack()
                    }) {
                    Icon(
                        tint = Color.Black,
                        contentDescription = stringResource(R.string.back),
                        imageVector = Icons.Default.ArrowBack
                    )
                }
                if(appScreen.showTitle) {
                    Text(
                        stringResource(appScreen.titleId), style = TextStyle(fontSize = 22.sp),
                        modifier = Modifier.padding(start = 12.dp).padding(vertical = 10.dp)
                    )
                }
            }
        }
    }
}