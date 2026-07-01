package com.textgame

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.textgame.data.audio.BgmManager
import com.textgame.data.audio.BgmTrack
import com.textgame.data.local.SettingsManager
import com.textgame.data.local.SettingsPreferences
import com.textgame.di.AppModule
import com.textgame.presentation.ui.creator.CreatorScreen
import com.textgame.presentation.ui.game.GameScreen
import com.textgame.presentation.ui.main.MainMenuScreen
import com.textgame.presentation.ui.settings.GameSettingsScreen
import com.textgame.presentation.ui.settings.SettingsScreen
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    private lateinit var bgmManager: BgmManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppModule.initialize(this)
        bgmManager = BgmManager.getInstance(this)
        bgmManager.initialize()

        lifecycleScope.launch {
            val settings = SettingsManager.getSettingsFlow(this@MainActivity).first()
            if (settings.apiKey.isNotEmpty()) {
                AppModule.configureAI(settings)
            }
            bgmManager.setMusicEnabled(settings.musicEnabled)
        }

        setContent {
            MaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    NavHost(navController = navController, startDestination = "main") {
                        composable("main") {
                            MainMenuScreen(
                                onNewGame = { navController.navigate("creator") },
                                onContinueGame = { sessionId ->
                                    navController.navigate("game/$sessionId")
                                },
                                onSettings = { navController.navigate("settings") }
                            )
                        }
                        composable("creator") {
                            CreatorScreen(
                                onBack = { navController.popBackStack() },
                                onGameCreated = { sessionId ->
                                    navController.navigate("game/$sessionId") {
                                        popUpTo("main") { inclusive = false }
                                    }
                                }
                            )
                        }
                        composable("game/{sessionId}") { backStackEntry ->
                            val sessionId = backStackEntry.arguments?.getString("sessionId")?.toLongOrNull() ?: 0L
                            GameScreen(
                                sessionId = sessionId,
                                onBack = { navController.popBackStack() },
                                onOpenSettings = { navController.navigate("game_settings/$sessionId") }
                            )
                        }
                        composable("game_settings/{sessionId}") { backStackEntry ->
                            val sessionId = backStackEntry.arguments?.getString("sessionId")?.toLongOrNull() ?: 0L
                            GameSettingsScreen(
                                sessionId = sessionId,
                                onBack = { navController.popBackStack() }
                            )
                        }
                        composable("settings") {
                            SettingsScreen(
                                onBack = { navController.popBackStack() }
                            )
                        }
                    }
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        bgmManager.resume()
    }

    override fun onPause() {
        super.onPause()
        bgmManager.pause()
    }
}
