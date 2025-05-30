package com.example.golfapp.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import com.example.golfapp.Typography

private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFF6B6B6B),
    onPrimary = Color(0xFFFFFFFF),
    background = Color(0xFF101010),
    onBackground = Color(0xFFFFFFFF),
    surface = Color(0xFF303030),
    onSurface = Color(0xFFFFFFFF)
)

private val LightColorScheme = lightColorScheme(
    primary = Color(0xFFD3D3D3),
    onPrimary = Color(0xFF000000),
    secondary = Color(0xFF475B5A),
    tertiary = Color(0xFFB98389),
    background = Color(0xFFFFFFFF),
    surface = Color(0xFFEEEEEE),
    onSurface = Color(0xFF0B0014)
)


@Composable
fun GolfAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}