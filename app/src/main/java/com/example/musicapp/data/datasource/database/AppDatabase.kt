package com.example.musicapp.data.datasource.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.musicapp.data.datasource.database.dao.PlaylistsDao
import com.example.musicapp.data.datasource.database.dao.TracksDao
import com.example.musicapp.data.datasource.database.entity.PlaylistEntity
import com.example.musicapp.data.datasource.database.entity.PlaylistTrackCrossRef
import com.example.musicapp.data.datasource.database.entity.TrackEntity

@Database(
    entities = [
        TrackEntity::class,
        PlaylistEntity::class,
        PlaylistTrackCrossRef::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun tracksDao(): TracksDao

    abstract fun playlistsDao(): PlaylistsDao
}

