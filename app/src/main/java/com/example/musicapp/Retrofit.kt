//package com.example.musicapp
//
//import android.telecom.Call
//import com.example.musicapp.domain.models.TracksSearchRequest
//import retrofit2.http.GET
//import retrofit2.http.Query
//
//
//class RetrofitNetworkClient(private val api: ITunesApiService) : NetworkClient {
//
//    override suspend fun doRequest(dto: Any): BaseResponse {
//        return try {
//            when (dto) {
//                is TracksSearchRequest -> api.searchTracks(
//                    query = dto.expression,
//                    media = "music",
//                    entity = "song",
//                    limit = 10
//                )
//
//                else -> BaseResponse().apply {
//                    resultCode = 400
//                    errorMessage = "Invalid request type: expected TracksSearchRequest or String"
//                }
//            }
//        } catch (e: IOException) {
//            BaseResponse().apply {
//                resultCode = -1
//                errorMessage = "Network error: ${e.message ?: "Unknown IO error"}"
//            }
//        } catch (e: Exception) {
//            BaseResponse().apply {
//                resultCode = -2
//                errorMessage = "Unexpected error: ${e.message ?: "Unknown error"}"
//            }
//        }
//    }
//}
//data class TrackDto(
//    val id: Long,
//    val trackName: String,
//    val artistName: String,
//    val trackTimeMillis: Long,
//    val previewUrl: String?,
//    val image: String?
//)
//
//data class TracksSearchResponse(
//    val resultCount: Int,
//    val results: List<TrackDto>
//) : BaseResponse()
//
//class ITunesApiServiceImpl {
//    fun search(@Query("term") text: String) : Call<TracksSearchResponse>{
//
//    }
//}
////
////https://itunes.apple.com.
////
////
/////search?entity=song
//
//interface ITunesApiService {
//    @GET("search")
//    fun searchTracks(
//        @Query("term") query: String,
//        @Query("media") media: String = "music",
//        @Query("entity") entity: String = "song",
//        @Query("limit") limit: Int = 50
//    ): Call<TracksSearchResponse>
//}
//
//interface NetworkClient {
//    suspend fun doRequest(dto: Any): BaseResponse
//}
//
//
//open class BaseResponse {
//    var resultCode: Int = 0
//}
//
