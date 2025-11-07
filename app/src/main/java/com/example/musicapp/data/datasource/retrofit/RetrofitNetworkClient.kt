package com.example.musicapp.data.network

import com.example.musicapp.data.datasource.dto.BaseResponse
import com.example.musicapp.data.datasource.dto.TracksSearchResponse
import com.example.musicapp.data.datasource.mock.Storage
import com.example.musicapp.domain.NetworkClient
import com.example.musicapp.domain.models.TracksSearchRequest
import kotlinx.coroutines.delay

class RetrofitNetworkClient(val storage: Storage): NetworkClient {

    override suspend fun doRequest(request: TracksSearchRequest): BaseResponse {
        delay(1000)
        val response = TracksSearchResponse(results = storage.search(request.expression))
        response.resultCode = 200
        return response
    }
}
