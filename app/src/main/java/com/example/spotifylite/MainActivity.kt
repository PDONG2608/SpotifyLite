package com.example.spotifylite

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.*
import androidx.media3.session.MediaController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.spotifylite.cleanarchitecture.player.buildController
import dagger.hilt.android.AndroidEntryPoint
import androidx.compose.material3.MaterialTheme
import com.example.spotifylite.cleanarchitecture.ui.home.HomeScreen
import com.example.spotifylite.cleanarchitecture.ui.search.SearchScreen

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val nav = rememberNavController()

            var controller by remember { mutableStateOf<MediaController?>(null) }

            // Tạo controller trên Main thread (suspend) và thu hồi khi composition bị hủy
            LaunchedEffect(Unit) {
                controller = buildController(this@MainActivity)
            }
            DisposableEffect(Unit) {
                onDispose { controller?.release() }
            }

            MaterialTheme {
                NavHost(navController = nav, startDestination = "home") {
                    composable("home") {
                        HomeScreen(
                            onOpenSearch = { nav.navigate("search") },
                            onOpenPlayer = { nav.navigate("player") },
                            controller = controller
                        )
                    }
                    composable("search") {
                        SearchScreen(
                            onBack = { nav.popBackStack() },
                            onOpenPlayer = { nav.navigate("player") },
                            controller = controller
                        )
                    }
                    composable("player") {
                        PlayerScreen(
                            onBack = { nav.popBackStack() },
                            controller = controller
                        )
                    }
                }
            }
        }
    }
}
