package com.example.recipebook.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val LightColors = lightColorScheme(
    primary = Accent,
    onPrimary = Color.White,
    primaryContainer = AccentSoft,
    onPrimaryContainer = AccentDeep,
    secondary = SecondaryText,
    onSecondary = Color.White,
    secondaryContainer = AccentSoft2,
    onSecondaryContainer = AccentDeep,
    background = PageBg,
    onBackground = OnSurface,
    surface = Surface,
    onSurface = OnSurface,
    surfaceVariant = SurfaceHigh,
    onSurfaceVariant = OnSurfaceVar,
    error = ErrorRed,
    onError = Color.White
)

private val DarkColors = darkColorScheme(
    primary = Accent,
    onPrimary = Color.White,
    primaryContainer = AccentDeep,
    onPrimaryContainer = AccentSoft,
    secondary = SecondaryText,
    onSecondary = Color.White,
    background = PageBg,
    onBackground = OnSurface,
    surface = Surface,
    onSurface = OnSurface
)

@Composable
fun RecipeBookTheme(
    darkTheme: Boolean = false,   // force light — our palette is light-only
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColors else LightColors

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = AccentSoft.toArgb()
            WindowCompat.getInsetsController(window, view)
                .isAppearanceLightStatusBars = true
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = AppTypography,
        content = content
    )
}