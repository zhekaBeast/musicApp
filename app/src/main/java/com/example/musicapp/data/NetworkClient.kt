package com.example.musicapp.data

import com.example.musicapp.data.dto.BaseResponse

interface NetworkClient {
    suspend fun doRequest(expression: String = ""): BaseResponse
}