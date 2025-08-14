package com.example.spotifylite.cleanarchitecture.data.model

import kotlinx.serialization.Serializable

@Serializable
data class JamendoTrackDto(
    val id: String,
    val title: String,
    val artist: String?,
    val durationSec: Int?,
    val audioUrl: String?,
    val imageUrl: String?
)