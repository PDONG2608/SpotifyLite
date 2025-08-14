package com.example.spotifylite.cleanarchitecture.domain.repo

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.spotifylite.cleanarchitecture.data.paging.TracksPagingSource
import com.example.spotifylite.cleanarchitecture.data.remote.JamendoApi
import com.example.spotifylite.cleanarchitecture.domain.model.Track
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TrackRepositoryImpl @Inject constructor(
    private val api : JamendoApi
) : TrackRepository{
    override fun popularTracks(pageSize: Int): Flow<PagingData<Track>> =
        Pager(PagingConfig(pageSize = pageSize, prefetchDistance = pageSize / 2)) {
            TracksPagingSource(api, pageSize)
        }.flow
    override fun searchTracks(query: String, pageSize: Int): Flow<PagingData<Track>> =
        Pager(PagingConfig(pageSize = pageSize, prefetchDistance = pageSize / 2)) {
            TracksPagingSource(api, pageSize, query)
        }.flow
}