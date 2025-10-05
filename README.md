SpotifyLite — Android Music App (Jetpack Compose + Clean Architecture)

A modern, Spotify-like Android music app powered by Kotlin/Flow, Jetpack Compose (Material 3 + Navigation), Hilt (DI), Retrofit + OkHttp + Kotlinx Serialization, Paging 3, Media3, and Coil — using the Jamendo public API as the backend. Built with a layered Clean Architecture.

✨ Features

Browse popular tracks (paged, infinite scroll)

Search songs/artists with debounced queries

Stream audio with Media3 (background playback + notification)

Simple Now Playing screen (play/pause/seek)

Artwork loading & caching with Coil

Resilient networking with Retrofit/OkHttp

Modular Clean Architecture with DI via Hilt

100% Jetpack Compose UI, Material 3 theming

Paging 3 for efficient list loading

🧱 Tech Stack

Language: Kotlin (+ Coroutines/Flow)

UI: Jetpack Compose, Material 3, Navigation Compose

DI: Hilt

Networking: Retrofit, OkHttp (Logging Interceptor), Kotlinx Serialization

Images: Coil

Paging: Paging 3 (+ paging-compose)

Playback: Media3 (ExoPlayer + Session)

Build: Gradle KTS

Min SDK: 25 (adjust as needed)
🏛️ Clean Architecture

     <img width="543" height="357" alt="image" src="https://github.com/user-attachments/assets/58c7dc73-c6c1-42e5-8663-dbb493cbb23d" />
     
Rules of thumb

ui depends on domain (interfaces/models), not on data directly

data implements domain.repo and depends on network, paging, etc.

player is an Android-specific boundary (Media3 service/session)

DI modules live in di/ and wire up implementations

🔑 Jamendo API Setup

Create a Jamendo developer account and obtain a client_id.

In app/build.gradle.kts, set:

defaultConfig {
    buildConfigField("String", "JAMENDO_CLIENT_ID", "\"YOUR_CLIENT_ID\"")
}


The app automatically appends client_id via an OkHttp interceptor.
Jamendo base URL: https://api.jamendo.com/v3.0/

📲 Permissions & Manifest

<img width="608" height="252" alt="image" src="https://github.com/user-attachments/assets/aa77b51b-08e7-4da2-803a-f0cd3c55c060" />

▶️ How Playback Works (Media3)

PlaybackService hosts a MediaSession with ExoPlayer

UI obtains a MediaController (via SessionToken) to control playback

Tapping a track builds a MediaItem (title/artist/artwork URI/audio URL), sets it on the controller, and calls prepare() + play()

Notification & lockscreen controls are provided by Media3 when session is active

🔄 Paging Flow

TracksPagingSource calls JamendoApi.getTracks() (or searchTracks()) with limit + offset

Pager(PagingConfig(...)) exposes Flow<PagingData<Track>>

Compose uses collectAsLazyPagingItems() to render infinite lists

🖼️ Screens (Compose)

Home: Popular tracks list (paged). Tap to play.

Search: Debounced query → paged results

Player: Minimal now playing & transport controls

Add player state listeners to reflect play/pause/time in real time.

🧪 Testing Ideas (optional)

Unit tests for mappers (DTO → domain)

Repository tests with fake PagingSource

Instrumented test to verify Media3 service binding

Snapshot tests for Compose UI states (loading/error/empty)

🧭 Roadmap

 Queue & playlist management

 Player bottom sheet mini-controller

 DataStore for last session + resume position

 DownloadService for offline caching

 Album/Artist detail screens

 Error, empty, and retry UI states

 Android Auto / MediaLibraryService

 📁 Sample Directory Tree
 
<img width="320" height="166" alt="image" src="https://github.com/user-attachments/assets/db0d90b9-3683-4b46-a05b-ed31a482ec95" />
 
⚠️ Notes / Limitations

Jamendo streams are public/free but subject to API terms/limits

Some tracks may not provide high-res artwork or long previews

This is a learning scaffold; refine error handling, caching, and state management for production

📜 License

MIT (or your choice). See LICENSE.

🙌 Acknowledgements

AndroidX, Media3, and Jetpack teams

Jamendo for the public API

Coil, Retrofit, OkHttp, Kotlinx Serialization, Hilt, Paging contributors

Quick Start Checklist

 Insert JAMENDO_CLIENT_ID

 Run app → browse popular tracks

 Use Search → play track

 Verify notification and background playback
