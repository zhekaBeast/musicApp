package com.example.musicapp.data.network

import com.example.musicapp.creator.Storage
import com.example.musicapp.data.NetworkClient
import com.example.musicapp.data.dto.BaseResponse
import com.example.musicapp.data.dto.TracksSearchResponse
import kotlinx.coroutines.delay

class RetrofitNetworkClient(val storage: Storage): NetworkClient {

    override suspend fun doRequest(expression: String): BaseResponse {
        delay(1000)
        val response = TracksSearchResponse(results = storage.search(expression))
        response.resultCode = 200
        return response
    }
}
