package com.example.spotifylite.cleanarchitecture.data.remote

import com.example.spotifylite.cleanarchitecture.data.model.JamendoResponse
import com.example.spotifylite.cleanarchitecture.data.model.JamendoTrackDto
import retrofit2.http.GET
import retrofit2.http.Query

interface JamendoApi {
    @GET
    suspend fun getTracks(
        @Query("limit") limit: Int,
        @Query("offset") offset: Int,
        @Query("format") format: String = "json",
        @Query("order") order: String = "popularity_total"
    ) : JamendoResponse<JamendoTrackDto>

    @GET("tracks")
    suspend fun searchTracks(
        @Query("search") query: String,
        @Query("limit") limit: Int,
        @Query("offset") offset: Int,
        @Query("format") format: String = "json"
    ): JamendoResponse<JamendoTrackDto>
}