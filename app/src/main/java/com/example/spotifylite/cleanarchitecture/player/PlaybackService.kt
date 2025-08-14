package com.example.spotifylite.cleanarchitecture.player

import androidx.media3.common.MediaItem
import androidx.media3.common.MediaMetadata
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.session.MediaSession
import androidx.media3.session.MediaSessionService
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class PlaybackService: MediaSessionService() {

    @Inject lateinit var player: ExoPlayer
    private var mediaSession: MediaSession? = null

    override fun onCreate() {
        super.onCreate()
        mediaSession = MediaSession.Builder(this, player).build()
    }
    override fun onGetSession(controllerInfo: MediaSession.ControllerInfo): MediaSession? {
        return mediaSession
    }

    override fun onUpdateNotification(session: MediaSession, startInForegroundRequired: Boolean) {
        super.onUpdateNotification(session, startInForegroundRequired)
    }

    override fun onDestroy() {
        mediaSession?.run {
            player.release()
            release()
        }
        mediaSession = null
        super.onDestroy()
    }
}

// Helper to build MediaItems from your domain tracks:
fun trackToMediaItem(
    id: String,
    title: String,
    artist: String?,
    audioUrl: String?,
    imageUrl: String?
): MediaItem {
    val metadata = MediaMetadata.Builder()
        .setTitle(title)
        .setArtist(artist)
        .setArtworkUri(imageUrl?.let { android.net.Uri.parse(it) })
        .build()

    val builder = MediaItem.Builder()
        .setMediaId(id)
        .setMediaMetadata(metadata)

    audioUrl?.let { builder.setUri(it) }
    return builder.build()
}
