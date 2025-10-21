package com.example.musicapp.data

import com.example.musicapp.data.dto.BaseResponse

interface NetworkClient {
    fun doRequest(dto: Any): BaseResponse
}