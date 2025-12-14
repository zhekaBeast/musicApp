package com.example.musicapp.data

import com.example.musicapp.data.datasource.dto.BaseResponse
import com.example.musicapp.domain.models.TracksSearchRequest

interface NetworkClient {
    suspend fun doRequest(request: TracksSearchRequest): BaseResponse
}