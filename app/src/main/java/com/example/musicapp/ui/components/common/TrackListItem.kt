package com.example.musicapp.ui.components.common

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.musicapp.domain.models.Track

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TrackListItem(
    track: Track,
    onClick: () -> Unit,
    onLongClick: () -> Unit = {}
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .combinedClickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = LocalIndication.current,
                onClick = onClick,
                onLongClick = onLongClick
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(Modifier.size(48.dp).padding(4.dp)) {
            UriPicture(track.pictureUrl)
        }
        Column {
            Text(track.trackName, fontWeight = FontWeight.Bold)
            Row {
                Text(track.artistName)
                Text(
                    modifier = Modifier.padding(start = 8.dp),
                    text = track.trackTime
                )
            }
        }
    }
}