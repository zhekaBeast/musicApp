package com.example.musicapp.data.datasource.remote.api

import com.example.musicapp.data.datasource.remote.dto.BaseResponse

interface NetworkClient {
    suspend fun doRequest(expression: String = ""): BaseResponse
}