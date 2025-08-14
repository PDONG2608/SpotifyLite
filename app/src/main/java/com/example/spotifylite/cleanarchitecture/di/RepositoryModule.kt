package com.example.spotifylite.cleanarchitecture.di

import com.example.spotifylite.cleanarchitecture.domain.repo.TrackRepository
import com.example.spotifylite.cleanarchitecture.domain.repo.TrackRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun bindTrackRepo(impl: TrackRepositoryImpl): TrackRepository
}