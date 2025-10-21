package com.example.musicapp.data.network

import com.example.musicapp.data.NetworkClient
import com.example.musicapp.data.dto.BaseResponse
import com.example.musicapp.data.dto.TracksSearchResponse

class RetrofitNetworkClient: NetworkClient {
    override fun doRequest(dto: Any): BaseResponse {
        return TracksSearchResponse(listOf())
    }
}