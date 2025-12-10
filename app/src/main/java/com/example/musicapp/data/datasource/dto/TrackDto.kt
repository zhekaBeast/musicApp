package com.example.musicapp.data.datasource.dto

import com.example.musicapp.domain.models.Track

data class TrackDto(
    val id: Long,
    val trackName: String,
    val artistName: String,
    val trackTimeMillis: Int,
    var favorite: Boolean=false
){
    fun toDomain(): Track {
        val seconds = this.trackTimeMillis/1000
        val minutes = seconds/60
        val trackTime = "%02d".format(minutes) + ":" + "%02d".format(seconds%60)
        return Track(
            id = this.id,
            trackName = this.trackName,
            artistName = this.artistName,
            trackTime = trackTime,
            favorite=this.favorite
        )
    }
}

