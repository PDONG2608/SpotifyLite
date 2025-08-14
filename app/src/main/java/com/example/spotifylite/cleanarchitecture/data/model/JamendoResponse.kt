package com.example.spotifylite.cleanarchitecture.data.model

import kotlinx.serialization.Serializable

@Serializable
data class JamendoResponse<T>(val results: List<T> = emptyList())