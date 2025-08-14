package com.example.spotifylite.cleanarchitecture.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.spotifylite.cleanarchitecture.data.mapper.toDomain
import com.example.spotifylite.cleanarchitecture.data.remote.JamendoApi
import com.example.spotifylite.cleanarchitecture.domain.model.Track

class TracksPagingSource(
    private val api: JamendoApi,
    private val pageSize: Int,
    private val query: String? = null
) : PagingSource<Int, Track>() {

    override fun getRefreshKey(state: PagingState<Int, Track>): Int? {
        val anchor = state.anchorPosition ?: return null
        val anchorPage = state.closestPageToPosition(anchor)
        return anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Track> {
        val page = params.key ?: 0 // 0-based page
        val offset = page * pageSize
        return try {
            val resp = if (query.isNullOrBlank()) {
                api.getTracks(limit = pageSize, offset = offset)
            } else {
                api.searchTracks(query = query, limit = pageSize, offset = offset)
            }
            val items = resp.results.map { it.toDomain() }
            LoadResult.Page(
                data = items,
                prevKey = if (page == 0) null else page - 1,
                nextKey = if (items.size < pageSize) null else page + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}