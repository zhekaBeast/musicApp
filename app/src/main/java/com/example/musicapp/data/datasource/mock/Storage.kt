package com.example.musicapp.data.datasource.mock

import com.example.musicapp.data.datasource.dto.TrackDto

class Storage {
    private val listTracks = listOf(
        TrackDto(
            id = 0,
            trackName = "Владивосток 2000",
            artistName = "Мумий Троль",
            trackTimeMillis = 158000
        ),
        TrackDto(
            id = 1,
            trackName = "Группа крови",
            artistName = "Кино",
            trackTimeMillis = 283000
        ),
        TrackDto(
            id = 2,
            trackName = "Не смотри назад",
            artistName = "Ария",
            trackTimeMillis = 312000
        ),
        TrackDto(
            id = 3,
            trackName = "Звезда по имени Солнце",
            artistName = "Кино",
            trackTimeMillis = 225000
        ),
        TrackDto(
            id = 4,
            trackName = "Лондон",
            artistName = "Аквариум",
            trackTimeMillis = 272000
        ),
        TrackDto(
            id = 5,
            trackName = "На заре",
            artistName = "Альянс",
            trackTimeMillis = 230000
        ),
        TrackDto(
            id = 6,
            trackName = "Перемен",
            artistName = "Кино",
            trackTimeMillis = 296000
        ),
        TrackDto(
            id = 7,
            trackName = "Розовый фламинго",
            artistName = "Сплин",
            trackTimeMillis = 195000
        ),
        TrackDto(
            id = 8,
            trackName = "Танцевать",
            artistName = "Мельница",
            trackTimeMillis = 222000
        ),
        TrackDto(
            id = 9,
            trackName = "Чёрный бумер",
            artistName = "Серега",
            trackTimeMillis = 241000
        )
    )

    fun search(request: String): List<TrackDto> {
        val result = listTracks.filter {
            it.trackName
                .lowercase()
                .contains(request.lowercase())
                    || it.artistName.lowercase()
                        .contains(request.lowercase())
        }
        return result
    }

    fun getAllTracks(): List<TrackDto> {
        return listTracks.toList()
    }
}