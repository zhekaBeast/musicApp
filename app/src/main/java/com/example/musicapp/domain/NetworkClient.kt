package com.example.musicapp.domain

import com.example.musicapp.data.dto.BaseResponse
import com.example.musicapp.domain.models.TracksSearchRequest

interface NetworkClient {
    suspend fun doRequest(request: TracksSearchRequest): BaseResponse
}