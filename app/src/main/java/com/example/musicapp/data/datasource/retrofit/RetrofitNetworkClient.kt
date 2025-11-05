package com.example.musicapp.data.datasource.retrofit

import com.example.musicapp.data.datasource.mock.Storage
import com.example.musicapp.data.datasource.api.NetworkClient
import com.example.musicapp.data.datasource.dto.BaseResponse
import com.example.musicapp.data.datasource.dto.TracksSearchResponse
import kotlinx.coroutines.delay

class RetrofitNetworkClient(val storage: Storage): NetworkClient {

    override suspend fun doRequest(expression: String): BaseResponse {
        delay(1000)
        val response = TracksSearchResponse(results = storage.search(expression))
        response.resultCode = 200
        return response
    }
}
