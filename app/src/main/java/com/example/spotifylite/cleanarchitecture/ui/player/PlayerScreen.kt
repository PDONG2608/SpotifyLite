package com.example.spotifylite.cleanarchitecture.ui.player

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.media3.session.MediaController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlayerScreen(onBack: () -> Unit, controller: MediaController?) {
    val isPlaying by remember { mutableStateOf(controller?.isPlaying == true) }
    // For a reactive UI, consider adding a Player.Listener and updating state via rememberUpdatedState.

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Now Playing") },
                navigationIcon = { TextButton(onClick = onBack) { Text("Back") } }
            )
        }
    ) { padding ->
        Column(
            Modifier
                .padding(padding)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(controller?.mediaMetadata?.title?.toString() ?: "Nothing playing")
            Spacer(Modifier.height(16.dp))
            Row {
                Button(onClick = { controller?.seekBack() }) { Text("<<") }
                Spacer(Modifier.width(12.dp))
                Button(onClick = {
                    if (controller?.isPlaying == true) controller.pause() else controller?.play()
                }) { Text(if (controller?.isPlaying == true) "Pause" else "Play") }
                Spacer(Modifier.width(12.dp))
                Button(onClick = { controller?.seekForward() }) { Text(">>") }
            }
        }
    }
}
