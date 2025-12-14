package com.example.musicapp.data.datasource.dto

data class Playlist(
    val id: Long = 0,
    val name: String,
    val description: String,
    val coverImageUri: String? = null,
    val trackIds: List<Long> = emptyList()
){
}

//@Entity(tableName = "playlists")
//data class PlaylistEntity(
//    @PrimaryKey(autoGenerate = true)
//    val id: Long = 0,
//    val name: String,
//    val description: String,
//    val coverImageUri: String? = null  // Новая колонка для URI обложки
//)