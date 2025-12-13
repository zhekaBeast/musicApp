package com.example.musicapp.ui.components.common

import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.musicapp.domain.models.Track

@Composable
fun TrackListItem(track: Track, onClick: () -> Unit) {
    Row(
        modifier = Modifier.Companion
            .fillMaxWidth()
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = LocalIndication.current,
                onClick = onClick
            ), verticalAlignment = Alignment.Companion.CenterVertically
    ) {
        IconButton(
            onClick = onClick,
            content = {
                Icon(
                    imageVector = Icons.Default.PlayArrow,
                    contentDescription = "",
                )
            }
        )
        Column {
            Text(track.trackName, fontWeight = FontWeight.Companion.Bold)
            Row {
                Text(track.artistName)
                Text(modifier = Modifier.Companion.padding(start = 8.dp), text = track.trackTime)
            }
        }
    }
}