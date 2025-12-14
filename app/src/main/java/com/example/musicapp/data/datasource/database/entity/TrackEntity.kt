package com.example.musicapp.data.datasource.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.musicapp.domain.models.Track

@Entity(tableName = "tracks")
data class TrackEntity(
    @PrimaryKey
    val id: Long,
    val trackName: String,
    val artistName: String,
    val trackTimeMillis: Long,
    val favorite: Boolean = false,
    val artworkUrl: String? = null, 
)

fun TrackEntity.toDomain(): Track {
    val seconds = trackTimeMillis / 1000
    val minutes = seconds / 60
    val trackTime = "%02d".format(minutes) + ":" + "%02d".format(seconds % 60)
    return Track(
        id = id,
        trackName = trackName,
        artistName = artistName,
        trackTime = trackTime,
        trackTimeMillis = trackTimeMillis,
        favorite = favorite,
        pictureUrl = artworkUrl
    )
}

