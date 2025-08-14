package com.example.spotifylite.cleanarchitecture.player

import android.content.ComponentName
import android.content.Context
import androidx.media3.session.MediaController
import androidx.media3.session.SessionToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.guava.await
import kotlinx.coroutines.withContext
import kotlin.coroutines.resumeWithException

suspend fun buildController(context: Context): MediaController =
    withContext(Dispatchers.Main.immediate) {
        val token = SessionToken(context, ComponentName(context, PlaybackService::class.java))
        // await() từ kotlinx-coroutines-guava
        MediaController.Builder(context, token).buildAsync().await()
    }