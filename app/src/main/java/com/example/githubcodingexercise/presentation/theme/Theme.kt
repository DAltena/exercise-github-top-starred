package com.example.githubcodingexercise.presentation.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
    primary = Color.White,
    background = ThemeBlack,
    surface = DarkGray,
    onPrimary = Color.White,
    primaryContainer = DarkGray2,
    onPrimaryContainer = Color.White,
    tertiaryContainer = DarkGreen,
    onTertiaryContainer = Color.White
)

@Composable
fun GitHubCodingExerciseTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = DarkColorScheme,
        typography = Typography,
        content = content
    )
}