package com.example.spotifylite.cleanarchitecture.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.spotifylite.cleanarchitecture.domain.model.Track
import com.example.spotifylite.cleanarchitecture.domain.repo.TrackRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(
    repo : TrackRepository
) : ViewModel(){
    val track : Flow<PagingData<Track>> = repo.popularTracks().cachedIn(viewModelScope)
}