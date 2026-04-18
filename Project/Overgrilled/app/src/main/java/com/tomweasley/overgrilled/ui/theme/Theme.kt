package com.tomweasley.overgrilled.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable

private val DarkColorScheme = darkColorScheme(
    primary = WarmOrange,
    secondary = Gold,
    tertiary = CreamWhite,
    background = DarkBrown,
    surface = MediumBrown,
    onPrimary = DarkBrown,
    onSecondary = DarkBrown,
    onTertiary = DarkBrown,
    onBackground = CreamWhite,
    onSurface = CreamWhite
)

@Composable
fun OvergrilledTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = DarkColorScheme,
        typography = Typography,
        content = content
    )
}
