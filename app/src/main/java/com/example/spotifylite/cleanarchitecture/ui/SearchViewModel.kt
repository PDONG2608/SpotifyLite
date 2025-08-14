package com.example.spotifylite.cleanarchitecture.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.spotifylite.cleanarchitecture.domain.model.Track
import com.example.spotifylite.cleanarchitecture.domain.repo.TrackRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flatMapLatest
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val repo: TrackRepository
) : ViewModel() {
    private val query = MutableStateFlow("")

    @OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
    val results: Flow<PagingData<Track>> =
        query.debounce(300)
            .distinctUntilChanged()
            .flatMapLatest { q ->
                if (q.isBlank()) emptyFlow()
                else repo.searchTracks(q)
            }.cachedIn(viewModelScope)

    fun setQuery(q: String) {
        query.value = q
    }
}