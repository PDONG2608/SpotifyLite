package com.example.spotifylite.cleanarchitecture.ui.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.media3.session.MediaController
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import com.example.spotifylite.cleanarchitecture.domain.model.Track
import com.example.spotifylite.cleanarchitecture.player.trackToMediaItem
import com.example.spotifylite.cleanarchitecture.ui.HomeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onOpenSearch: () -> Unit,
    onOpenPlayer: () -> Unit,
    controller: MediaController?,
    vm: HomeViewModel = hiltViewModel()
) {
    val items = vm.track.collectAsLazyPagingItems()
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("JamPlay") },
                actions = { TextButton(onClick = onOpenSearch) { Text("Search") } }
            )
        }
    ) { padding ->
        LazyColumn(Modifier.padding(padding)) {
            items(items) { track ->
                TrackRow(track) {
                    controller?.let { c ->
                        val mediaItem = trackToMediaItem(
                            id = track.getA,
                            title = track.title,
                            artist = track.artist,
                            audioUrl = track.audioUrl,
                            imageUrl = track.imageUrl
                        )
                        c.setMediaItem(mediaItem)
                        c.prepare()
                        c.play()
                        onOpenPlayer()
                    }
                }
            }
        }
    }
}

@Composable
private fun TrackRow(track: Track, onClick: () -> Unit) {
    Row(
        Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(12.dp)
    ) {
        AsyncImage(
            model = track.imageUrl,
            contentDescription = null,
            modifier = Modifier.size(56.dp)
        )
        Spacer(Modifier.width(12.dp))
        Column(Modifier.weight(1f)) {
            Text(track.title, style = MaterialTheme.typography.titleMedium)
            Text(track.artist ?: "Unknown", style = MaterialTheme.typography.bodyMedium)
        }
        Text("${track.durationSec ?: 0}s", style = MaterialTheme.typography.labelMedium)
    }
}
