package com.example.musicapp.data.network

import com.example.musicapp.data.NetworkClient
import com.example.musicapp.data.datasource.dto.BaseResponse
import com.example.musicapp.data.datasource.dto.TracksSearchResponse
import com.example.musicapp.domain.models.TracksSearchRequest
import retrofit2.http.GET
import retrofit2.http.Query
import java.io.IOException

interface ITunesApiService {

    @GET("search")
    suspend fun searchTracks(
        @Query("term") query: String,
        @Query("media") media: String = "music",
        @Query("entity") entity: String = "song",
        @Query("limit") limit: Int = 50
    ): TracksSearchResponse
}

class RetrofitNetworkClient(
    private val api: ITunesApiService
) : NetworkClient {

    override suspend fun doRequest(request: TracksSearchRequest): BaseResponse {
        return try {
            val response = api.searchTracks(
                query = request.expression,
                media = "music",
                entity = "song",
                limit = 50
            )
            response.resultCode = 200
            response
        } catch (e: IOException) {
            BaseResponse(resultCode = -1)
        } catch (e: Exception) {
            BaseResponse(resultCode = -2)
        }
    }
}
