package com.example.musicapp.data.datasource.dto

data class TrackDto(
    val id: Long,
    val trackName: String,
    val artistName: String,
    val trackTimeMillis: Int,
    var favorite: Boolean=false
)