package com.example.musicapp.data.datasource.dto

data class Playlist(
    val id: Long = 0,
    val name: String,
    val description: String,
){
    val trackIds = mutableListOf<Long>()
}