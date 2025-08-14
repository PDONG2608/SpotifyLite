package com.example.spotifylite.cleanarchitecture.domain.usecase

import com.example.spotifylite.cleanarchitecture.domain.repo.TrackRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {
    @Provides
    fun provideGetPopular(repo: TrackRepository) = GetPopularTracks(repo)
    @Provides
    fun provideSearch(repo: TrackRepository) = SearchTracks(repo)
}
