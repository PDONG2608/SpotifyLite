package com.example.spotifylite.cleanarchitecture.domain.repo

import androidx.paging.PagingData
import com.example.spotifylite.cleanarchitecture.domain.model.Track
import kotlinx.coroutines.flow.Flow

interface TrackRepository {
    fun popularTracks(pageSize: Int = 20): Flow<PagingData<Track>>
    fun searchTracks(query: String, pageSize: Int = 20): Flow<PagingData<Track>>
}