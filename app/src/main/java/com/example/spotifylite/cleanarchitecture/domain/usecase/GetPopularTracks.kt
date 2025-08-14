package com.example.spotifylite.cleanarchitecture.domain.usecase

import com.example.spotifylite.cleanarchitecture.domain.repo.TrackRepository

class GetPopularTracks(private val repo: TrackRepository) {
    operator fun invoke(pageSize: Int = 20) = repo.popularTracks(pageSize)
}