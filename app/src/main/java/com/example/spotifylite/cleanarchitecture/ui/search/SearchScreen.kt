package com.example.spotifylite.cleanarchitecture.ui.search

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ListItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.media3.session.MediaController
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import com.example.spotifylite.cleanarchitecture.domain.model.Track
import com.example.spotifylite.cleanarchitecture.ui.SearchViewModel

@Composable
fun SearchScreen(
    onBack: () -> Unit,
    onOpenPlayer: () -> Unit,
    controller: MediaController?,
    vm: SearchViewModel = hiltViewModel()
) {
    val results = vm.results.collectAsLazyPagingItems()
    var text by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Search") }, navigationIcon = {
                TextButton(onClick = onBack) { Text("Back") }
            })
        }
    ) { padding ->
        Column(Modifier.padding(padding)) {
            OutlinedTextField(
                value = text,
                onValueChange = {
                    text = it
                    vm.setQuery(it)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                placeholder = { Text("Type song or artist…") }
            )
            LazyColumn {
                items(results) { track ->
                    if (track != null) SearchRow(track) {
                        controller?.let { c ->
                            val mediaItem = trackToMediaItem(
                                id = track.id,
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
}

@Composable
private fun SearchRow(track: Track, onClick: () -> Unit) {
    ListItem(
        headlineContent = { Text(track.title) },
        supportingContent = { Text(track.artist ?: "Unknown") },
        leadingContent = {
            AsyncImage(
                model = track.imageUrl,
                contentDescription = null,
                modifier = Modifier.size(48.dp)
            )
        },
        modifier = Modifier.fillMaxWidth().padding(horizontal = 6.dp).clickable { onClick() }
    )
}