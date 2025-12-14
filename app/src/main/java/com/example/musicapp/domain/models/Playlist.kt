package com.example.musicapp.domain.models

data class Playlist(
    val id: Long = 0,
    val name: String,
    val description: String,
    val coverImageUri: String? = null,
    val trackIds: List<Long> = emptyList()
){
}