package com.example.musicapp.data.datasource.api

import com.example.musicapp.data.datasource.dto.BaseResponse

interface NetworkClient {
    suspend fun doRequest(expression: String = ""): BaseResponse
}