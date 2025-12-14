package com.example.musicapp.ui.components.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import coil.compose.AsyncImage
import com.example.musicapp.R

@Composable
fun UriPicture(coverImageUri: String?){
    Box(
        contentAlignment = Alignment.Companion.Center,
        modifier = Modifier.Companion
            .clip(RoundedCornerShape(8.dp))
            .fillMaxWidth()
            .aspectRatio(1f)
    ) {
        if (coverImageUri === null) {
            Image(
                modifier = Modifier.Companion.size(48.dp),
                painter = painterResource(id = R.drawable.library_light),
                contentDescription = stringResource(R.string.cover),
                colorFilter = ColorFilter.Companion.tint(Color.Companion.Gray)
            )
        } else {
            AsyncImage(
                model = coverImageUri.toUri(),
                contentDescription = stringResource(R.string.cover),
                modifier = Modifier.Companion.fillMaxSize(),
                contentScale = ContentScale.Companion.Crop
            )
        }
    }
}