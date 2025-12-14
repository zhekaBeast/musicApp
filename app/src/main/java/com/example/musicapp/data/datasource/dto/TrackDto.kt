package com.example.musicapp.data.datasource.dto

import com.example.musicapp.domain.models.Track
import com.google.gson.annotations.SerializedName

data class TrackDto(
    @SerializedName("trackId")
    val id: Long,
    @SerializedName("trackName")
    val trackName: String,
    @SerializedName("artistName")
    val artistName: String,
    @SerializedName("trackTimeMillis")
    val trackTimeMillis: Long,
    @SerializedName("artworkUrl100")
    val artworkUrl: String? = null,
    var favorite: Boolean = false
) {
    fun toDomain(): Track {
        val seconds = this.trackTimeMillis / 1000
        val minutes = seconds / 60
        val trackTime = "%02d".format(minutes) + ":" + "%02d".format(seconds % 60)
        return Track(
            id = this.id,
            trackName = this.trackName,
            artistName = this.artistName,
            trackTime = trackTime,
            favorite = this.favorite,
            trackTimeMillis = trackTimeMillis,
        )
    }
}

