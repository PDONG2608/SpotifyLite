package com.example.spotifylite.cleanarchitecture.data.mapper

import com.example.spotifylite.cleanarchitecture.data.model.JamendoTrackDto
import com.example.spotifylite.cleanarchitecture.domain.model.Track

fun JamendoTrackDto.toDomain() = Track (
    id = id,
    title = title,
    artist = artist,
    durationSec = durationSec,
    audioUrl = audioUrl,
    imageUrl = imageUrl
)