package com.example.snake_game

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.snake_game.ui.theme.Snake_GameTheme
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Snake_GameTheme {
                // Initialize the state for splash screen
                val showSplashScreen = remember { mutableStateOf(true) }

                // Show splash screen initially
                if (showSplashScreen.value) {
                    SplashScreen(onSplashFinished = { showSplashScreen.value = false })
                } else {
                    // Once splash is finished, show the game screen
                    val viewModel = viewModel<SnakeGameViewModel>()
                    val state by viewModel.state.collectAsStateWithLifecycle()
                    SnakeGameScreen(
                        state = state,
                        onEvent = viewModel::onEvent
                    )
                }
            }
        }
    }
}

// Splash Screen Composable
@Composable
fun SplashScreen(onSplashFinished: () -> Unit) {
    val splashImage: Painter = painterResource(id = R.drawable.snake_byte_logo)

    // Box to center the image on the screen
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = splashImage,
            contentDescription = "Splash Screen Image",
            modifier = Modifier.size(400.dp)
        )
    }

    // Trigger transition after a delay (3 seconds)
    LaunchedEffect(Unit) {
        delay(3000) // Delay for 3 seconds
        onSplashFinished() // Transition to the game screen
    }
}
