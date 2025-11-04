package com.example.musicapp.data.datasource.remote.retrofit

import com.example.musicapp.data.datasource.remote.mock.Storage
import com.example.musicapp.data.datasource.remote.api.NetworkClient
import com.example.musicapp.data.datasource.remote.dto.BaseResponse
import com.example.musicapp.data.datasource.remote.dto.TracksSearchResponse
import kotlinx.coroutines.delay

class RetrofitNetworkClient(val storage: Storage): NetworkClient {

    override suspend fun doRequest(expression: String): BaseResponse {
        delay(1000)
        val response = TracksSearchResponse(results = storage.search(expression))
        response.resultCode = 200
        return response
    }
}
