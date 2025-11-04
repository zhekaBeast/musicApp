package com.example.musicapp.data.datasource.remote.dto

data class Track (
    val id: Long = 0,
    val trackName: String,
    val artistName: String,
    val trackTime: String,
    var favorite: Boolean = false,
    )