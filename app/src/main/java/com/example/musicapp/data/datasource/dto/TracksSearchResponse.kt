package com.example.musicapp.data.datasource.dto

data class TracksSearchResponse(
    val resultCount: Int,
    val results: List<TrackDto>
) : BaseResponse()