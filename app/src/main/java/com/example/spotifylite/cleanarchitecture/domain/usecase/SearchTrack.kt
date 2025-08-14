package com.example.spotifylite.cleanarchitecture.domain.usecase

import com.example.spotifylite.cleanarchitecture.domain.repo.TrackRepository

class SearchTracks(private val repo: TrackRepository) {
    operator fun invoke(query: String, pageSize: Int = 20) = repo.searchTracks(query, pageSize)
}