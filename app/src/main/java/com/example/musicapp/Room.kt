//package com.example.musicapp
////package ru.yandex.practicum.playlistmaker.data.database.entity
//import androidx.datastore.dataStore
//import androidx.room.Dao
//import androidx.room.Database
//import androidx.room.Entity
//import androidx.room.Insert
//import androidx.room.OnConflictStrategy
//import androidx.room.PrimaryKey
//import androidx.room.RoomDatabase
//import com.example.musicapp.domain.models.Track
//import com.example.musicapp.domain.repository.PlaylistsRepository
//import kotlinx.coroutines.CoroutineName
//import kotlinx.coroutines.CoroutineScope
//import kotlinx.coroutines.SupervisorJob
//import kotlinx.coroutines.coroutineScope
//import org.koin.dsl.module
//
//
//class Room {
//}
//
//
//@Entity(tableName = "tracks")
//data class TrackEntity(
//    @PrimaryKey
//    val id: Long,
//    val trackName: String,
//    val artistName: String,
//    val trackTime: String,
//    val image: String,
//    val favorite: Boolean = false,
//    val playlistId: Long
//)
//
////package ru.yandex.practicum.playlistmaker.data.database.dao
//
//@Dao
//interface TracksDao {
//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    suspend fun insertTrack(track: TrackEntity)
//
//    @Query("SELECT * FROM tracks WHERE trackName = :name AND artistName = :artist")
//    fun getTrackByNameAndArtist(name: String, artist: String): Flow<TrackEntity?>
//}
//
//
//@Database(
//    entities = [
//        TrackEntity::class
//    ], version = 1, exportSchema = false
//)
//abstract class AppDatabase : RoomDatabase() {
//    abstract fun TracksDao(): TracksDao
//}
//
//
//fun TrackEntity.toTrack(): Track {
//    return Track(
//        id = this.id,
//        trackName = this.trackName,
//        artistName = this.artistName,
//        trackTime = this.trackTime,
//        favorite = this.favorite,
//        image = this.image,
//        playlistId = this.playlistId
//    )
//}
//
//fun Track.toEntity(): TrackEntity {
//    return TrackEntity(
//        id = this.id,
//        trackName = this.trackName,
//        artistName = this.artistName,
//        trackTime = this.trackTime,
//        image = this.image,
//        favorite = this.favorite,
//        playlistId = this.playlistId
//    )
//}
//
//
////package ru.yandex.practicum.playlistmaker.di
//
//val databaseModule = module {
//    single {
//        Room.databaseBuilder(
//            get<Context>(),
//            AppDatabase::class.java,
//            "playlists_maker"
//        ).build()
//    }
//}
//
//
////////////////////////
//
//interface TracksRepository {
//    suspend fun searchTracks(expression: String): List<Track>
//
//    fun getTrackByNameAndArtist(track: Track): Flow<Track?>
//
//    fun getFavoriteTracks(): Flow<List<Track>>
//
//    suspend fun insertTrackToPlaylist(track: Track, playlistId: Long)
//
//    suspend fun deleteTrackFromPlaylist(track: Track)
//
//    suspend fun updateTrackFavoriteStatus(track: Track, isFavorite: Boolean)
//}
//
//
//
//////////////////
//
//
//class TracksRepositoryImpl(
//    private val networkClient: NetworkClient,
//    database: AppDatabase
//) : TracksRepository {
//
//    private val dao = database.TracksDao()
//
//    override suspend fun searchTracks(expression: String): List<Track> { ...  }
//
//    override fun getTrackByNameAndArtist(track: Track): Flow<Track?> {
//        return dao.getTrackByNameAndArtist(track.trackName, track.artistName).map { it?.toTrack() }
//    }
//
//    override suspend fun updateTrackFavoriteStatus(track: Track, isFavorite: Boolean) {
//        dao.insertTrack(track.copy(favorite = isFavorite).toEntity())
//    }
//}
//
///////////////////ВАЖНОЕ
//
//
//class PlaylistsRepositoryImpl(
//    database: AppDatabase
//) : PlaylistsRepository {
//    private val dao = database.playlistDao()
//
//    override suspend fun addNewPlaylist(name: String, description: String) {
//        dao.insertPlaylist(
//            PlaylistEntity(
//                name = name,
//                description = description
//            )
//        )
//    }
//}
//
////package ru.yandex.practicum.playlistmaker.data.preferences
//
//class SearchHistoryPreferences(
//    private val dataStore: DataStore<Preferences>,
//    private val coroutineScope: CoroutineScope = CoroutineScope(CoroutineName("search-history-preferences") + SupervisorJob())
//) {
//    fun addEntry(word: String) {
//    }
//
//    suspend fun getEntries(): List<String> {
//    }
//}
//
//
//////////////////
//
//fun addEntry(word: String) {
//    if (word.isEmpty()) {
//        return
//    }
//
//    coroutineScope.launch {
//        dataStore.updateData { preferences ->
//            val historyString = preferences[preferencesKey].orEmpty()
//            val history = if (historyString.isNotEmpty()) {
//                historyString.split(SEPARATOR).toMutableList()
//            } else {
//                mutableListOf()
//            }
//
//            history.remove(word) // удаляем дубликат, если был
//            history.add(0, word)
//
//            val subList = history.subList(0, MAX_ENTRIES) // храним не более 10 элементов
//            val updatedString = subList.joinToString(SEPARATOR)
//
//
//            preferences.toMutablePreferences().apply {
//                this[preferencesKey] = updatedString
//            }
//
//        }
//    }
//}
//
//private const val MAX_ENTRIES = 10
//private const val SEPARATOR = ","